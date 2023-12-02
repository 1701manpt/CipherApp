package sgu.atbmhttt.cipherapp.models;

public class Hill {
    private final int[][] keyMatrix;
    private final int matrixSize;

    public Hill(int[][] keyMatrix) throws Exception {
        if (keyMatrix.length != keyMatrix[0].length || (keyMatrix.length != 2 && keyMatrix.length != 3)) {
            throw new Exception("Khóa ma trận vuông không hợp lệ. Chỉ hỗ trợ ma trận 2x2 hoặc 3x3");
        }

        this.keyMatrix = keyMatrix;
        this.matrixSize = keyMatrix.length;
    }

    // Hàm tính modulo 26
    private int modulo26(int x) {
        return (x % 26 + 26) % 26;
    }

    // Hàm kiểm tra tính khả nghịch của ma trận
    private boolean isMatrixInvertible(int[][] matrix) {
        int matrixSize = matrix.length;

        // Kiểm tra chỉ đối với ma trận vuông
        if (matrixSize != matrix[0].length) {
            return false;
        }

        // Trường hợp ma trận 2x2
        if (matrixSize == 2) {
            int determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            determinant = modulo26(determinant);
            int inverseDeterminant = -1;

            for (int i = 0; i < 26; i++) {
                if (modulo26(determinant * i) == 1) {
                    inverseDeterminant = i;
                    break;
                }
            }

            return inverseDeterminant != -1;
        }

        // Trường hợp ma trận 3x3
        if (matrixSize == 3) {
            int determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                    - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                    + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

            determinant = modulo26(determinant);
            int inverseDeterminant = -1;

            for (int i = 0; i < 26; i++) {
                if (modulo26(determinant * i) == 1) {
                    inverseDeterminant = i;
                    break;
                }
            }

            return inverseDeterminant != -1;
        }

        // Trường hợp ma trận có kích thước khác 2x2 và 3x3
        return false;
    }

    // Hàm mã hóa thông điệp
    public String encrypt(String plainText) throws Exception {
        if (keyMatrix.length > plainText.length() || !isMatrixInvertible(keyMatrix)) {
            throw new Exception("Khóa ma trận vuông này không khả nghịch");
        }

        if (keyMatrix.length > plainText.length()) {
            throw new Exception("Chiều dài của bản rõ phải lớn hơn chiều dài của khóa ma trận vuông");
        }

        plainText = plainText.toUpperCase();
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i += matrixSize) {
            String block = plainText.substring(i, i + matrixSize);
            int[] plainTextBlock = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                plainTextBlock[j] = block.charAt(j) - 'A';
            }

            int[] encryptedBlock = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    encryptedBlock[j] += keyMatrix[j][k] * plainTextBlock[k];
                }
                encryptedBlock[j] = modulo26(encryptedBlock[j]);
            }

            for (int j = 0; j < matrixSize; j++) {
                encryptedText.append((char) (encryptedBlock[j] + 'A'));
            }
        }

        return encryptedText.toString();
    }

    // Hàm giải mã thông điệp
    public String decrypt(String cipherText) throws Exception {
        if (!isMatrixInvertible(keyMatrix)) {
            throw new Exception("Khóa ma trận vuông này không khả nghịch");
        }

        if (keyMatrix.length > cipherText.length()) {
            throw new Exception("Chiều dài của bản mã phải lớn hơn chiều dài của khóa ma trận vuông");
        }

        cipherText = cipherText.toUpperCase();
        StringBuilder decryptedText = new StringBuilder();

        int[][] inverseMatrix = new int[matrixSize][matrixSize];

// Tính ma trận nghịch đảo (3x3)
        if (matrixSize == 3) {
            int determinant = keyMatrix[0][0] * (keyMatrix[1][1] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][1])
                    - keyMatrix[0][1] * (keyMatrix[1][0] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][0])
                    + keyMatrix[0][2] * (keyMatrix[1][0] * keyMatrix[2][1] - keyMatrix[1][1] * keyMatrix[2][0]);

            determinant = modulo26(determinant);

            // Kiểm tra xem determinant có khác 0 không
            if (determinant != 0) {
                int inverseDeterminant = -1;

                for (int i = 0; i < 26; i++) {
                    if (modulo26(determinant * i) == 1) {
                        inverseDeterminant = i;
                        break;
                    }
                }

                // Tính phần tử của ma trận nghịch đảo
                inverseMatrix[0][0] = modulo26((keyMatrix[1][1] * keyMatrix[2][2] - keyMatrix[1][2] * keyMatrix[2][1]) * inverseDeterminant);
                inverseMatrix[0][1] = modulo26((keyMatrix[0][2] * keyMatrix[2][1] - keyMatrix[0][1] * keyMatrix[2][2]) * inverseDeterminant);
                inverseMatrix[0][2] = modulo26((keyMatrix[0][1] * keyMatrix[1][2] - keyMatrix[0][2] * keyMatrix[1][1]) * inverseDeterminant);
                inverseMatrix[1][0] = modulo26((keyMatrix[1][2] * keyMatrix[2][0] - keyMatrix[1][0] * keyMatrix[2][2]) * inverseDeterminant);
                inverseMatrix[1][1] = modulo26((keyMatrix[0][0] * keyMatrix[2][2] - keyMatrix[0][2] * keyMatrix[2][0]) * inverseDeterminant);
                inverseMatrix[1][2] = modulo26((keyMatrix[0][2] * keyMatrix[1][0] - keyMatrix[0][0] * keyMatrix[1][2]) * inverseDeterminant);
                inverseMatrix[2][0] = modulo26((keyMatrix[1][0] * keyMatrix[2][1] - keyMatrix[1][1] * keyMatrix[2][0]) * inverseDeterminant);
                inverseMatrix[2][1] = modulo26((keyMatrix[0][1] * keyMatrix[2][0] - keyMatrix[0][0] * keyMatrix[2][1]) * inverseDeterminant);
                inverseMatrix[2][2] = modulo26((keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) * inverseDeterminant);
            } else {
                // Xử lý trường hợp determinant bằng 0 (ma trận không nghịch đảo)
                throw new Exception("Ma trận không nghịch đảo vì determinant bằng 0.");
            }
        }
        // Tính ma trận nghịch đảo (2x2)
        else {
            int determinant = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
            determinant = modulo26(determinant);
            int inverseDeterminant = -1;

            for (int i = 0; i < 26; i++) {
                if (modulo26(determinant * i) == 1) {
                    inverseDeterminant = i;
                    break;
                }
            }

            inverseMatrix[0][0] = modulo26(keyMatrix[1][1] * inverseDeterminant);
            inverseMatrix[0][1] = modulo26(-keyMatrix[0][1] * inverseDeterminant);
            inverseMatrix[1][0] = modulo26(-keyMatrix[1][0] * inverseDeterminant);
            inverseMatrix[1][1] = modulo26(keyMatrix[0][0] * inverseDeterminant);
        }

        for (int i = 0; i < cipherText.length(); i += matrixSize) {
            String block = cipherText.substring(i, i + matrixSize);
            int[] cipherTextBlock = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                cipherTextBlock[j] = block.charAt(j) - 'A';
            }

            int[] decryptedBlock = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    decryptedBlock[j] += inverseMatrix[j][k] * cipherTextBlock[k];
                }
                decryptedBlock[j] = modulo26(decryptedBlock[j]);
            }

            for (int j = 0; j < matrixSize; j++) {
                decryptedText.append((char) (decryptedBlock[j] + 'A'));
            }
        }

        return decryptedText.toString();
    }
}

