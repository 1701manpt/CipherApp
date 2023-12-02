package sgu.atbmhttt.cipherapp.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sgu.atbmhttt.cipherapp.models.CaesarCipher;

import java.net.URL;
import java.util.ResourceBundle;

public class CaesarCipherController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ma_Hoa_Button.setOnAction(event -> maHoa());
        Giai_Ma_Button.setOnAction(event -> giaiMa());
        Xoa_Button.setOnAction(event -> xoa());
    }

    public void maHoa() {
        try {
            String bangRo = Bang_Ro_TextArea.getText();
            String khoaDich = Khoa_Dich_TextField.getText();

            String bangMa = CaesarCipher.encrypt(bangRo, Integer.parseInt(khoaDich));

            Bang_Ma_TextArea.setText(bangMa);
        } catch (Exception e) {

        }
    }

    public void giaiMa() {
        try {
            String bangMa = Bang_Ma_TextArea.getText();
            String khoaDich = Khoa_Dich_1_TextField.getText();

            String bangRo = CaesarCipher.decrypt(bangMa, Integer.parseInt(khoaDich));

            Bang_Ro_TextArea.setText(bangRo);
        } catch (Exception e) {

        }
    }

    public void xoa() {
        try {
            Bang_Ro_TextArea.setText(null);
            Bang_Ma_TextArea.setText(null);
            Khoa_Dich_TextField.setText(null);
            Khoa_Dich_1_TextField.setText(null);
        } catch (Exception e) {

        }
    }
}
