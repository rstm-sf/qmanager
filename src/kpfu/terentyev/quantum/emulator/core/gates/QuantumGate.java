package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.Matrix;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
    protected int nQubits;
    protected int size;

    @Override
    public String toString(){
        Matrix matrix = null;
        try {
            matrix = this.getMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrix.toString();
    }

    public abstract Matrix getMatrix () throws Exception;

    //Gate matrices
    public static Matrix identityGateMatrix(){
        Complex result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return new Matrix(result);
    }

    public static Matrix hadamardGateMatrix (){
        Complex result [][] = {
                {Complex.cmplx((float) (1/Math.sqrt(2)), 0),Complex.cmplx((float) (1/Math.sqrt(2)),0)},
                {Complex.cmplx((float) (1/Math.sqrt(2)), 0),Complex.cmplx((float) (-1/Math.sqrt(2)),0)}
        };
        return new Matrix(result);
    }

    public static Matrix pauliXGateMatrix (){
        Complex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return new Matrix(result);
    }

    public static Matrix pauliYGateMatrix (){
        Complex result [][] = {
                {Complex.zero(),Complex.cmplx(0, -1)},
                {Complex.cmplx(0,1),Complex.zero()}
        };
        return new Matrix(result);
    }

    public static Matrix pauliZGateMatrix (){
        Complex result [][] = {
                {Complex.cmplx(0,1),Complex.zero()},
                {Complex.zero(),Complex.cmplx(0, -1)}
        };
        return new Matrix(result);
    }

    public static Matrix swapGateMatrix(){
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return new Matrix(result);
    }

    public static Matrix controlledNOTGateMatrix(){
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return new Matrix(result);
    }

    public static Matrix controlledUGateMatrix(Matrix uMatrix) throws Exception {
        if (uMatrix.getM() != 2 || uMatrix.getN() != 2)
            throw new Exception();
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix.get(0, 0), uMatrix.get(0, 1)},
                {Complex.zero(), Complex.zero(), uMatrix.get(1, 0), uMatrix.get(1, 1)},
        };
        return new Matrix(result);
    }

    public static Matrix toffoliGateMatrix() {
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return new Matrix(result);
    }
    
    public static Matrix fredkinGateMatrix(){
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return new Matrix(result);
    }
}
