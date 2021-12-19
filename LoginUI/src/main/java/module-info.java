module com.example.loginui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.loginui to javafx.fxml;
    exports com.example.loginui;
}