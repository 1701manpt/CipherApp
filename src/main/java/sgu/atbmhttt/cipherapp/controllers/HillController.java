package sgu.atbmhttt.cipherapp.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sgu.atbmhttt.cipherapp.Components.MyAlert;
import sgu.atbmhttt.cipherapp.models.Hill;
import sgu.atbmhttt.cipherapp.models.InputCheck;

import java.net.URL;
import java.util.ResourceBundle;

public class HillController implements Initializable {
    public TextArea Bang_Ro_TextArea;
    public Button Chon_Tep_Button;
    public TextField Khoa_Dich_TextField;
    public Button Luu_Button;
    public Button Ma_Hoa_Button;
    public Button Xoa_Button;
    public Button Giai_Ma_Button;
    public Button Luu_1_Button;
    public TextField Khoa_Dich_1_TextField;
    public Button Chon_Tep_1_Button;
    public TextArea Bang_Ma_TextArea;
    public MyAlert myAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ma_Hoa_Button.setOnAction(event -> maHoa());
        Giai_Ma_Button.setOnAction(event -> giaiMa());
        Xoa_Button.setOnAction(event -> xoa());
    }

    public void maHoa() {
        try {
            String bangRo = Bang_Ro_TextArea.getText().trim();
            Bang_Ro_TextArea.setText(bangRo);
            InputCheck.isAlphaOnly(bangRo);
            String khoaDich = Khoa_Dich_TextField.getText().trim();
            Khoa_Dich_TextField.setText(khoaDich);
            if (khoaDich.isEmpty()) {
                throw new Exception("Khóa dịch của mã hóa không được bỏ trống");
            }

            Hill hill = new Hill(stringToSquareMatrix(khoaDich));
            String bangMa = hill.encrypt(bangRo);

            Bang_Ma_TextArea.setText(bangMa);
        } catch (Exception e) {
            myAlert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            myAlert.show();
        }
    }

    public void giaiMa() {
        try {
            String bangMa = Bang_Ma_TextArea.getText().trim();
            InputCheck.isAlphaOnly(bangMa);
            Bang_Ma_TextArea.setText(bangMa);
            String khoaDich1 = Khoa_Dich_1_TextField.getText().trim();
            Khoa_Dich_1_TextField.setText(khoaDich1);
            if (khoaDich1.isEmpty()) {
                throw new Exception("Khóa dịch của giải mã không được bỏ trống");
            }

            Hill hill = new Hill(stringToSquareMatrix(khoaDich1));
            String bangRo = hill.decrypt(bangMa);

            Bang_Ro_TextArea.setText(bangRo);
        } catch (Exception e) {
            myAlert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            myAlert.show();
        }
    }

    public void xoa() {
        try {
            Bang_Ro_TextArea.setText(null);
            Bang_Ma_TextArea.setText(null);
            Khoa_Dich_TextField.setText(null);
            Khoa_Dich_1_TextField.setText(null);
        } catch (Exception e) {
            myAlert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            myAlert.show();
        }
    }

    private int[][] stringToSquareMatrix(String input) throws Exception {
        String[] numbers = new String[0];
        input = input.trim();
        if (input.contains(" ")) {
            numbers = input.split("\\s+");
        } else {
            throw new Exception("Khóa ma trận vuông không hợp lệ. Chỉ hỗ trợ ma trận 2x2 hoặc 3x3");
        }

        // Calculate the size of the square matrix
        int size = (int) Math.sqrt(numbers.length);

        if (size * size != numbers.length) {
            throw new Exception("Khóa ma trận vuông không hợp lệ. Chỉ hỗ trợ ma trận 2x2 hoặc 3x3");
        }

        int[][] matrix = new int[size][size];
        int index = 0;

        for (String number : numbers) {
            int value = Integer.parseInt(number);
            matrix[index / size][index % size] = value;
            index++;
        }

        return matrix;
    }
}
