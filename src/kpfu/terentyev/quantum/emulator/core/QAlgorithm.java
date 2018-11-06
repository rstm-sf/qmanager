package kpfu.terentyev.quantum.emulator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kpfu.terentyev.quantum.emulator.core.gates.QuantumGate;
import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.Matrix;

/**
 * Created by alexandrterentyev on 09.03.15.
 */

//Algorithm is a matrix. Each cell is the QSchemeStepQubitAttr
// (Map) gates :{gateID:specifications (Quantum gate)}.
// Generally quantum step and algorithm are quantum gates too.

public class QAlgorithm extends QuantumGate {

    public QAlgorithm() {
        this.nSteps = 0;
    }

    public QAlgorithm(
        QSchemeStepQubitAttr[][] algSchemeMat,
        String[]                 mainGateIDs,
        Map<String, QuantumGate> gates) {
        this.algSchemeMat = algSchemeMat;
        this.gates        = gates;
        this.mainGateIDs  = mainGateIDs;
        this.nQubits      = algSchemeMat.length;
        this.nSteps       = algSchemeMat[0].length;
        this.size         = (int) Math.pow(2, nQubits);
    }

    Matrix generateStepMatrix(int step) throws Exception {
        int mainGateIndexesSum = 0;
        int count = 0;
        String mainGateID = this.mainGateIDs[step];
        List<Number> mainGateQubits = new ArrayList<>();

        for (int q = 0; q < this.nQubits; q++) {
            QSchemeStepQubitAttr qubitParams = this.algSchemeMat[q][step];
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
        
        // central matrix
        Matrix cMat = Matrix.identity(1);
        String mainGateID = this.mainGateIDs[step];
        Matrix gateMatrix = this.gates.get(mainGateID).getMatrix();
        // if qubits is near to each other just multiply identity gates
        // and mainGate matrices (tensors)
        for (int currQubit = 0; currQubit < nQubits; ) {
            QSchemeStepQubitAttr qubitParams =
                this.algSchemeMat[currQubit][step];
            if (qubitParams.gateID.equals(mainGateID)){
                currQubit += mainGateQubits.size();
                cMat = cMat.tensorTimes(gateMatrix);
            } else if (qubitParams.gateID.equals(
                QSchemeStepQubitAttr.IdentityGateID)
            ) {
                Matrix gateMatrx = identityGateMatrix();
                cMat = cMat.tensorTimes(gateMatrx);
                currQubit++;
            } else {
                throw new Exception("Two non trivial gates at step!");
            }
        }

        //Move control qubit to top if need
        int controlQubitIndex = -1;
        for (int i = 0; i < mainGateQubits.size(); i++) {
            int qubitIndex = mainGateQubits.get(i).intValue();
            if (this.algSchemeMat[qubitIndex][step].control)
                controlQubitIndex = qubitIndex;
        }

        Matrix result = Matrix.identity(1);
        List<Matrix> swapMat = new ArrayList<>();
        if (controlQubitIndex != -1) {
            Matrix swapGateMatrix = swapGateMatrix();
            Matrix identityMatrx = identityGateMatrix();
            int higherQubitIndex = mainGateQubits.get(0).intValue();

            for ( ;
                controlQubitIndex > higherQubitIndex;
                controlQubitIndex--) {
                Matrix currSwap = Matrix.identity(1);
                for (int i = 0; i < nQubits; ) {
                    if (i < nQubits-1 && i+1 == controlQubitIndex) {
                        currSwap = currSwap.tensorTimes(swapGateMatrix);
                        controlQubitIndex = i;
                        i += 2;
                    } else {
                        currSwap = currSwap.tensorTimes(identityMatrx);
                        i++;
                    }
                }
                swapMat.add(currSwap);
            }

            if (swapMat.size() > 0) {
                result = swapMat.get(0).clone();
                for (int i = 1 ; i < swapMat.size(); i++)
                    result = result.times(swapMat.get(i));

                Matrix resultDagger = result.dagger();
                result = result.times(cMat).times(resultDagger);
            } else {
                result = cMat;
            }
        } else {
            result = cMat;
        }
        return result;
    }

    private Matrix fun_else(
        int          step,
        List<Number> mainGateQubits,
        int          gravityCenter) throws Exception {

        boolean[] currQubitsPositions = new boolean[this.nQubits];
        for (Number idx : mainGateQubits)
            currQubitsPositions[idx.intValue()] = true;

        int currUpQubitIdx = mainGateQubits.get(0).intValue();
        //matrix perfomed main gate when all qubits are near
        Matrix swapMat = null;
        Matrix swapGateMatrix = swapGateMatrix();
        Matrix identityMatrx = identityGateMatrix();
        int nLevel = mainGateQubits.size() / 2 + mainGateQubits.size() % 2;
        for (int level = 0; level <= nLevel; level++) {
            //find upper and lower qubits. Upper index is less than lower index
            int upperQubit = -1; //empty
            int lowerQubit = -1; //empty
            int upperPlace = gravityCenter - level;
            int lowerPlace = gravityCenter + level;
            int upperIndex = upperPlace;
            int lowerIndex = lowerPlace;

            for (; upperIndex >= 0; upperIndex--)
                if (upperQubit==-1 && currQubitsPositions[upperIndex]) {
                    upperQubit = upperIndex;
                    currQubitsPositions[upperIndex]            = false;
                    currQubitsPositions[gravityCenter - level] = true;
                    break;
                }

            if (level > 0)
                for ( ; lowerIndex < nQubits; lowerIndex++) {
                    if (lowerQubit == -1 && currQubitsPositions[lowerIndex]) {
                        lowerQubit = lowerIndex;
                        currQubitsPositions[lowerIndex]            = false;
                        currQubitsPositions[gravityCenter + level] = true;
                        break;
                    }
                }

            int distance = Math.max(
                upperPlace - upperQubit, lowerQubit - lowerPlace);
            //move qubits to gravity center + level
            for ( ; distance > 0; distance--) {
                //form swap matrix
                Matrix currentDistanceSwap = Matrix.identity(1);
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

                        currentDistanceSwap = currentDistanceSwap.tensorTimes(
                            swapGateMatrix);
                        i += 2;
                    } else {
                        currentDistanceSwap = currentDistanceSwap.tensorTimes(
                            identityMatrx);
                        i++;
                    }

                if (swapMat == null) {
                    swapMat = currentDistanceSwap.clone();
                } else {
                    swapMat = currentDistanceSwap.times(swapMat);
                }
            }

            if (upperQubit != -1)
                currUpQubitIdx = upperPlace;
        }

        //Move control qubit to top if need
        int controlQubitIndex = -1;
        for (int i = 0; i < mainGateQubits.size(); i++)
            if (algSchemeMat[mainGateQubits.get(i).intValue()][step].control)
                controlQubitIndex = i;

        if (controlQubitIndex != -1) {
            //project to current positions
            controlQubitIndex = currUpQubitIdx + controlQubitIndex;
            for ( ; controlQubitIndex > currUpQubitIdx; controlQubitIndex--) {
                Matrix currSwap = Matrix.identity(1);
                for (int i=0; i < this.nQubits; )
                    if (i < this.nQubits-1 && i+1 == controlQubitIndex) {
                        currSwap = currSwap.tensorTimes(swapGateMatrix);
                        controlQubitIndex = i;
                        i += 2;
                    } else {
                        currSwap = currSwap.tensorTimes(identityMatrx);
                        i++;
                    }

                swapMat = currSwap.times(swapMat);
            }
        }

        // central matrix
        Matrix cMat = Matrix.identity(1);
        String mainGateID = this.mainGateIDs[step];
        Matrix gateMatrix = this.gates.get(mainGateID).getMatrix();
        //form central matrix after swaps
        for (int i = 0; i < nQubits; )
            if (i == gravityCenter - nLevel / 2) {
                cMat = cMat.tensorTimes(gateMatrix);
                i   += mainGateQubits.size();
            } else {
                cMat = cMat.tensorTimes(identityMatrx);
                i++;
            }

        Matrix swapMatDagger = swapMat.dagger();
        return swapMatDagger.times(cMat).times(swapMat);
    }

    private boolean checkAdjustment (List<Number> listToCheck) {
        for (int i = 1; i < listToCheck.size(); i++)
            if (
                listToCheck.get(i).intValue() >
                listToCheck.get(i-1).intValue() + 1
            ) {
                return false;
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
    protected QSchemeStepQubitAttr[][] algSchemeMat;
    protected String[] mainGateIDs;
    protected Map <String, QuantumGate> gates;
}