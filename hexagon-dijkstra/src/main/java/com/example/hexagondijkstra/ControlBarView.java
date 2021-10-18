package com.example.hexagondijkstra;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlBarView extends HBox {
    private final Button startButton;
    private final Button resetButton;

    public ControlBarView() {
        setPrefHeight(80);

        startButton = new Button("Start");
        resetButton = new Button("Reset");

        setAlignment(Pos.CENTER);

        getChildren().add(startButton);
        getChildren().add(resetButton);
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getStartButton() {
        return startButton;
    }
}
