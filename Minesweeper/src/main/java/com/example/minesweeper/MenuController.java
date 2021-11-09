package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button easyButton;
    @FXML
    private Button normalButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button extremeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        easyButton.setOnAction(e -> startEasyGame());
        normalButton.setOnAction(e -> startNormalGame());
        hardButton.setOnAction(e -> startHardGame());
        extremeButton.setOnAction(e -> startExtremeGame());
    }

    private void startEasyGame() {

    }

    private void startNormalGame() {

    }

    private void startHardGame() {

    }

    private void startExtremeGame() {

    }
}
