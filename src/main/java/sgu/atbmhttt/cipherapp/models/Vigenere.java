package sgu.atbmhttt.cipherapp.models;

public class Vigenere {

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = encryptChar(plainChar, keyChar);
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char encryptedChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char plainChar = decryptChar(encryptedChar, keyChar);
            plaintext.append(plainChar);
        }
        return plaintext.toString();
    }

    private static char encryptChar(char plainChar, char keyChar) {
        if (Character.isLetter(plainChar)) {
            int shift = Character.isUpperCase(keyChar) ? 'A' : 'a';
            int encryptedChar = (plainChar + keyChar - 2 * shift) % 26 + shift;
            return (char) encryptedChar;
        }
        return plainChar;
    }

    private static char decryptChar(char encryptedChar, char keyChar) {
        if (Character.isLetter(encryptedChar)) {
            int shift = Character.isUpperCase(keyChar) ? 'A' : 'a';
            int plainChar = (encryptedChar - keyChar + 26) % 26 + shift;
            return (char) plainChar;
        }
        return encryptedChar;
    }
}
