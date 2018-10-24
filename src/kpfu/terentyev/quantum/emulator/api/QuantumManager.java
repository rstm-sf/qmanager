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

public class QuantumManager {

    public static class Qubit {
        // Можно было сделать его частным случаем регистра,
        // но пока удобнее хранить идентификаторы регистров
        // и их номер в регистре
        String registerAddress;
        int addressInRegister;

        public Qubit (String registerAddress, int addressInRegister) {
            this.registerAddress = registerAddress;
            this.addressInRegister = addressInRegister;
        }
    }

    protected static class RegInfo {
        public QRegister register;
        public ArrayList<Qubit> qubits;

        public RegInfo(ArrayList<Qubit> qubits, QRegister register) {
            this.qubits = qubits;
            this.register = register;
        }
    }

    // This class must contain quantum registeres
    //protected HashMap <String, RegInfo> registers = new HashMap<String, RegInfo>();
    protected HashMap <String, RegInfo> registers;

    //protected static final String qubitDestroyedRegAddr = "Qubit destroyed";

    // Qubit creation
    public Qubit initNewQubit() throws Exception {
        return initNewQubit(Complex.unit(), Complex.zero());
    }

    public Qubit initNewQubit(
        ComplexDouble alpha, ComplexDouble beta
    ) throws Exception {
        QRegister newRegister = new QRegister(
            1, new ComplexDouble[]{alpha, beta});
        String registerID = Double.toString(
            new Date().getTime() + new Random().nextDouble());
        Qubit newQubit = new Qubit(registerID, 0);
        ArrayList<Qubit> qubits = new ArrayList<>();
        qubits.add(newQubit);
        registers.put(registerID, new RegInfo(qubits, newRegister));
        return newQubit;
    }

    // Service functions
    protected RegInfo checkAndMergeRegistersIfNeedForQubits(
        Qubit... qubits
    ) throws Exception {
        ArrayList<String> usedRegisterAddresses = new ArrayList<>();
        for (Qubit qubit : qubits) {
            if (!usedRegisterAddresses.contains(qubit.registerAddress)) {
                usedRegisterAddresses.add(qubit.registerAddress);
            }
        }

        if (usedRegisterAddresses.size() == 1){
            return registers.get(usedRegisterAddresses.get(0));
        }

        // Create new register merged registers
        ComplexDouble[][] newRegConfig = {{Complex.unit()}};
        ArrayList <Qubit> newRegQubits = new ArrayList<Qubit>();
        String newRegisterAddress = Double.toString(new Date().getTime());

        for (String registerAddress : usedRegisterAddresses) {
            RegInfo currRegInfo = registers.get(registerAddress);
            ComplexDouble[][] currRegInfoMat = currRegInfo.
                                               register.getDensityMatrix();
            newRegConfig = ComplexMath.tensorMultiplication(
                newRegConfig, newRegConfig.length, newRegConfig.length,
                currRegInfoMat, currRegInfoMat.length, currRegInfoMat.length);
            
            for (Qubit qubit : currRegInfo.qubits) {
                newRegQubits.add(qubit);
                qubit.registerAddress = newRegisterAddress;
                qubit.addressInRegister = newRegQubits.size() - 1;
            }
            registers.remove(registerAddress);
        }

        RegInfo newRegisterInfo = new RegInfo(
            newRegQubits, 
            new QRegister(newRegQubits.size(), newRegConfig));
        registers.put(newRegisterAddress, newRegisterInfo);

        return newRegisterInfo;
    }

    protected int qubitAddressInRegister(Qubit q) {
        return q.addressInRegister;
    }

    protected String registerAddressOfQubit(Qubit q) {
        return q.registerAddress;
    }

    protected void performTransitionForQubits (
        Qubit             controlQubit,
        ComplexDouble[][] transitionMatrix,
        RegInfo           mergedRegisterInfo,
        Qubit ...         qubits
    ) throws Exception {
        ArrayList<Integer> qubitIndexes = new ArrayList<Integer>();
        for (Qubit q : qubits) {
            qubitIndexes.add(q.addressInRegister);
        }

        int controlQubitIndex = NotAnIndex;
        if (controlQubit != null){
            controlQubitIndex = controlQubit.addressInRegister;
        }

        mergedRegisterInfo.register.performAlgorythm(new QAlgorithmOneStep(
            mergedRegisterInfo.qubits.size(),
            controlQubitIndex,
            qubitIndexes,
            transitionMatrix));
    }

    public void performTransitionForQubits (
        Qubit             controlQubit,
        ComplexDouble[][] transitionMatrix,
        Qubit ...         qubits
    ) throws Exception {
        ArrayList<Qubit> allQubits = new ArrayList<Qubit>();
        for (Qubit q: qubits){
            allQubits.add(q);
        }

        if (controlQubit != null) {
            allQubits.add(controlQubit);
        }

        Qubit[] qubitsArray = new Qubit[allQubits.size()];
        qubitsArray = allQubits.toArray(qubitsArray);
        RegInfo info = checkAndMergeRegistersIfNeedForQubits(qubitsArray);
        performTransitionForQubits(controlQubit, transitionMatrix, info, qubits);
    }

    // Operations
    public int measure (Qubit qubit) throws Exception {
//        int result = regInfo.register.measureQubit(qubit.addressInRegister);
//        int qubitPosition = regInfo.qubits.indexOf(qubit);
//        regInfo.qubits.remove(qubitPosition);
//        for (int i=qubitPosition; i< regInfo.qubits.size(); i++){
//            regInfo.qubits.get(i).addressInRegister --;
//        }
//        qubit.registerAddress = qubitDestroyedRegAddr;
//        TODO: remove register if qubits count is 0
        return registers.get(qubit.registerAddress).register.measureQubit(
            qubit.addressInRegister);
    }
}
