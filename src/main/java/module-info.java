module com.example.tcstest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires layout;
    requires kernel;
    requires io;

    opens com.example.tcstest to javafx.fxml;
    exports com.example.tcstest;
    opens com.example.tcstest.model to javafx.base;

}