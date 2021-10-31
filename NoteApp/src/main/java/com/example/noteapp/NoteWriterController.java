package com.example.noteapp;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class NoteWriterController implements Initializable {
    public Button clearButton;
    public Button saveButton;
    public TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clearButtonPressed(ActionEvent actionEvent) {
        textArea.setText("CLEAR");
    }
}
