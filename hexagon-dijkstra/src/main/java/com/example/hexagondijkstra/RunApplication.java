package com.example.hexagondijkstra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RunApplication extends Application {
    private MapView mapView;
    private MapController mapController;

    private ControlBarView controlBarView;
    private ControlBarController controlBarController;

    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("sun.java2d.opengl", "true");

        BorderPane borderPane = new BorderPane();

        mapView = new MapView(19, 30);
        mapController = new MapController(mapView);
        controlBarView = new ControlBarView();
        controlBarController = new ControlBarController(controlBarView, mapController);

        borderPane.setTop(controlBarView);
        borderPane.setCenter(mapView);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}