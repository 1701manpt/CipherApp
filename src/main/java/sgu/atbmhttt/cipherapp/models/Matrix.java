package sgu.atbmhttt.cipherapp.models;

public class Matrix {
    public static boolean isInverse(int[][] matrix) {
        int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        int detInverse = modInverse(det, 26);
        return detInverse != -1;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // Inverse doesn't exist
    }
}
