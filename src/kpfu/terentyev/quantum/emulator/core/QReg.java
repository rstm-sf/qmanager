package kpfu.terentyev.quantum.emulator.core;

import java.util.Random;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexMath;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QReg {
    private int nQubits;
    private int size;
    private ComplexDouble[][] densMat;

    public QReg (int nQubits, ComplexDouble[][] densMat) throws Exception {
        this.nQubits = nQubits;
        size = ((int) Math.pow(2, nQubits));
        this.densMat = densMat;
        if (size != densMat.length) {
            throw new Exception();
        }
    }

    public QReg (int nQubits, ComplexDouble[] configuration) throws Exception {
        this.nQubits = nQubits;
        size = ((int) Math.pow(2, nQubits));
        this.densMat = densMat4ClearStageConfigurationVec(configuration);
        if (size != densMat.length) {
            throw new Exception();
        }
    }

    public ComplexDouble[][] getDensMat() {
        return densMat;
    }

    public int getQubitsNumber() {
        return nQubits;
    }

    private ComplexDouble[][] densMat4ClearStageConfigurationVec(
        ComplexDouble[] vector) {
        return ComplexMath.ketBraTensorMultiplication(vector, vector);
    }

    @Override
    public String toString() {
        String result ="";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result+ densMat[i][j] + " ";
            }
           result = result + "\n";
        }
        return result;
    }

    public void performAlgorythm (QAlgorithm algorythm) throws Exception {
        ComplexDouble[][] uMat = algorythm.getMatrix();
        densMat = ComplexMath.squareMatricesMultiplication(
            uMat, densMat, size);
        ComplexDouble[][] uMat_transpose = ComplexMath.
            hermitianTransposeForMatrix(uMat, size, size);
        densMat = ComplexMath.squareMatricesMultiplication(
            densMat, uMat_transpose, size);
    }        

    // Измерение
    public int measureQubit (int idxQubit) throws Exception {
        if (idxQubit >= nQubits){
            throw new Exception();
        }

        ComplexDouble [][] p0Mat = ComplexMath.zeroMatrix(size, size);
        int pow2n_q = (int) Math.pow(2, nQubits - idxQubit);
        int pow2n_q_1 = (int) Math.pow(2, nQubits - idxQubit - 1);
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
            p0MatTr_p0Mat, size, size, densMat, size, size);

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
                ComplexMath.squareMatricesMultiplication(pmMat, densMat, size),
                pmMatTr,
                size);
        ComplexDouble [][] pmTr_pmMat_ro = ComplexMath.
            squareMatricesMultiplication(
                ComplexMath.squareMatricesMultiplication(pmMatTr, pmMat, size),
                densMat,
                size);

        densMat = ComplexMath.multiplication(
            ComplexDouble.cuCmplx(
                1.0 / ComplexMath.trace(pmTr_pmMat_ro, size).x, 0.0),
            pmMat_ro_pmMatTr,
            size);

        return result;
    }
}