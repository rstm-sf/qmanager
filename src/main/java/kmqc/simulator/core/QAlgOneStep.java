package kmqc.simulator.core;

import java.util.HashMap;
import java.util.List;

import kmqc.simulator.core.gates.QuantumGate;
import kmqc.simulator.core.gates.UGate;
import kmqc.simulator.util.Matrix;

/**
 * Created by aleksandrterentev on 02.04.16.
 */

/**
 * Algorythm matrix must math to qubits order in algorythm scheme.
 *
 * To not use controlQubit pass value NotAnIndex
 * */

public class QAlgOneStep extends QAlgo {

    public static final int NotAnIndex = -1;

    public QAlgOneStep(
        int           nQubits,
        int           idxControlQubit,
        List<Integer> idxGateQubits,
        Matrix        transformMat
    ) throws Exception {
        QSchemeStepQubitAttr[][] algoMat = new QSchemeStepQubitAttr[nQubits][1];
        QuantumGate gate = new UGate(idxGateQubits.size(), transformMat);
        String gateId = "Gate";
        for (int i = 0; i < nQubits; i++)
            //if (idxGateQubits.contains(new Integer(i))){ //! deprecated
            if (idxGateQubits.contains(Integer.valueOf(i))) {
                algoMat[i][0] = new QSchemeStepQubitAttr(gateId, false);
            } else if (i == idxControlQubit) {
                algoMat[i][0] = new QSchemeStepQubitAttr(gateId, true);
            } else {
                algoMat[i][0] = new QSchemeStepQubitAttr();
            }
        
        this.gates         = new HashMap<String, QuantumGate>();
        this.gates.put(gateId, gate);
        this.mainGateIDs   = new String[] { gateId };
        this.algoSchemeMat = algoMat;
        this.nQubits       = nQubits;
        this.nSteps        = 1;
        this.size          = (int) Math.pow(2, nQubits);
    }
}
