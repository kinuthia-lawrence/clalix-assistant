module com.example.clalixassistant {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.clalixassistant to javafx.fxml;
    exports com.example.clalixassistant;
}