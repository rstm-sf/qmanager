package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {
    private ComplexDouble[][] matrix;

    public UGate (int qubitsNumber, ComplexDouble[][] uMatrix) {
        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
        this.matrix = uMatrix;
    }

    @Override
    public ComplexDouble[][] getMatrix() {
        return matrix;
    }
}
