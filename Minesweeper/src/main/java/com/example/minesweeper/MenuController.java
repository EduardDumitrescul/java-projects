package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
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

    private FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("game-view.fxml"));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        easyButton.setOnAction(e -> startEasyGame());
        normalButton.setOnAction(e -> startNormalGame());
        hardButton.setOnAction(e -> startHardGame());
        extremeButton.setOnAction(e -> startExtremeGame());
    }

    private void startEasyGame(){
        try {
            Scene scene = new Scene(fxmlLoader.load());
            GameController gameController = fxmlLoader.getController();
            gameController.setDifficulty(GameController.EASY);
            Minesweeper.setScene(scene);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startNormalGame() {
        try {
            Scene scene = new Scene(fxmlLoader.load());
            GameController gameController = fxmlLoader.getController();
            gameController.setDifficulty(GameController.NORMAL);
            Minesweeper.setScene(scene);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startHardGame() {
        try {
            Scene scene = new Scene(fxmlLoader.load());
            GameController gameController = fxmlLoader.getController();
            gameController.setDifficulty(GameController.HARD);
            Minesweeper.setScene(scene);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startExtremeGame() {
        try {
            Scene scene = new Scene(fxmlLoader.load());
            GameController gameController = fxmlLoader.getController();
            gameController.setDifficulty(GameController.EXTREME);
            Minesweeper.setScene(scene);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
