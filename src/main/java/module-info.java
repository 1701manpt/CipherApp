module sgu.atbmhttt.cipherapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens sgu.atbmhttt.cipherapp to javafx.fxml;
    exports sgu.atbmhttt.cipherapp;
    exports sgu.atbmhttt.cipherapp.controllers;
    opens sgu.atbmhttt.cipherapp.controllers to javafx.fxml;
}