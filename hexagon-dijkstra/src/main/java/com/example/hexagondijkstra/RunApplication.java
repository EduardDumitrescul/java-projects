package com.example.hexagondijkstra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunApplication extends Application {
    private MapView mapView;

    @Override
    public void start(Stage stage) throws IOException {
        mapView = new MapView(19, 30);

        Scene scene = new Scene(mapView);

        stage.setScene(scene);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}