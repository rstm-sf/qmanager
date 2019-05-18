package kmqc.simulator.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import kmqc.simulator.core.*;
import kmqc.simulator.util.Complex;
import kmqc.simulator.util.Matrix;
import kmqc.simulator.util.Vector;

import static kmqc.simulator.core.QAlgOneStep.NotAnIndex;

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
        public List<QubitInfo> qubits;

        public QRegInfo(List<QubitInfo> qubits, QReg reg) {
            this.qubits = qubits;
            this.reg = reg;
        }
    }

    // QubitInfo creation
    public QubitInfo initNewQubit() throws Exception {
        return initNewQubit(Complex.unit(), Complex.zero());
    }

    public QubitInfo initNewQubit(
        Complex alpha, Complex beta
    ) throws Exception {
        Vector vec             = new Vector(new Complex[] { alpha, beta });
        QReg newRegister       = new QReg(1, vec);
        String registerID      = Double.toString(
            new Date().getTime() + new Random().nextDouble());
        QubitInfo newQubit     = new QubitInfo(registerID, 0);
        List<QubitInfo> qubits = new ArrayList<>();
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
        List<String> usedRegisterAddresses = new ArrayList<>();
        for (QubitInfo qubit : qubits) {
            if (!usedRegisterAddresses.contains(qubit.regAddr)) {
                usedRegisterAddresses.add(qubit.regAddr);
            }
        }

        if (usedRegisterAddresses.size() == 1)
            return regs.get(usedRegisterAddresses.get(0));

        // Create new reg merged regs
        Matrix newRegConfig           = Matrix.identity(1);
        List <QubitInfo> newRegQubits = new ArrayList<>();
        String newRegAddr             = Double.toString(new Date().getTime());
        for (String regAddr : usedRegisterAddresses) {
            QRegInfo currRegInfo  = regs.get(regAddr);
            Matrix currRegInfoMat = currRegInfo.reg.getDensMat();
            newRegConfig          = newRegConfig.tensorTimes(currRegInfoMat);
            
            for (QubitInfo qubit : currRegInfo.qubits) {
                newRegQubits.add(qubit);
                qubit.regAddr  = newRegAddr;
                qubit.idxInReg = newRegQubits.size() - 1;
            }
            regs.remove(regAddr);
        }

        QRegInfo newRegInfo = new QRegInfo(
            newRegQubits, new QReg(newRegQubits.size(), newRegConfig));
        regs.put(newRegAddr, newRegInfo);
        return newRegInfo;
    }

    // Применение преобразования с матрицей transitMat, к кубитам qubits.
    // transitMat - матрица преобразования
    // qubits - кубиты, к которым применяется преобразование
    // mergedRegisterInfo - регистр, содержащий все qubits
    // firstQubitAddressInRegister - минимальный индекс кубита в
    // регистре, к которому применяется преобразование
    protected void performTransitionForQubits (
        QubitInfo    controlQubit,
        Matrix       transitMat,
        QRegInfo     mergedRegisterInfo,
        QubitInfo... qubits
    ) throws Exception {
        List<Integer> idxQubits = new ArrayList<>();
        for (QubitInfo q : qubits)
            idxQubits.add(q.idxInReg);

        int idxControlQubit = NotAnIndex;
        if (controlQubit != null)
            idxControlQubit = controlQubit.idxInReg;

        mergedRegisterInfo.reg.performAlgorythm(new QAlgOneStep(
            mergedRegisterInfo.qubits.size(),
            idxControlQubit,
            idxQubits,
            transitMat));
    }

    // Operations
    public int measure (QubitInfo qubit) throws Exception {
        //TODO: remove reg if qubits count is 0
        return regs.get(qubit.regAddr).reg.measureQubit(qubit.idxInReg);
    }
}
