package kpfu.terentyev.quantum.emulator.core;

import java.util.Random;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;
import kpfu.terentyev.quantum.util.Matrix;
import kpfu.terentyev.quantum.util.Vector;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QReg {
    
    public QReg (int nQubits, Matrix densMat) throws Exception {
        this.nQubits = nQubits;
        size = ((int) Math.pow(2, nQubits));
        this.densMat = densMat;
        if (size != densMat.getM()) {
            throw new Exception();
        }
    }

    public QReg (int nQubits, Vector configuration) throws Exception {
        this.nQubits = nQubits;
        size = ((int) Math.pow(2, nQubits));
        this.densMat = densMat4ClearStageConfigurationVec(configuration);
        if (size != densMat.getM()) {
            throw new Exception();
        }
    }

    public Matrix getDensMat() {
        return densMat;
    }

    public int getQubitsNumber() {
        return nQubits;
    }

    private Matrix densMat4ClearStageConfigurationVec(Vector vector) {
        return vector.braketTimes(vector);
    }

    @Override
    public String toString() {
        return densMat.toString();
    }

    public void performAlgorythm (QAlgorithm algorythm) throws Exception {
        Matrix uMat = algorythm.getMatrix();
        Matrix uMat_transpose = uMat.dagger();
        densMat = uMat.times(densMat).times(uMat_transpose);
    }        

    // Измерение
    public int measureQubit (int idxQubit) throws Exception {
        if (idxQubit >= nQubits){
            throw new Exception();
        }

        Matrix p0Mat = new Matrix(size, size);
        int pow2n_q = (int) Math.pow(2, nQubits - idxQubit);
        int pow2n_q_1 = (int) Math.pow(2, nQubits - idxQubit - 1);
        // нужно пройти по всем состояниям, где текущий кубит 0
        for (int i = 0; i < size; i += pow2n_q)
            for (int j = i; j < i + pow2n_q_1; j++)
                p0Mat.set(j, j, Complex.unit());

        Matrix p0MatTr = p0Mat.dagger();
        Matrix p0MatTr_p0Mat_ro = p0MatTr.times(p0Mat).times(densMat);
        double p0Norm = ComplexDouble.cuCreal(p0MatTr_p0Mat_ro.trace());        

        //measure and normalize
        Matrix pmMat;
        int result;
        if (new Random().nextDouble() > p0Norm) {
            result = 1;
            // Configure P1 projector
            pmMat = new Matrix(size, size);
            for (int i = pow2n_q_1; i < size; i += pow2n_q)
                for (int j = i; j < i+pow2n_q_1; j++)
                    pmMat.set(j, j, Complex.unit());
        } else {
            result = 0;
            pmMat = p0Mat;
        }

        Matrix pmMatTr = pmMat.dagger();
        Matrix pmMat_ro_pmMatTr = pmMat.times(densMat).times(pmMatTr);
        Matrix pmTr_pmMat_ro = pmMatTr.times(pmMat).times(densMat);
        densMat = pmMat_ro_pmMatTr.times(
            Complex.complex(pmTr_pmMat_ro.trace().x, 0.0));
        return result;
    }

    private int nQubits;
    private int size;
    private Matrix densMat;
}
