package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.Matrix;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {

    public UGate (int qubitsNumber, Matrix uMatrix) {
        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
        this.matrix = uMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    private Matrix matrix;
}
