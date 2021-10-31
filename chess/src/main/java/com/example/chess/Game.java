package com.example.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("chess-board-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setScene(scene);
        stage.show();
    }
}
