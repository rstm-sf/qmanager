package kmqc.simulator.core.gates;

import kmqc.simulator.util.Matrix;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {

    public UGate (int nQubits, Matrix uMatrix) {
        this.nQubits = nQubits;
        this.size = (int) Math.pow(2, nQubits);
        this.matrix = uMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    private Matrix matrix;
}
