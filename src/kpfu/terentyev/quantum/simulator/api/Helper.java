package kpfu.terentyev.quantum.simulator.api;

import kpfu.terentyev.quantum.simulator.util.Complex;
import kpfu.terentyev.quantum.simulator.util.Matrix;

public class Helper extends QManager {

    public Helper() {
        super();
    }
    
    public void opQET(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        Matrix matQET = generateMatQET(theta);
        performTransitionForQubits(null, matQET, info, a, b);
    }

    public void opPHASE(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        Matrix matPHASE = generateMatPHASE(theta);
        performTransitionForQubits(null, matPHASE, info, a, b);
    }

    public void opCQET(
        QubitInfo a, QubitInfo c, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, c, b);
        boolean isFirstC =  getIdxInReg(c) < getIdxInReg(a)
                         && getIdxInReg(c) < getIdxInReg(b);
        boolean isLastC  =  getIdxInReg(c) > getIdxInReg(a)
                         && getIdxInReg(c) > getIdxInReg(b);
        Matrix matCQET = generateMatCQET(theta, isFirstC, isLastC);
        performTransitionForQubits(null, matCQET, info, a, b, c);
    }

    public void merge2qubit(QubitInfo q1, QubitInfo q2) throws Exception {
        QubitInfo[] qubitsArray = new QubitInfo[2];
        qubitsArray[0] = q1;
        qubitsArray[1] = q2;
        checkAndMergeRegistersIfNeedForQubits(qubitsArray);
    }

    private static Matrix generateMatQET(double theta) {
        return new Matrix(new Complex[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.cmplx(Math.cos(theta / 2.0), 0.0),
                Complex.cmplx(0.0, Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.cmplx(0.0, Math.sin(theta / 2.0)),
                Complex.cmplx(Math.cos(theta / 2.0), 0.0),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        });
    }

    private static Matrix generateMatPHASE(double theta) {
        return new Matrix(new Complex[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.cmplx(Math.cos(-theta / 2.0), Math.sin(-theta / 2.0)),
                Complex.zero(),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.zero(),
                Complex.cmplx(Math.cos(theta / 2.0), Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        });
    }

    private static Matrix generateMatCQET(
        double theta, boolean isFirstC, boolean isLastC) {
        final Complex z  = Complex.zero();
        final Complex u  = Complex.unit();
        final Complex co = Complex.cmplx(Math.cos(theta / 2.0), 0.0);
        final Complex si = Complex.cmplx(0.0, Math.sin(theta / 2.0));

        if (isFirstC) {
            return new Matrix(new Complex[][] {
                {u,  z,  z, z, z, z, z, z},
                {z, co, si, z, z, z, z, z},
                {z, si, co, z, z, z ,z, z},
                {z,  z,  z, u, z, z, z, z},
                {z,  z,  z, z, u, z, z, z},
                {z,  z,  z, z, z, u, z, z},
                {z,  z,  z, z, z, z, u, z},
                {z,  z,  z, z, z, z, z, u}
            });
        } else if (isLastC) {
            return new Matrix(new Complex[][] {
                {u, z,  z, z,  z, z, z, z},
                {z, u,  z, z,  z, z, z, z},
                {z, z, co, z, si, z, z, z},
                {z, z,  z, u,  z, z, z, z},
                {z, z, si, z, co, z, z, z},
                {z, z,  z, z,  z, u, z, z},
                {z, z,  z, z,  z, z, u, z},
                {z, z,  z, z,  z, z, z, u}
            });
        } else {
            return new Matrix(new Complex[][] {
                {u,  z, z, z,  z, z, z, z},
                {z, co, z, z, si, z, z, z},
                {z,  z, u, z,  z, z, z, z},
                {z,  z, z, u,  z, z, z, z},
                {z, si, z, z, co, z, z, z},
                {z,  z, z, z,  z, u, z, z},
                {z,  z, z, z,  z, z, u, z},
                {z,  z, z, z,  z, z, z, u}
            });
        }
    }
}