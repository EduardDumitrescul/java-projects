package com.example.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Minesweeper extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Minesweeper.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }

    public static void setSize(double width, double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
