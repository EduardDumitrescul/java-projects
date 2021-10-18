package com.example.hexagondijkstra;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;

public class ControlBarController {
    private ControlBarView controlBarView;
    private MapController mapController;

    private Button startButton, resetButton;

    public ControlBarController(ControlBarView controlBarView, MapController mapController) {
        this.controlBarView = controlBarView;
        this.mapController = mapController;

        resetButton = controlBarView.getResetButton();
        startButton = controlBarView.getStartButton();

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mapController.startDijkstra();
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mapController.resetMap();
            }
        });
    }
}
