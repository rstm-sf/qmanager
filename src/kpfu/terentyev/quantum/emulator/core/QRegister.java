package kpfu.terentyev.quantum.emulator.core;

import java.util.Random;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexMath;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QRegister {
    private int qubitsNumber;
    private int size;
    private ComplexDouble [][] densityMatrix;

    public QRegister (int qubitsNumber) {
        this.setQubitsNumber(qubitsNumber);
    }

    public QRegister (
        int qubitsNumber, ComplexDouble[][] densityMatrix
    ) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.densityMatrix =densityMatrix;
        if (size != densityMatrix.length){
            throw new Exception();
        }
    }

    public QRegister (
        int qubitsNumber, ComplexDouble [] configuration
    ) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.densityMatrix = densityMatrixForClearStageConfigurationVector(
            configuration);
        if (size != densityMatrix.length){
            throw new Exception();
        }
    }

    public ComplexDouble[][] getDensityMatrix(){
        return densityMatrix;
    }

    public int getQubitsNumber() {
        return qubitsNumber;
    }

    public void setQubitsNumber(int qubitsNumber) {
        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
        ComplexDouble[] vector = new ComplexDouble[size];
        vector[0]=Complex.unit();
        for (int i = 1; i < vector.length; i++) {
            vector[i]=Complex.zero();
        }
    }

    private ComplexDouble[][] densityMatrixForClearStageConfigurationVector (
        ComplexDouble[] vector) {
        //int size = vector.length;
        //ComplexDouble[][] result = new ComplexDouble[size][size];
        //ComplexDouble[][] result = ComplexMath.ketBraTensorMultiplication(
        //    vector, vector);
        return ComplexMath.ketBraTensorMultiplication(vector, vector);
    }

    @Override
    public String toString() {
        String result ="";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result+ densityMatrix[i][j] + " ";
            }
           result = result + "\n";
        }
        return result;
    }

    public void performAlgorythm (QAlgorithm algorythm) throws Exception {
        ComplexDouble[][] uMat = algorythm.getMatrix();
        densityMatrix = ComplexMath.squareMatricesMultiplication(
            uMat, densityMatrix, size);
        ComplexDouble[][] uMat_transpose = ComplexMath.
            hermitianTransposeForMatrix(uMat, size, size);
        densityMatrix = ComplexMath.squareMatricesMultiplication(
            densityMatrix, uMat_transpose, size);
    }        

    // Измерение
    public int measureQubit (int qubit) throws Exception {
        if (qubit >= qubitsNumber){
            throw new Exception();
        }

        ComplexDouble [][] p0Mat = ComplexMath.zeroMatrix(size, size);
        int pow2n_q = (int) Math.pow(2, qubitsNumber - qubit);
        int pow2n_q_1 = (int) Math.pow(2, qubitsNumber - qubit - 1);
        // нужно пройти по всем состояниям, где текущий кубит 0
        for (int i = 0; i < size; i += pow2n_q) {
            for (int j = i; j < i + pow2n_q_1; j++) {
                p0Mat[j][j] = Complex.unit();
            }
        }

        ComplexDouble[][] p0MatTr = ComplexMath.hermitianTransposeForMatrix(
            p0Mat, size, size);
        ComplexDouble[][] p0MatTr_p0Mat = ComplexMath.multiplication(
            p0MatTr, size, size, p0Mat, size, size);
        ComplexDouble [][] p0MatTr_p0Mat_ro = ComplexMath.multiplication(
            p0MatTr_p0Mat, size, size, densityMatrix, size, size);

        double p0Norm =  ComplexDouble.cuCreal(
            ComplexMath.trace(p0MatTr_p0Mat_ro, size));

        //measure and normalize
        ComplexDouble[][] pmMat;
        int result;
        if (new Random().nextDouble() > p0Norm) {
            result = 1;
            // Configure P1 projector
            pmMat = ComplexMath.zeroMatrix(size, size);
            for (int i = pow2n_q_1; i < size; i += pow2n_q) {
                for (int j = i; j < i+pow2n_q_1; j++){
                    pmMat[j][j] = Complex.unit();
                }
            }
        } else {
            result = 0;
            pmMat = p0Mat;
        }

        ComplexDouble[][] pmMatTr = ComplexMath.hermitianTransposeForMatrix(
            pmMat, size, size);
        ComplexDouble [][] pmMat_ro_pmMatTr = ComplexMath.
            squareMatricesMultiplication(
                ComplexMath.squareMatricesMultiplication(
                    pmMat, densityMatrix, size),
                pmMatTr,
                size);
        ComplexDouble [][] pmTr_pmMat_ro = ComplexMath.
            squareMatricesMultiplication(
                ComplexMath.squareMatricesMultiplication(pmMatTr, pmMat, size),
                densityMatrix,
                size);

        densityMatrix = ComplexMath.multiplication(
            ComplexDouble.cuCmplx(
                1.0 / ComplexMath.trace(pmTr_pmMat_ro, size).x, 0.0),
            pmMat_ro_pmMatTr,
            size);

        return result;
    }
}
