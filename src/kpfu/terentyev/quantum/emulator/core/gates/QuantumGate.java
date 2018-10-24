package kpfu.terentyev.quantum.emulator.core.gates;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
    protected int qubitsNumber;
    protected int size;

    @Override
    public String toString(){
        ComplexDouble[][] matrix = new ComplexDouble[0][];
        try {
            matrix = this.getMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result + matrix[i][j]+" ";
            }
            result = result + "\n";
        }
        return result;
    }

    public abstract ComplexDouble [][] getMatrix () throws Exception;

    //Gate matrices
    public static ComplexDouble [][] identityGateMatrix(){
        ComplexDouble result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return result;
    }

    public static ComplexDouble [][] hadamardGateMatrix (){
        ComplexDouble result [][] = {
                {ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)), 0),ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)),0)},
                {ComplexDouble.cuCmplx((float) (1/Math.sqrt(2)), 0),ComplexDouble.cuCmplx((float) (-1/Math.sqrt(2)),0)}
        };
        return result;
    }

    public static ComplexDouble [][] pauliXGateMatrix (){
        ComplexDouble result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }

    public static ComplexDouble [][] pauliYGateMatrix (){
        ComplexDouble result [][] = {
                {Complex.zero(),ComplexDouble.cuCmplx(0, -1)},
                {ComplexDouble.cuCmplx(0,1),Complex.zero()}
        };
        return result;
    }

    public static ComplexDouble [][] pauliZGateMatrix (){
        ComplexDouble result [][] = {
                {ComplexDouble.cuCmplx(0,1),Complex.zero()},
                {Complex.zero(),ComplexDouble.cuCmplx(0, -1)}
        };
        return result;
    }

    public static ComplexDouble [][] swapGateMatrix(){
        ComplexDouble result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }

    public static ComplexDouble [][] controlledNOTGateMatrix(){
        ComplexDouble result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }

    public static ComplexDouble [][] controlledUGateMatrix(ComplexDouble[][] uMatrix) throws Exception {
        if (uMatrix.length!=2 || (uMatrix.length==2 && (uMatrix[0].length!=2 || uMatrix[1].length!=2))){
            throw new Exception();
        }
        ComplexDouble result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix[0][0], uMatrix[0][1]},
                {Complex.zero(), Complex.zero(), uMatrix[1][0], uMatrix[1][1]},
        };
        return result;
    }

    public static ComplexDouble [][] toffoliGateMatrix(){
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
        return result;
    }
    
    public static ComplexDouble [][] fredkinGateMatrix(){
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
        return result;
    }
}
