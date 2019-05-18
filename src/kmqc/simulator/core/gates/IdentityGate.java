package kmqc.simulator.core.gates;

import kmqc.simulator.util.Complex;
import kmqc.simulator.util.Matrix;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class IdentityGate extends QuantumGate {

    public IdentityGate() {
        this.nQubits = 1;
        this.size = 2;
    }

    @Override
    public Matrix getMatrix() {
        return new Matrix(new Complex[][] {
                {Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit()}
        });
    }
}
