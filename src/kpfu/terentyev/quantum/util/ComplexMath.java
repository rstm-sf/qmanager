package kpfu.terentyev.quantum.util;

/**
 * Created by alexandrterentyev on 25.03.15.
 */
public class ComplexMath {

    public static ComplexDouble[][] tensorMultiplication(ComplexDouble[][] firstMatrix, int firstMatrixHeight,
                                                           int firstMatrixWidth, ComplexDouble[][] secondMatrix, int secondMatrixHeight, int secondMatrixWidth) {
        ComplexDouble[][] result = new ComplexDouble[firstMatrixHeight * secondMatrixHeight][firstMatrixWidth * secondMatrixWidth];
        for (int iFirst = 0; iFirst < firstMatrixHeight; iFirst++) {
            for (int jFirst = 0; jFirst < firstMatrixWidth; jFirst++) {
                for (int iSecond = 0; iSecond < secondMatrixHeight; iSecond++) {
                    for (int jSecond = 0; jSecond < secondMatrixWidth; jSecond++) {
                        result[iFirst * secondMatrixHeight + iSecond][jFirst * secondMatrixWidth + jSecond] =
                                ComplexDouble.cuCmul(firstMatrix[iFirst][jFirst], secondMatrix[iSecond][jSecond]);
                    }
                }
            }
        }

        return result;
    }

    public static ComplexDouble[][] multiplication(ComplexDouble a, ComplexDouble[][] matrix, int size) {
        ComplexDouble[][] result = new ComplexDouble[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = ComplexDouble.cuCmul(a, matrix[i][j]);
            }
        }
        return result;
    }

    public static ComplexDouble[] multiplication(ComplexDouble[][] matrix, int size, ComplexDouble[] vector) {
        ComplexDouble[] result = new ComplexDouble[size];
        for (int i = 0; i < size; i++) {
            result[i] = vectorProduct(Complex.getRow(matrix, i), vector);
        }
        return result;
    }

    public static ComplexDouble[] tensorMultiplication(ComplexDouble[] a, ComplexDouble[] b) {
        ComplexDouble[] result = new ComplexDouble[a.length * b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i * b.length + j] = ComplexDouble.cuCmul(a[i], b[j]);
            }
        }
        return result;
    }

    public static ComplexDouble[][] ketBraTensorMultiplication(ComplexDouble[] ket, ComplexDouble[] bra) {
        ComplexDouble[][] result = new ComplexDouble[ket.length][bra.length];

        for (int i = 0; i < ket.length; i++) {
            for (int j = 0; j < bra.length; j++) {
                result[i][j] = ComplexDouble.cuCmul(ket[i], bra[j]);
            }
        }

        return result;
    }

    public static ComplexDouble[][] cpuMultiplication(ComplexDouble[][] a, int aHeight, int aWidth,
                                                        ComplexDouble[][] b, int bHeight, int bWidth) {
        ComplexDouble[][] res = new ComplexDouble[aHeight][bWidth];

        for (int i = 0; i < aHeight; i++) {
            for (int j = 0; j < bWidth; j++) {
                ComplexDouble resIJ = Complex.zero();

                for (int z = 0; z < aWidth; z++) {
                    resIJ = ComplexDouble.cuCadd(resIJ, ComplexDouble.cuCmul(a[i][z], b[z][j]));
                }

                res[i][j] = resIJ;
            }
        }

        return res;
    }

    public static ComplexDouble[][] multiplication(ComplexDouble[][] a, int aHeight, int aWidth,
                                                     ComplexDouble[][] b, int bHeight, int bWidth) {

        return cpuMultiplication(a, aHeight, aWidth, b, bHeight, bWidth);
    }


    public static ComplexDouble cpuVectorProduct(ComplexDouble[] a, ComplexDouble[] b) {
        ComplexDouble res = Complex.zero();

        for (int i = 0; i < a.length; i++) {
            res = ComplexDouble.cuCadd(res, ComplexDouble.cuCmul(a[i], b[i]));
        }

        return res;
    }

    public static ComplexDouble vectorProduct(ComplexDouble[] a, ComplexDouble[] b) {
        return cpuVectorProduct(a, b);
    }


    public static ComplexDouble[][] zeroMatrix(int height, int width) {
        ComplexDouble[][] result = new ComplexDouble[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = Complex.zero();
            }
        }
        return result;
    }

    public static ComplexDouble[][] squareMatricesMultiplication(ComplexDouble[][] matrixA, ComplexDouble[][] matrixB, int size) {

        return multiplication(matrixA, size, size, matrixB, size, size);
    }

    public static ComplexDouble[][] hermitianTransposeForMatrix(ComplexDouble[][] matrix, int height, int width) {
        ComplexDouble[][] result = new ComplexDouble[width][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                result[j][i] = ComplexDouble.cuConj(matrix[i][j]);
            }
        return result;
    }

    public static ComplexDouble trace(ComplexDouble[][] matrix, int size) {
        ComplexDouble result = Complex.zero();
        for (int i = 0; i < size; i++) {
            result = ComplexDouble.cuCadd(result, matrix[i][i]);
        }

        return result;
    }
}
