package kpfu.terentyev.quantum.emulator.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import kpfu.terentyev.quantum.emulator.core.*;
import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;
import kpfu.terentyev.quantum.util.Matrix;
import kpfu.terentyev.quantum.util.Vector;

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
        return initNewQubit(Complex.unit(), Complex.zero());
    }

    public QubitInfo initNewQubit(
        ComplexDouble alpha, ComplexDouble beta
    ) throws Exception {
        Vector vec = new Vector(new ComplexDouble[]{alpha, beta});
        QReg newRegister = new QReg(1, vec);
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
        Matrix newRegConfig = Matrix.identity(1);
        ArrayList <QubitInfo> newRegQubits = new ArrayList<QubitInfo>();
        String newRegisterAddress = Double.toString(new Date().getTime());

        for (String regAddr : usedRegisterAddresses) {
            QRegInfo currRegInfo = regs.get(regAddr);
            Matrix currRegInfoMat = currRegInfo.reg.getDensMat();
            newRegConfig = newRegConfig.tensorTimes(currRegInfoMat);
            
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
        QubitInfo     controlQubit,
        Matrix        transitionMatrix,
        QRegInfo      mergedRegisterInfo,
        QubitInfo ... qubits
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
        QubitInfo     controlQubit,
        Matrix        transitionMatrix,
        QubitInfo ... qubits
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
}
