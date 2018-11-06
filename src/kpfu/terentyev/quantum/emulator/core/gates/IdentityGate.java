package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.Matrix;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class IdentityGate extends QuantumGate {

    public IdentityGate() {
        this.qubitsNumber = 1;
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
