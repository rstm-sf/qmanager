package kpfu.terentyev.quantum.emulator.core;

import java.util.HashMap;
import java.util.List;

import kpfu.terentyev.quantum.emulator.core.gates.QuantumGate;
import kpfu.terentyev.quantum.emulator.core.gates.UGate;
import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by aleksandrterentev on 02.04.16.
 */

/**
 * Algorythm matrix must math to qubits order in algorythm scheme.
 *
 * To not use controlQubit pass value NotAnIndex
 * */

public class QAlgorithmOneStep extends QAlgorithm {

    public static final int NotAnIndex = -1;

    public QAlgorithmOneStep(
        int               qubitsInRegister,
        int               controlQubitIndex,
        List<Integer>     gateQubitIndexes,
        ComplexDouble[][] transformationMatrix
    ) throws Exception {
        stepsNumber = 1;
        QSchemeStepQubitAttributes[][] algSheme = 
            new QSchemeStepQubitAttributes[qubitsInRegister][1];
        String gateId = "Gate";
        for (int i = 0; i < qubitsInRegister; i++) {
            //if (gateQubitIndexes.contains(new Integer(i))){ //! deprecated
            if (gateQubitIndexes.contains(Integer.valueOf(i))) {
                algSheme[i][0] = new QSchemeStepQubitAttributes(gateId, false);
            } else if (i == controlQubitIndex){
                algSheme[i][0] = new QSchemeStepQubitAttributes(gateId, true);
            } else {
                algSheme[i][0] = new QSchemeStepQubitAttributes();
            }
        }
        gates = new HashMap<String, QuantumGate>();
        mainGateIDs = new String[]{gateId};
        QuantumGate gate = new UGate(
            gateQubitIndexes.size(), transformationMatrix);
        gates.put(gateId, gate);
        algorithmSchemeMatrix = algSheme;
        qubitsNumber = qubitsInRegister;
        size = (int) Math.pow(2, qubitsNumber);
    }
}
