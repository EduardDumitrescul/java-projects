module com.example.testing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testing to javafx.fxml;
    exports com.example.testing;
}