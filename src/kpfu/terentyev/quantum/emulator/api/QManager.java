package kpfu.terentyev.quantum.emulator.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import kpfu.terentyev.quantum.emulator.core.*;
import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexMath;
import kpfu.terentyev.quantum.util.ComplexDouble;

import static kpfu.terentyev.quantum.emulator.core.QAlgorithmOneStep.NotAnIndex;

/**
 * Created by aleksandrterentev on 24.01.16.
 */

// Данный класс при разработке рассматривался как обёртка
// c возможностью последующего расширения и настройки.
// Класс QManager реализует средства для хранения информации о
// квантовых регистрах (QReg), а также для работы с регистрами
// (например, для сцепления двух регистров в 1).
public class QManager {

    public QManager() {
        regs = new HashMap<String, QRegInfo>();
    }

    // This class must contain quantum registeres
    protected HashMap <String, QRegInfo> regs;

    // Класс QubitInfo хранит служебную информацию для работы с кубитами
    // (номера кубитов в регистре и адрес регистра)
    public static class QubitInfo {
        // Можно было сделать его частным случаем регистра,
        // но пока удобнее хранить идентификаторы регистров
        // и их номер в регистре
        String regAddr;
        int idxInReg;

        public QubitInfo (String regAddr, int idxInReg) {
            this.regAddr = regAddr;
            this.idxInReg = idxInReg;
        }
    }

    protected int getIdxInReg(QubitInfo q) {
        return q.idxInReg;
    }

    protected String getRegAddr(QubitInfo q) {
        return q.regAddr;
    }

    protected static class QRegInfo {
        public QReg reg;
        public ArrayList<QubitInfo> qubits;

        public QRegInfo(ArrayList<QubitInfo> qubits, QReg reg) {
            this.qubits = qubits;
            this.reg = reg;
        }
    }

    // QubitInfo creation
    public QubitInfo initNewQubit() throws Exception {
        //! Почему (1, 0)?
        return initNewQubit(Complex.unit(), Complex.zero());
    }

    public QubitInfo initNewQubit(
        ComplexDouble alpha, ComplexDouble beta
    ) throws Exception {
        QReg newRegister = new QReg(1, new ComplexDouble[]{alpha, beta});
        String registerID = Double.toString(
            new Date().getTime() + new Random().nextDouble());
        QubitInfo newQubit = new QubitInfo(registerID, 0);
        ArrayList<QubitInfo> qubits = new ArrayList<>();
        qubits.add(newQubit);
        regs.put(registerID, new QRegInfo(qubits, newRegister));
        return newQubit;
    }

    // Service functions
    // Данная функция проверяет, находятся ли кубиты в одном регистре.
    // Если кубиты находятся в различных регистрах, то эти регистры
    // сцепляются в один.
    // qubits - список кубитов для проверки
    // Возвращаемое значение: QRegInfo для регистра, включающего все
    // qubits вместе со сцепленными с ними (все кубиты запутаны).
    protected QRegInfo checkAndMergeRegistersIfNeedForQubits(
        QubitInfo... qubits
    ) throws Exception {
        ArrayList<String> usedRegisterAddresses = new ArrayList<>();
        for (QubitInfo qubit : qubits) {
            if (!usedRegisterAddresses.contains(qubit.regAddr)) {
                usedRegisterAddresses.add(qubit.regAddr);
            }
        }

        if (usedRegisterAddresses.size() == 1) {
            return regs.get(usedRegisterAddresses.get(0));
        }

        // Create new reg merged regs
        ComplexDouble[][] newRegConfig = {{Complex.unit()}};
        ArrayList <QubitInfo> newRegQubits = new ArrayList<QubitInfo>();
        String newRegisterAddress = Double.toString(new Date().getTime());

        for (String regAddr : usedRegisterAddresses) {
            QRegInfo currRegInfo = regs.get(regAddr);
            ComplexDouble[][] currRegInfoMat = currRegInfo.reg.getDensMat();
            newRegConfig = ComplexMath.tensorMultiplication(
                newRegConfig, newRegConfig.length, newRegConfig.length,
                currRegInfoMat, currRegInfoMat.length, currRegInfoMat.length);
            
            for (QubitInfo qubit : currRegInfo.qubits) {
                newRegQubits.add(qubit);
                qubit.regAddr = newRegisterAddress;
                qubit.idxInReg = newRegQubits.size() - 1;
            }
            regs.remove(regAddr);
        }

        QRegInfo newRegisterInfo = new QRegInfo(
            newRegQubits, new QReg(newRegQubits.size(), newRegConfig));
        regs.put(newRegisterAddress, newRegisterInfo);

        return newRegisterInfo;
    }

