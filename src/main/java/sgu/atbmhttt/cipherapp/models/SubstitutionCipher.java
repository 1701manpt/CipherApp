package sgu.atbmhttt.cipherapp.models;

public class SubstitutionCipher {
    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encrypt(String text, String key) {
        key = key.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int index = alphabet.indexOf(Character.toUpperCase(c));
                if (index != -1) {
                    char substitutionChar = key.charAt(index);
                    if (Character.isLowerCase(c)) {
                        substitutionChar = Character.toLowerCase(substitutionChar);
                    }
                    result.append(substitutionChar);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String decrypt(String text, String key) {
        key = key.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int index = key.indexOf(Character.toUpperCase(c));
                if (index != -1) {
                    char originalChar = alphabet.charAt(index);
                    if (Character.isLowerCase(c)) {
                        originalChar = Character.toLowerCase(originalChar);
                    }
                    result.append(originalChar);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
