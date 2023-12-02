package sgu.atbmhttt.cipherapp.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCheck {
    public static void isAlphaOnly(String text) throws Exception {
        // Tạo một biểu thức chính quy để kiểm tra chuỗi
        Pattern pattern = Pattern.compile("^[a-zA-Z]*$");

        // Tạo một Matcher để kiểm tra chuỗi
        Matcher matcher = pattern.matcher(text);

        // Kiểm tra xem chuỗi có khớp với biểu thức chính quy hay không
        if (!matcher.matches()) {
            throw new Exception("Chuỗi chỉ chấp nhận các chữ cái a-z, A-Z");
        }
    }
}
