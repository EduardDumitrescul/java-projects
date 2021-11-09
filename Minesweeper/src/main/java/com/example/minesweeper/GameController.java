package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class GameController {
    public static final int EASY = 0, NORMAL = 1, HARD = 2 , EXTREME = 3;
    private static final int[] rowsOptions = new int[]{10, 14, 20, 24};
    private static final int[] columnsOptions = new int[]{10, 18, 30, 40};
    private static final int[] numberOfMines = new int[]{16, 40, 100};

    private static final int cellSize = 26;

    @FXML
    private GridPane grid;
    private Cell[][] cells;

    private int difficulty = 0, rows, columns, mines;
    
    public void setDifficulty(int value) {
        difficulty = value;
        rows = rowsOptions[value];
        columns = columnsOptions[value];
        if(value != EXTREME)
            mines = numberOfMines[value];
        else
            mines = 140 + (int)(Math.random() * 60);

        initializeGrid();
    }

    private void initializeGrid() {
        cells = new Cell[rows][columns];

        grid.setMinSize(cellSize * columns, cellSize * rows);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setMinWidth(cellSize);
        columnConstraints.setPrefWidth(cellSize);
        columnConstraints.setMaxWidth(cellSize);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(cellSize);
        rowConstraints.setPrefHeight(cellSize);
        rowConstraints.setMaxHeight(cellSize);

        for(int i = 0; i < rows; i ++)
            grid.getRowConstraints().add(rowConstraints);
        for(int i = 0; i < columns; i ++)
            grid.getColumnConstraints().add(columnConstraints);

        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                cells[i][j] = new Cell();
                grid.add(cells[i][j], i, j);
            }
        }
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);
    }

}