    // Применение преобразования с матрицей transitionMatrix, к кубитам qubits.
    // transitionMatrix - матрица преобразования
    // qubits - кубиты, к которым применяется преобразование
    // mergedRegisterInfo - регистр, содержащий все qubits
    // firstQubitAddressInRegister - минимальный индекс кубита в
    // регистре, к которому применяется преобразование
    protected void performTransitionForQubits (
        QubitInfo         controlQubit,
        ComplexDouble[][] transitionMatrix,
        QRegInfo          mergedRegisterInfo,
        QubitInfo ...     qubits
    ) throws Exception {
        ArrayList<Integer> qubitIndexes = new ArrayList<Integer>();
        for (QubitInfo q : qubits) {
            qubitIndexes.add(q.idxInReg);
        }

        int controlQubitIndex = NotAnIndex;
        if (controlQubit != null){
            controlQubitIndex = controlQubit.idxInReg;
        }

        mergedRegisterInfo.reg.performAlgorythm(new QAlgorithmOneStep(
            mergedRegisterInfo.qubits.size(),
            controlQubitIndex,
            qubitIndexes,
            transitionMatrix));
    }

    public void performTransitionForQubits (
        QubitInfo         controlQubit,
        ComplexDouble[][] transitionMatrix,
        QubitInfo ...     qubits
    ) throws Exception {
        ArrayList<QubitInfo> allQubits = new ArrayList<QubitInfo>();
        for (QubitInfo q: qubits){
            allQubits.add(q);
        }

        if (controlQubit != null) {
            allQubits.add(controlQubit);
        }

        QubitInfo[] qubitsArray = new QubitInfo[allQubits.size()];
        qubitsArray = allQubits.toArray(qubitsArray);
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(qubitsArray);
        performTransitionForQubits(controlQubit, transitionMatrix, info, qubits);
    }

    // Operations
    public int measure (QubitInfo qubit) throws Exception {
        //TODO: remove reg if qubits count is 0
        return regs.get(qubit.regAddr).reg.measureQubit(qubit.idxInReg);
    }

    public void opQET(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        ComplexDouble[][] matQET = generateMatQET(theta);
        performTransitionForQubits(null, matQET, info, a, b);
    }

    public void opPHASE(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        ComplexDouble[][] matPHASE = generateMatPHASE(theta);
        performTransitionForQubits(null, matPHASE, info, a, b);
    }

    public void opCQET(
        QubitInfo a, QubitInfo b, QubitInfo c, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, c, b);
        boolean isFirstC =  getIdxInReg(c) < getIdxInReg(a)
                         && getIdxInReg(c) < getIdxInReg(b);
        boolean isLastC  =  getIdxInReg(c) > getIdxInReg(a)
                         && getIdxInReg(c) > getIdxInReg(b);
        ComplexDouble[][] matCQET = generateMatCQET(theta, isFirstC, isLastC);
        performTransitionForQubits(null, matCQET, info, a, b, c);
    }

    private static ComplexDouble[][] generateMatQET(double theta) {
        return new ComplexDouble[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(Math.cos(theta / 2.0), 0.0),
                Complex.complex(0.0, Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(0.0, Math.sin(theta / 2.0)),
                Complex.complex(Math.cos(theta / 2.0), 0.0),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        };
    }

    private static ComplexDouble[][] generateMatPHASE(double theta) {
        return new ComplexDouble[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(Math.cos(-theta / 2.0), Math.sin(-theta / 2.0)),
                Complex.zero(),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.zero(),
                Complex.complex(Math.cos(theta / 2.0), Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        };
    }

    private static ComplexDouble[][] generateMatCQET(
        double theta, boolean isFirstC, boolean isLastC) {
        final ComplexDouble z  = Complex.zero();
        final ComplexDouble u  = Complex.unit();
        final ComplexDouble co = Complex.complex(Math.cos(theta / 2.0), 0.0);
        final ComplexDouble si = Complex.complex(0.0, Math.sin(theta / 2.0));

        if (isFirstC) {
            return new ComplexDouble[][] {
                {u,  z,  z, z, z, z, z, z},
                {z, co, si, z, z, z, z, z},
                {z, si, co, z, z, z ,z, z},
                {z,  z,  z, u, z, z, z, z},
                {z,  z,  z, z, u, z, z, z},
                {z,  z,  z, z, z, u, z, z},
                {z,  z,  z, z, z, z, u, z},
                {z,  z,  z, z, z, z, z, u}
            };
        } else if (isLastC) {
            return new ComplexDouble[][] {
                {z, z, z, z, u,  z,  z, z},
                {u, z, z, z, z,  z,  z, z},
                {z, z, z, z, z, co, si, z},
                {z, u, z, z, z,  z,  z, z},
                {z, z, z, z, z, si, co, z},
                {z, z, u, z, z,  z,  z, z},
                {z, z, z, z, z,  z,  z, u},
                {z, z, z, u, z,  z,  z, z}
            };
        } else {
            return new ComplexDouble[][] {
                {z, z, u,  z,  z, z, z, z},
                {z, z, z, co, si, z, z, z},
                {u, z, z,  z,  z, z, z, z},
                {z, u, z,  z,  z, z, z, z},
                {z, z, z, si, co, z, z, z},
                {z, z, z,  z,  z, u, z, z},
                {z, z, z,  z,  z, z, u, z},
                {z, z, z,  z,  z, z, z, u}
            };
        }
    }
}
