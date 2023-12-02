package sgu.atbmhttt.cipherapp.models;

public class CaesarCipher {

    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) ('A' + (Character.toUpperCase(ch) - 'A' + key) % 26);
                if (Character.isLowerCase(ch)) {
                    result.append(Character.toLowerCase(shifted));
                } else {
                    result.append(shifted);
                }
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int key) {
        return encrypt(text, 26 - key);
    }
}
