package main.java.kmqc.simulator.util;

public class Complex {
    public double x;
    public double y;

    private Complex() {}

    public static double cReal(Complex x) {
        return x.x;
    }

    public static double cImag(Complex x) {
        return x.y;
    }

    public static Complex cmplx(double r, double i) {
        Complex res = new Complex();
        res.x = r;
        res.y = i;
        return res;
    }

    public static Complex conj(Complex x) {
        return cmplx(cReal(x), -cImag(x));
    }

    public static Complex cAdd(Complex x, Complex y) {
        return cmplx(cReal(x) + cReal(y), cImag(x) + cImag(y));
    }

    public static Complex cMul(Complex x, Complex y) {
        Complex prod = cmplx(
            cReal(x) * cReal(y) - cImag(x) * cImag(y),
            cReal(x) * cImag(y) + cImag(x) * cReal(y));
        return prod;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public static Complex zero() {
        return Complex.cmplx(0, 0);
    }

    public static Complex unit() {
        return Complex.cmplx(1, 0);
    }
}
