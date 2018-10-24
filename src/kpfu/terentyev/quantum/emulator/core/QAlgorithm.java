package kpfu.terentyev.quantum.emulator.core;

import java.util.ArrayList;
import java.util.Map;

import kpfu.terentyev.quantum.emulator.core.gates.QuantumGate;
import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexMath;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by alexandrterentyev on 09.03.15.
 */

//Algorithm is a matrix. Each cell is the QSchemeStepQubitAttributes
// (Map) gates :{gateID:specifications (Quantum gate)}.
// Generally quantum step and algorithm are quantum gates too.

public class QAlgorithm extends QuantumGate {

    int stepsNumber;
    QSchemeStepQubitAttributes [][] algorithmSchemeMatrix;
    String [] mainGateIDs;
    Map <String, QuantumGate> gates;

    public  QAlgorithm(){
        stepsNumber = 0;
    }

    public QAlgorithm(
        QSchemeStepQubitAttributes[][] algorithmSchemeMatrix,
        String[]                       mainGateIDs,
        Map<String, QuantumGate>       gates) {
        this.algorithmSchemeMatrix = algorithmSchemeMatrix;
        this.gates = gates;
        this.mainGateIDs = mainGateIDs;
        qubitsNumber = algorithmSchemeMatrix.length;
        stepsNumber= algorithmSchemeMatrix[0].length;
        size = (int) Math.pow(2, qubitsNumber);
    }

