package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;
import kpfu.terentyev.quantum.util.Matrix;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
    protected int qubitsNumber;
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
        ComplexDouble result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return new Matrix(result);
    }

    public static Matrix hadamardGateMatrix (){
        ComplexDouble result [][] = {
                {ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)), 0),ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)),0)},
                {ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)), 0),ComplexDouble.cuCmplx((float) (-1/Math.sqrt(2)),0)}
        };
        return new Matrix(result);
    }

    public static Matrix pauliXGateMatrix (){
        ComplexDouble result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return new Matrix(result);
    }

    public static Matrix pauliYGateMatrix (){
        ComplexDouble result [][] = {
                {Complex.zero(),ComplexDouble.cuCmplx(0, -1)},
                {ComplexDouble.cuCmplx(0,1),Complex.zero()}
        };
        return new Matrix(result);
    }

    public static Matrix pauliZGateMatrix (){
        ComplexDouble result [][] = {
                {ComplexDouble.cuCmplx(0,1),Complex.zero()},
                {Complex.zero(),ComplexDouble.cuCmplx(0, -1)}
        };
        return new Matrix(result);
    }

    public static Matrix swapGateMatrix(){
        ComplexDouble result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return new Matrix(result);
    }

    public static Matrix controlledNOTGateMatrix(){
        ComplexDouble result [][] = {
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
        ComplexDouble result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix.get(0, 0), uMatrix.get(0, 1)},
                {Complex.zero(), Complex.zero(), uMatrix.get(1, 0), uMatrix.get(1, 1)},
        };
        return new Matrix(result);
    }

    public static Matrix toffoliGateMatrix() {
        ComplexDouble result [][] = {
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
        ComplexDouble result [][] = {
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
