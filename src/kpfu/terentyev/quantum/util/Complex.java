package kpfu.terentyev.quantum.util;

/**
 * Created by aleksandrterentev on 14.04.17.
 */
public class Complex {

    public static ComplexDouble complex (double real, double imagine) {
        return ComplexDouble.cuCmplx(real, imagine);
    }

    public static ComplexDouble zero () {
        return ComplexDouble.cuCmplx(0, 0);
    }

    public static ComplexDouble unit (){
        return ComplexDouble.cuCmplx(1, 0);
    }

    public static ComplexDouble [] getRow(ComplexDouble[][] matr, int row) {
        return matr [row];
    }
}