    ComplexDouble[][] generateStepMatrix(int step) throws Exception {
        int mainGateIndexesSum = 0;
        int count=0;
        String mainGateID = mainGateIDs[step];
        ArrayList<Number> mainGateQubits = new ArrayList<Number>();

        for (int qubitNum = 0; qubitNum < qubitsNumber; qubitNum++) {
            QSchemeStepQubitAttributes qubitParams = 
                algorithmSchemeMatrix[qubitNum][step];
            if (qubitParams.gateID.equals(mainGateID)) {
                mainGateIndexesSum += qubitNum;
                count++;
                mainGateQubits.add(qubitNum);
            }
        }

        ComplexDouble[][] gateMatrix = gates.get(mainGateID).getMatrix();
        ComplexDouble[][] result = {{Complex.unit()}};
        if (checkAdjustment(mainGateQubits)) {
            ComplexDouble centralMatr[][] = {{Complex.unit()}};
            // if qubits is near to each other just multiply identity gates
            // and mainGate matrices (tensors)
            for (int currentQubit=0; currentQubit < qubitsNumber; ) {
                QSchemeStepQubitAttributes qubitParams =
                    algorithmSchemeMatrix[currentQubit][step];
                if (qubitParams.gateID.equals(mainGateID)){
                    currentQubit += mainGateQubits.size();
                    centralMatr = ComplexMath.tensorMultiplication(centralMatr,centralMatr.length,centralMatr.length,
                            gateMatrix, gateMatrix.length, gateMatrix.length);
                } else if (qubitParams.gateID.equals(
                    QSchemeStepQubitAttributes.IdentityGateID)) {
                    ComplexDouble[][] gateMatrx = identityGateMatrix();
                    centralMatr =ComplexMath.tensorMultiplication(
                        centralMatr,centralMatr.length,centralMatr.length,
                        gateMatrx, gateMatrx.length, gateMatrx.length);
                    currentQubit++;
                } else {
                    throw new Exception("Two non trivial gates at step!");
                }
            }

            //Move control qubit to top if need
            int controlQubitIndex = -1;
            for (int i = 0; i<mainGateQubits.size(); i++) {
                int qubitIndex = mainGateQubits.get(i).intValue();
                if (algorithmSchemeMatrix[qubitIndex][step].control)
                    controlQubitIndex= qubitIndex;
            }

            ArrayList <ComplexDouble[][]> swapMatrices = new ArrayList<>();
            
            if (controlQubitIndex != -1) {
                ComplexDouble[][] swapGateMatrix = swapGateMatrix();
                ComplexDouble[][] identityMatrx = identityGateMatrix();
                int higherQubitIndex = mainGateQubits.get(0).intValue();

                for ( ;
                    controlQubitIndex > higherQubitIndex;
                    controlQubitIndex--) {
                    ComplexDouble currentSwap[][] = {{Complex.unit()}};
                    for (int i = 0; i < qubitsNumber; ) {
                        if (i < qubitsNumber-1 && i+1 == controlQubitIndex) {
                            currentSwap = ComplexMath.tensorMultiplication(
                                currentSwap,
                                currentSwap.length, currentSwap.length,
                                swapGateMatrix,
                                swapGateMatrix.length, swapGateMatrix.length);
                            controlQubitIndex = i;
                            i += 2;
                        } else {
                            currentSwap = ComplexMath.tensorMultiplication(
                                currentSwap,
                                currentSwap.length, currentSwap.length,
                                identityMatrx,
                                identityMatrx.length, identityMatrx.length);
                            i++;
                        }
                    }
                    swapMatrices.add(currentSwap);
                }

                if (swapMatrices.size() > 0) {
                    result = swapMatrices.get(0).clone();
                    for (int i = 1 ; i < swapMatrices.size(); i++) {
                        result=ComplexMath.squareMatricesMultiplication(
                            result, swapMatrices.get(i), result.length);
                    }

                    ComplexDouble[][] swapConj = ComplexMath.
                        hermitianTransposeForMatrix(
                            result, result.length, result.length);

                    result = ComplexMath.squareMatricesMultiplication(
                        result, centralMatr, result.length);
                    result = ComplexMath.squareMatricesMultiplication(
                        result, swapConj, result.length);
                } else {
                    result = centralMatr;
                }
            } else {
                result = centralMatr;
            }

        } else {
            // Group one gate qubits
            // check Type of gravityCenter (maybe needs double)
            int gravityCenter=0;
            if (count>0){
                gravityCenter = mainGateIndexesSum/count;
            }

            int currentUpperQubitIdex = mainGateQubits.get(0).intValue();

            int upperQubit, lowerQubit;
            int levelNumber = 
                mainGateQubits.size() / 2 +
                mainGateQubits.size() % 2;
//            ArrayList <Complex[][]> swapMatrices = new ArrayList<Complex[][]>();
            //matrix perfomed main gate when all qubits are near
            ComplexDouble[][] centralMatrix = {{Complex.unit()}};
            ComplexDouble[][] swapGateMatrix = swapGateMatrix();
            ComplexDouble[][] identityMatrx = identityGateMatrix();

            ComplexDouble[][] swapMatrix = null;

            boolean[] currentQubitsPositions = new boolean[qubitsNumber];
            for (Number index: mainGateQubits){
                currentQubitsPositions[index.intValue()] = true;
            }

            for (int level = 0; level <= levelNumber; level++){
                //find upper and lower qubits. Upper index is less than lower index
                upperQubit=-1; lowerQubit=-1; //empty
                int upperPlace = gravityCenter-level;
                int lowerPlace = gravityCenter+level;
                int upperIndex = upperPlace;
                int lowerIndex = lowerPlace;

                int distance;
                for (; upperIndex>=0; upperIndex--){
                    if (upperQubit==-1 && currentQubitsPositions[upperIndex]){
                        upperQubit=upperIndex;
                        currentQubitsPositions[upperIndex] = false;
                        currentQubitsPositions[gravityCenter-level] = true;
                        break;
                    }
                }

                if (level>0) {
                    for (; lowerIndex < qubitsNumber; lowerIndex++) {
                        if (lowerQubit == -1 && currentQubitsPositions[lowerIndex]) {
                            lowerQubit = lowerIndex;
                            currentQubitsPositions[lowerIndex] = false;
                            currentQubitsPositions[gravityCenter+level] = true;
                            break;
                        }
                    }
                }

                distance = Math.max(upperPlace-upperQubit, lowerQubit-lowerPlace);

                //move qubits to gravity center + level
                for (; distance>0; distance--){
                    //form swap matrix
                    ComplexDouble currentDistanceSwap[][] = {{Complex.unit()}};
                    for (int i=0; i<qubitsNumber; ){
                        if ((i==upperQubit && upperQubit==upperPlace-distance) ||
                                (i==lowerQubit-1 && lowerQubit==lowerPlace+distance)){
                            //need to swap upper gate

                            if (i == upperQubit){
                                upperQubit++;
                            }

                            if (i == lowerQubit - 1){
                                lowerQubit--;
                            }

                            currentDistanceSwap = ComplexMath.tensorMultiplication(currentDistanceSwap,
                                    currentDistanceSwap.length, currentDistanceSwap.length,
                                    swapGateMatrix,
                                    swapGateMatrix.length, swapGateMatrix.length);
                            i+=2;
                        }else {
                            currentDistanceSwap = ComplexMath.tensorMultiplication(currentDistanceSwap, currentDistanceSwap.length,
                                    currentDistanceSwap.length, identityMatrx,
                                    identityMatrx.length, identityMatrx.length);
                            i++;
                        }
                    }
//                    swapMatrices.add(currentDistanceSwap);
                    if (swapMatrix == null){
                        swapMatrix = currentDistanceSwap.clone();
                    }else {
                        swapMatrix = ComplexMath.squareMatricesMultiplication(currentDistanceSwap, swapMatrix,
                                swapMatrix.length);
                    }
                }


                if (upperQubit != -1){
                    currentUpperQubitIdex = upperPlace;
                }
            }

            //Move control qubit to top if need
            int controlQubitIndex = -1;
            for (int i=0; i<mainGateQubits.size(); i++){
                if (algorithmSchemeMatrix[mainGateQubits.get(i).intValue()][step].control)
                    controlQubitIndex=i;
            }
            if (controlQubitIndex!=-1) {
                //project to current positions
                controlQubitIndex = currentUpperQubitIdex + controlQubitIndex;
                for (; controlQubitIndex > currentUpperQubitIdex; controlQubitIndex--){
                    ComplexDouble currentSwap[][] = {{Complex.unit()}};
                    for (int i=0; i<qubitsNumber;){
                        if (i < qubitsNumber - 1 && i+1==controlQubitIndex){
                            currentSwap = ComplexMath.tensorMultiplication(currentSwap,
                                    currentSwap.length, currentSwap.length,
                                    swapGateMatrix, swapGateMatrix.length, swapGateMatrix.length);
                            controlQubitIndex = i;
                            i+=2;
                        }else {
                            currentSwap = ComplexMath.tensorMultiplication(currentSwap, currentSwap.length,
                                    currentSwap.length, identityMatrx,
                                    identityMatrx.length, identityMatrx.length);
                            i++;
                        }
                    }
//                    swapMatrices.add(currentSwap);
                    swapMatrix = ComplexMath.squareMatricesMultiplication(currentSwap, swapMatrix, swapMatrix.length);
                }
            }
            //form central matrix after swaps
            for (int i=0; i<qubitsNumber;){
                if (i==gravityCenter-levelNumber/2){
                    centralMatrix = ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            gateMatrix, gateMatrix.length, gateMatrix.length);
                    i+=mainGateQubits.size();
                }else {
                    centralMatrix = ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            identityMatrx, identityMatrx.length, identityMatrx.length);
                    i++;
                }
            }
            ComplexDouble [][] swapConjugateMatrix = ComplexMath.hermitianTransposeForMatrix(swapMatrix,
                    swapMatrix.length, swapMatrix.length);
            result = ComplexMath.squareMatricesMultiplication(centralMatrix, swapMatrix,swapMatrix.length);
            result = ComplexMath.squareMatricesMultiplication(swapConjugateMatrix, result, swapMatrix.length);

            //form common matrix, using matrix associative property, mult all matrices
//            result=swapMatrices.get(0).clone();
//            for (int i=1 ; i<swapMatrices.size(); i++){
//                result=ComplexMath.squareMatricesMultiplication(result, swapMatrices.get(i), result.length);
//            }
//            result=ComplexMath.squareMatricesMultiplication(result, centralMatrix, result.length);
//            for (int i=swapMatrices.size()-1 ; i>=0; i--){
//                result=ComplexMath.squareMatricesMultiplication(result, swapMatrices.get(i), result.length);
//            }
        }
        return result;
    }

    boolean checkAdjustment (ArrayList<Number> listToCheck) {
        for (int i = 1; i < listToCheck.size(); i++){
            if (
                listToCheck.get(i).intValue() >
                listToCheck.get(i-1).intValue() + 1
            ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ComplexDouble[][] getMatrix() throws Exception {
        ComplexDouble[][] result = generateStepMatrix(stepsNumber - 1);
        for (int i = stepsNumber-2; i >= 0; i--) {
            result=ComplexMath.squareMatricesMultiplication(
                result, generateStepMatrix(i), result.length);
        }
        return result;
    }
}