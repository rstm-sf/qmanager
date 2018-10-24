package kpfu.terentyev.quantum.util;

public class ComplexDouble {
    public double x;
    public double y;

    private ComplexDouble() {
    }

    public static double cuCreal(ComplexDouble x) {
        return x.x;
    }

    public static double cuCimag(ComplexDouble x) {
        return x.y;
    }

    public static ComplexDouble cuCmplx(double r, double i) {
        ComplexDouble res = new ComplexDouble();
        res.x = r;
        res.y = i;
        return res;
    }

    public static ComplexDouble cuConj(ComplexDouble x) {
        return cuCmplx(cuCreal(x), -cuCimag(x));
    }

    public static ComplexDouble cuCadd(ComplexDouble x, ComplexDouble y) {
        return cuCmplx(cuCreal(x) + cuCreal(y), cuCimag(x) + cuCimag(y));
    }

    public static ComplexDouble cuCmul(ComplexDouble x, ComplexDouble y) {
        ComplexDouble prod = cuCmplx(cuCreal(x) * cuCreal(y) - cuCimag(x) * cuCimag(y), cuCreal(x) * cuCimag(y) + cuCimag(x) * cuCreal(y));
        return prod;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
