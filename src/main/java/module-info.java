module com.example.stickhero {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.stickhero to javafx.fxml;
    exports com.example.stickhero;
}