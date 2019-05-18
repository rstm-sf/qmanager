package main.java.kmqc.simulator.core;

import java.util.Random;

import main.java.kmqc.simulator.util.Complex;
import main.java.kmqc.simulator.util.Matrix;
import main.java.kmqc.simulator.util.Vector;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QReg {
    
    public QReg (int nQubits, Matrix densMat) throws Exception {
        this.nQubits = nQubits;
        size = ((int) Math.pow(2, nQubits));
        this.densMat = densMat;
        if (size != densMat.getM())
            throw new Exception();
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
        return vector.ketbraTimes(vector);
    }

    @Override
    public String toString() {
        return densMat.toString();
    }

    public void performAlgorythm (QAlgo algorythm) throws Exception {
        Matrix uMat = algorythm.getMatrix();
        densMat = uMat.times(densMat).times(uMat.dagger());
    }        

    // Измерение
    public int measureQubit (int idxQubit) throws Exception {
        if (idxQubit >= nQubits)
            throw new Exception();

        Matrix p0     = new Matrix(size, size);
        int pow2n_q   = (int) Math.pow(2, nQubits - idxQubit);
        int pow2n_q_1 = (int) Math.pow(2, nQubits - idxQubit - 1);
        // нужно пройти по всем состояниям, где текущий кубит 0
        for (int i = 0; i < size; i += pow2n_q)
            for (int j = i; j < i + pow2n_q_1; j++)
                p0.set(j, j, Complex.unit());

        Matrix p0Dp0_ro = p0.dagger().times(p0).times(densMat);
        double p0Norm   = Complex.cReal(p0Dp0_ro.trace());        

        // measure and normalize
        Matrix pm;
        int result;
        if (new Random().nextDouble() > p0Norm) {
            result = 1;
            // Configure P1 projector
            pm = new Matrix(size, size);
            for (int i = pow2n_q_1; i < size; i += pow2n_q)
                for (int j = i; j < i+pow2n_q_1; j++)
                    pm.set(j, j, Complex.unit());
        } else {
            result = 0;
            pm = p0;
        }

        Matrix pmDagger       = pm.dagger();
        Matrix pm_ro_pmDagger = pm.times(densMat).times(pmDagger);
        Matrix pmDpm_ro       = pmDagger.times(pm).times(densMat);
        densMat = pm_ro_pmDagger.times(Complex.cmplx(pmDpm_ro.trace().x, 0.0));
        return result;
    }

    private int nQubits;
    private int size;
    private Matrix densMat;
}
