package kmqc.simulator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmqc.simulator.core.gates.QuantumGate;
import kmqc.simulator.util.Complex;
import kmqc.simulator.util.Matrix;

/**
 * Created by alexandrterentyev on 09.03.15.
 */

//Algorithm is a matrix. Each cell is the QSchemeStepQubitAttr
// (Map) gates :{gateID:specifications (Quantum gate)}.
// Generally quantum step and algorithm are quantum gates too.

public class QAlgo extends QuantumGate {

    public QAlgo() {
        this.nSteps = 0;
    }

    public QAlgo(
        QSchemeStepQubitAttr[][] algoSchemeMat,
        String[]                 mainGateIDs,
        Map<String, QuantumGate> gates) {
        this.algoSchemeMat = algoSchemeMat;
        this.gates         = gates;
        this.mainGateIDs   = mainGateIDs;
        this.nQubits       = algoSchemeMat.length;
        this.nSteps        = algoSchemeMat[0].length;
        this.size          = (int) Math.pow(2, nQubits);
    }

    Matrix generateStepMatrix(int step) throws Exception {
        int mainGateIndexesSum = 0;
        int count = 0;
        String mainGateID = this.mainGateIDs[step];
        List<Number> mainGateQubits = new ArrayList<>();

        for (int q = 0; q < this.nQubits; q++) {
            QSchemeStepQubitAttr qubitParams = this.algoSchemeMat[q][step];
            if (qubitParams.gateID.equals(mainGateID)) {
                mainGateIndexesSum += q;
                count++;
                mainGateQubits.add(q);
            }
        }

        if (checkAdjustment(mainGateQubits)) {
            return fun_if(step, mainGateQubits);
        } else {
            // Group one gate qubits
            // check Type of gravityCenter (maybe needs double)
            int gravityCenter = 0;
            if (count > 0)
                gravityCenter = mainGateIndexesSum / count;
            return fun_else(step, mainGateQubits, gravityCenter);
        }
    }

    private Matrix fun_if(
        int step, List<Number> mainGateQubits) throws Exception {

        //Move control qubit to top if need
        int idxControlQubit = -1;
        for (int i = 0; i < mainGateQubits.size(); i++) {
            int idxQubit = mainGateQubits.get(i).intValue();
            if (this.algoSchemeMat[idxQubit][step].control)
                idxControlQubit = idxQubit;
        }

        Matrix identityGateMat = identityGateMatrix();
        Matrix swapGateMat     = swapGateMatrix();
        Matrix swapMat         = null;
        if (idxControlQubit != -1) {
            int idxHigherQubit = mainGateQubits.get(0).intValue();

            for ( ; idxControlQubit > idxHigherQubit; idxControlQubit--) {
                Matrix currSwap = Matrix.identity(1);
                for (int i = 0; i < nQubits; )
                    if (i < nQubits-1 && i+1 == idxControlQubit) {
                        currSwap = currSwap.tensorTimes(swapGateMat);
                        idxControlQubit = i;
                        i += 2;
                    } else {
                        currSwap = currSwap.tensorTimes(identityGateMat);
                        i++;
                    }

                if(swapMat == null) {
                    swapMat = currSwap.clone();
                } else {
                    swapMat = swapMat.times(currSwap);
                }
            }
        }

        String mainGateID     = this.mainGateIDs[step];
        String identityGateID = QSchemeStepQubitAttr.IdentityGateID;
        Matrix mainGateMat    = this.gates.get(mainGateID).getMatrix();
        // central matrix
        Matrix cMat = Matrix.identity(1);
        // if qubits is near to each other just multiply identity gates
        // and mainGate matrices (tensors)
        for (int q = 0; q < nQubits; ) {
            QSchemeStepQubitAttr qubitParams = this.algoSchemeMat[q][step];
            if (qubitParams.gateID.equals(mainGateID)) {
                cMat = cMat.tensorTimes(mainGateMat);
                q   += mainGateQubits.size();
            } else if (qubitParams.gateID.equals(identityGateID)) {
                cMat = cMat.tensorTimes(identityGateMat);
                q++;
            } else {
                throw new Exception("Two non trivial gates at step!");
            }
        }

        if (swapMat == null) {
            return cMat;
        } else {
            return swapMat.times(cMat).times(swapMat.dagger());
        }
    }

