package kpfu.terentyev.quantum.emulator.api;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class Helper extends QManager {

    public Helper() {
        super();
    }
    
    public void opQET(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        ComplexDouble[][] matQET = generateMatQET(theta);
        performTransitionForQubits(null, matQET, info, a, b);
    }

    public void opPHASE(
        QubitInfo a, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, b);
        ComplexDouble[][] matPHASE = generateMatPHASE(theta);
        performTransitionForQubits(null, matPHASE, info, a, b);
    }

    public void opCQET(
        QubitInfo a, QubitInfo c, QubitInfo b, double theta) throws Exception {
        QRegInfo info = checkAndMergeRegistersIfNeedForQubits(a, c, b);
        boolean isFirstC =  getIdxInReg(c) < getIdxInReg(a)
                         && getIdxInReg(c) < getIdxInReg(b);
        boolean isLastC  =  getIdxInReg(c) > getIdxInReg(a)
                         && getIdxInReg(c) > getIdxInReg(b);
        ComplexDouble[][] matCQET = generateMatCQET(theta, isFirstC, isLastC);
        performTransitionForQubits(null, matCQET, info, a, b, c);
    }

    public void merge2qubit(QubitInfo q1, QubitInfo q2) throws Exception {
        QubitInfo[] qubitsArray = new QubitInfo[2];
        qubitsArray[0] = q1;
        qubitsArray[1] = q2;
        checkAndMergeRegistersIfNeedForQubits(qubitsArray);
    }

    private static ComplexDouble[][] generateMatQET(double theta) {
        return new ComplexDouble[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(Math.cos(theta / 2.0), 0.0),
                Complex.complex(0.0, Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(0.0, Math.sin(theta / 2.0)),
                Complex.complex(Math.cos(theta / 2.0), 0.0),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        };
    }

    private static ComplexDouble[][] generateMatPHASE(double theta) {
        return new ComplexDouble[][] {
            {
                Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()
            },
            {
                Complex.zero(),
                Complex.complex(Math.cos(-theta / 2.0), Math.sin(-theta / 2.0)),
                Complex.zero(),
                Complex.zero()
            },
            {
                Complex.zero(),
                Complex.zero(),
                Complex.complex(Math.cos(theta / 2.0), Math.sin(theta / 2.0)),
                Complex.zero()
            },
            {
                Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()
            }
        };
    }

    private static ComplexDouble[][] generateMatCQET(
        double theta, boolean isFirstC, boolean isLastC) {
        final ComplexDouble z  = Complex.zero();
        final ComplexDouble u  = Complex.unit();
        final ComplexDouble co = Complex.complex(Math.cos(theta / 2.0), 0.0);
        final ComplexDouble si = Complex.complex(0.0, Math.sin(theta / 2.0));

        if (isFirstC) {
            return new ComplexDouble[][] {
                {u,  z,  z, z, z, z, z, z},
                {z, co, si, z, z, z, z, z},
                {z, si, co, z, z, z ,z, z},
                {z,  z,  z, u, z, z, z, z},
                {z,  z,  z, z, u, z, z, z},
                {z,  z,  z, z, z, u, z, z},
                {z,  z,  z, z, z, z, u, z},
                {z,  z,  z, z, z, z, z, u}
            };
        } else if (isLastC) {
            return new ComplexDouble[][] {
                {z, z, z, z, u,  z,  z, z},
                {u, z, z, z, z,  z,  z, z},
                {z, z, z, z, z, co, si, z},
                {z, u, z, z, z,  z,  z, z},
                {z, z, z, z, z, si, co, z},
                {z, z, u, z, z,  z,  z, z},
                {z, z, z, z, z,  z,  z, u},
                {z, z, z, u, z,  z,  z, z}
            };
        } else {
            return new ComplexDouble[][] {
                {z, z, u,  z,  z, z, z, z},
                {z, z, z, co, si, z, z, z},
                {u, z, z,  z,  z, z, z, z},
                {z, u, z,  z,  z, z, z, z},
                {z, z, z, si, co, z, z, z},
                {z, z, z,  z,  z, u, z, z},
                {z, z, z,  z,  z, z, u, z},
                {z, z, z,  z,  z, z, z, u}
            };
        }
    }
}