package kpfu.terentyev.quantum.emulator.core;

import java.util.HashMap;
import java.util.List;

import kpfu.terentyev.quantum.emulator.core.gates.QuantumGate;
import kpfu.terentyev.quantum.emulator.core.gates.UGate;
import kpfu.terentyev.quantum.util.Matrix;

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
        int           qubitsInRegister,
        int           controlQubitIndex,
        List<Integer> gateQubitIndexes,
        Matrix        transformationMatrix
    ) throws Exception {
        QSchemeStepQubitAttr[][] algSheme = 
            new QSchemeStepQubitAttr[qubitsInRegister][1];
        String gateId = "Gate";
        for (int i = 0; i < qubitsInRegister; i++)
            //if (gateQubitIndexes.contains(new Integer(i))){ //! deprecated
            if (gateQubitIndexes.contains(Integer.valueOf(i))) {
                algSheme[i][0] = new QSchemeStepQubitAttr(gateId, false);
            } else if (i == controlQubitIndex){
                algSheme[i][0] = new QSchemeStepQubitAttr(gateId, true);
            } else {
                algSheme[i][0] = new QSchemeStepQubitAttr();
            }

        QuantumGate gate = new UGate(
            gateQubitIndexes.size(), transformationMatrix);
        
        this.gates        = new HashMap<String, QuantumGate>();
        this.gates.put(gateId, gate);
        this.mainGateIDs  = new String[]{gateId};
        this.algSchemeMat = algSheme;
        this.nQubits      = qubitsInRegister;
        this.nSteps       = 1;
        this.size         = (int) Math.pow(2, nQubits);
    }
}
