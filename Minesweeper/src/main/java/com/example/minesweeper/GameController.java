package com.example.minesweeper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
        addVisuals();
    }

    private Cell currentCell = null;
    private void addVisuals() {
        grid.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cell cell = computeCurrentCell(mouseEvent);

                if(currentCell != cell) {
                    if(cell != null)
                        cell.setSelected(Cell.SELECTED);
                    if(currentCell != null)
                        currentCell.setSelected(Cell.UNSELECTED);
                    currentCell = cell;
                }

                mouseEvent.consume();
            }
        });
        grid.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cell cell = computeCurrentCell(mouseEvent);

                if(currentCell != cell) {
                    if(cell != null)
                        cell.setSelected(Cell.SELECTED);
                    if(currentCell != null)
                        currentCell.setSelected(Cell.UNSELECTED);
                    currentCell = cell;
                }

                mouseEvent.consume();
            }
        });

        grid.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(currentCell != null) {
                    currentCell.setSelected(Cell.UNSELECTED);
                    currentCell = null;
                }
            }
        });
    }

    private Cell computeCurrentCell(MouseEvent mouseEvent) {
        try {
            return cells[(int)(mouseEvent.getY() / cellSize)][(int)(mouseEvent.getX() / cellSize)];
        }
        catch(Exception ex) {
            return null;
        }


    }

    private void initializeGrid() {
        cells = new Cell[rows][columns];

        grid.setMinSize(cellSize * columns, cellSize * rows);
        grid.setPrefSize(cellSize * columns, cellSize * rows);
        grid.setMaxSize(cellSize * columns, cellSize * rows);
        grid.setBackground(new Background(new BackgroundFill(Color.PALEGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));

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
                grid.add(cells[i][j], j, i);
            }
        }
        grid.setAlignment(Pos.CENTER);
    }

}
