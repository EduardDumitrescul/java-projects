package com.example.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    private static Stage stage;
    public static Font font = Font.loadFont(String.valueOf(Minesweeper.class.getResource("/fonts/arcade_ya/ARCADE_I.TTF")), 40);

    @Override
    public void start(Stage stage) throws Exception {
        Minesweeper.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Minesweeper.class.getResource("/stylesheets/style.css").toString());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setSize(double width, double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void setScene(Scene scene) {
        scene.getStylesheets().add(Minesweeper.class.getResource("/stylesheets/style.css").toString());
        stage.setScene(scene);
    }

    public static void setMenuScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Minesweeper.class.getResource("/stylesheets/style.css").toString());
            stage.setScene(scene);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
