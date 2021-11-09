package com.example.minesweeper;

import javafx.scene.layout.Pane;

public class Cell extends Pane {
    public static final int EMPTY = 0, EXPOSED_NUMBER = 1, EXPOSED_BOMB = 2;

    private int state = 0;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