    private Matrix fun_else(
        int          step,
        List<Number> mainGateQubits,
        int          gravityCenter) throws Exception {

        boolean[] currQubitsPositions = new boolean[this.nQubits];
        for (Number idx : mainGateQubits)
            currQubitsPositions[idx.intValue()] = true;

        int idxUpperQubit = mainGateQubits.get(0).intValue();
        //matrix perfomed main gate when all qubits are near
        int nLevels = mainGateQubits.size() / 2 + mainGateQubits.size() % 2;
        Matrix swapGateMat     = swapGateMatrix();
        Matrix identityGateMat = identityGateMatrix();
        Matrix swapMat         = null;
        for (int level = 0; level <= nLevels; level++) {
            //find upper and lower qubits. Upper index is less than lower index
            int upperQubit = -1; //empty
            int lowerQubit = -1; //empty
            int upperPlace = gravityCenter - level;
            int lowerPlace = gravityCenter + level;
            int idxUpper   = upperPlace;
            int idxLower   = lowerPlace;

            for (; idxUpper >= 0; idxUpper--)
                if (upperQubit == -1 && currQubitsPositions[idxUpper]) {
                    upperQubit = idxUpper;
                    currQubitsPositions[idxUpper]              = false;
                    currQubitsPositions[gravityCenter - level] = true;
                    break;
                }

            if (level > 0)
                for ( ; idxLower < nQubits; idxLower++)
                    if (lowerQubit == -1 && currQubitsPositions[idxLower]) {
                        lowerQubit = idxLower;
                        currQubitsPositions[idxLower]              = false;
                        currQubitsPositions[gravityCenter + level] = true;
                        break;
                    }

            int distance = Math.max(
                upperPlace - upperQubit, lowerQubit - lowerPlace);
            //move qubits to gravity center + level
            for ( ; distance > 0; distance--) {
                //form swap matrix
                Matrix distSwap = Matrix.identity(1);
                for (int i = 0; i < this.nQubits; )
                    if (
                        i == upperQubit   && upperQubit == upperPlace - distance
                    ||  i == lowerQubit-1 && lowerQubit == lowerPlace + distance
                    ){
                        //need to swap upper gate
                        if (i == upperQubit)
                            upperQubit++;

                        if (i == lowerQubit - 1)
                            lowerQubit--;

                        distSwap = distSwap.tensorTimes(swapGateMat);
                        i += 2;
                    } else {
                        distSwap = distSwap.tensorTimes(identityGateMat);
                        i++;
                    }

                if (swapMat == null) {
                    swapMat = distSwap.clone();
                } else {
                    swapMat = distSwap.times(swapMat);
                }
            }

            if (upperQubit != -1)
                idxUpperQubit = upperPlace;
        }

        //Move control qubit to top if need
        int idxControlQubit = -1;
        for (int i = 0; i < mainGateQubits.size(); i++)
            if (algoSchemeMat[mainGateQubits.get(i).intValue()][step].control)
                idxControlQubit = i;

        if (idxControlQubit != -1) {
            //project to current positions
            idxControlQubit += idxUpperQubit;
            for ( ; idxControlQubit > idxUpperQubit; idxControlQubit--) {
                Matrix currSwap = Matrix.identity(1);
                for (int i=0; i < this.nQubits; )
                    if (i < this.nQubits-1 && i+1 == idxControlQubit) {
                        currSwap = currSwap.tensorTimes(swapGateMat);
                        idxControlQubit = i;
                        i += 2;
                    } else {
                        currSwap = currSwap.tensorTimes(identityGateMat);
                        i++;
                    }

                swapMat = currSwap.times(swapMat);
            }
        }

        // central matrix
        Matrix cMat = Matrix.identity(1);
        String mainGateID  = this.mainGateIDs[step];
        Matrix mainGateMat = this.gates.get(mainGateID).getMatrix();
        //form central matrix after swaps
        for (int i = 0; i < nQubits; )
            if (i == gravityCenter - nLevels / 2) {
                cMat = cMat.tensorTimes(mainGateMat);
                i   += mainGateQubits.size();
            } else {
                cMat = cMat.tensorTimes(identityGateMat);
                i++;
            }

        return swapMat.dagger().times(cMat).times(swapMat);
    }

    private boolean checkAdjustment (List<Number> listToCheck) {
        int prev = listToCheck.get(0).intValue();
        for (int i = 1; i < listToCheck.size(); i++) {
            int current = listToCheck.get(i).intValue();
            if (current > prev + 1)
                return false;
            prev = current;
        }
        return true;
    }

    @Override
    public Matrix getMatrix() throws Exception {
        Matrix result = generateStepMatrix(this.nSteps - 1);
        for (int i = this.nSteps - 2; i >= 0; i--)
            result = result.times(generateStepMatrix(i));
        return result;
    }

    protected int nSteps;
    protected QSchemeStepQubitAttr[][] algoSchemeMat;
    protected String[] mainGateIDs;
    protected Map <String, QuantumGate> gates;
}