package kpfu.terentyev.quantum.util;

final public class Matrix {

    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new ComplexDouble[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                data[i][j] = Complex.zero();
    }

    public Matrix(ComplexDouble[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new ComplexDouble[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
    }

    private Matrix(Matrix A) { this(A.data); }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public void set(int i, int j, ComplexDouble val) {
        if (i >= M || i < 0 || j >= N || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.data[i][j] = val;
    }

    public ComplexDouble get(int i, int j) {
        if (i >= M || i < 0 || j >= N || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[i][j];
    }

    public Matrix clone() {
        return new Matrix(this.data);
    }

    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = Complex.unit();
        return I;
    }

    private void swap(int i, int j) {
        ComplexDouble[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    public Matrix dagger() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = ComplexDouble.cuConj(this.data[i][j]);
        return A;
    }

    public ComplexDouble trace() {
        ComplexDouble result = Complex.zero();
        for (int i = 0; i < M; i++)
            result = ComplexDouble.cuCadd(result, this.data[i][i]);
        return result;
    }

    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M)
            throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] = ComplexDouble.cuCadd(
                        C.data[i][j],
                        ComplexDouble.cuCmul(A.data[i][k], B.data[k][j]));
        return C;
    }

    public Matrix times(ComplexDouble a) {
        Matrix A = this;
        Matrix B = new Matrix(A.M, A.N);
        for (int i = 0; i < B.M; i++)
            for (int j = 0; j < B.N; j++)
                B.data[i][j] = ComplexDouble.cuCmul(a, A.data[i][j]);
        return B;
    }

    public Vector times(Vector b) {
        Matrix A = this;
        if (A.N != b.size())
            throw new RuntimeException("Illegal matrix and vector dimensions.");
        Vector x = new Vector(A.M);
        for (int i = 0; i < A.M; i++)
            x.set(i, b.times(new Vector(A.data[i])));
        return x;
    }

    public Matrix tensorTimes(Matrix B) {
        Matrix A = this;
        Matrix C = new Matrix(A.M * B.M, A.N * B.N);
        for (int i1 = 0; i1 < A.M; i1++)
            for (int j1 = 0; j1 < A.N; j1++)
                for (int i2 = 0; i2 < B.M; i2++)
                    for (int j2 = 0; j2 < B.N; j2++)
                        C.data[i1 * B.M + i2][j1 * B.N + j2] =
                            ComplexDouble.cuCmul(A.data[i1][j1], B.data[i2][j2]);
        return C;
    }

    @Override
    public String toString() {
        String result ="";
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                result = result + data[i][j] + " ";
            result = result + "\n";
        }
        return result;
    }

    private final int M;
    private final int N;
    private ComplexDouble data[][];
}
