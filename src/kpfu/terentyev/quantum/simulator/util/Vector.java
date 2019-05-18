package kpfu.terentyev.quantum.simulator.util;

final public class Vector {

    public Vector(int M) {
        this.M = M;
        data = new Complex[M];
        for (int i = 0; i < M; i++)
            data[i] = Complex.zero();
    }

    public Vector(Complex[] data) {
        M = data.length;
        this.data = new Complex[M];
        for (int i = 0; i < M; i++)
            this.data[i] = data[i];
    }

    private Vector(Vector A) { this(A.data); }

    public void set(int i, Complex val) {
        if (i >= M || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.data[i] = val;
    }

    public int size() {
        return M;
    }

    private void swap(int i, int j) {
        Complex temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public Complex times(Vector B) {
        Vector A = this;
        if (A.M != B.M)
            throw new RuntimeException("Illegal vector dimensions.");
        Complex result = Complex.zero();
        for (int i = 0; i < A.M; i++)
            result = Complex.cAdd(
                result, Complex.cMul(A.data[i], B.data[i]));
        return result;
    }

    public Vector tensorTimes(Vector B) {
        Vector A = this;
        Vector C = new Vector(A.M * B.M);
        for (int i1 = 0; i1 < A.M; i1++)
            for (int i2 = 0; i2 < B.M; i2++)
                C.data[i1 * B.M + i2] =
                    Complex.cMul(A.data[i1], B.data[i2]);
        return C;
    }

    public Matrix ketbraTimes(Vector B) {
        Vector A = this;
        Matrix C = new Matrix(A.M, B.M);
        for (int i1 = 0; i1 < A.M; i1++)
            for (int i2 = 0; i2 < B.M; i2++)
                C.set(i1, i2, Complex.cMul(A.data[i1], B.data[i2]));
        return C;
    }

    @Override
    public String toString() {
        String result ="";
        for (int i = 0; i < M; i++)
            result = result + data[i] + " ";
        result = result + "\n";
        return result;
    }

    private final int M;
    private Complex data[];
}