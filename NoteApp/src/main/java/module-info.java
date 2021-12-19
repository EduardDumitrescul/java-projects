module com.example.noteapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.noteapp to javafx.fxml;
    exports com.example.noteapp;
}