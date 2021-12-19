package com.example.minesweeper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    public static final int EASY = 0, NORMAL = 1, HARD = 2 , EXTREME = 3;
    private static final int[] rowsOptions = new int[]{10, 14, 20, 24};
    private static final int[] columnsOptions = new int[]{10, 18, 30, 40};
    private static final int[] numberOfMines = new int[]{12, 36, 100};
    private static final int cellSize = 26;
    private static Font font;


    @FXML
    private Button returnButton;
    @FXML
    private Text timerView;
    @FXML
    private Text timeLabel;
    @FXML
    private Text minesLabel;
    @FXML
    private Text minesCountLabel;
    @FXML
    private GridPane grid;


    private Cell[][] cells;
    private int[][] a;
    private Timer timer = new Timer();

    private int difficulty = 0, rows, columns;
    private int mines, noFlags = 0, remainingMines, exposedCells = 0;
    private int gameDuration = 0;
    boolean gameFinished = false;
    
    public void createGame(int value) {
        difficulty = value;
        rows = rowsOptions[value];
        columns = columnsOptions[value];
        if(value != EXTREME)
            mines = numberOfMines[value];
        else
            mines = 120 + (int)(Math.random() * 30);
        remainingMines = mines;

        font = Font.loadFont(String.valueOf(Minesweeper.class.getResource("/fonts/arcade_ya/ARCADE_I.TTF")), columns);

        minesLabel.setFont(font);
        minesLabel.setFill(Color.PALEGOLDENROD);
        minesCountLabel.setFont(font);
        minesCountLabel.setFill(Color.PALEGOLDENROD);

        if(difficulty != EXTREME)
            minesCountLabel.setText(Integer.toString(mines));
        else
            minesCountLabel.setText("???");

        timerView.setFont(font);
        timerView.setFill(Color.PALEGOLDENROD);
        timeLabel.setFont(font);
        timeLabel.setFill(Color.PALEGOLDENROD);

        returnButton.setFont(font);
        returnButton.setTextFill(Color.PALEGOLDENROD);


        initializeGrid();
        addVisuals();
        initializeTimer();
    }

    private Cell currentCell = null, startingCell = null;
    private int mouseButton = 0;
    private void addVisuals() {
        grid.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(gameFinished)
                    return;

                if(mouseEvent.isPrimaryButtonDown()) mouseButton = 1;
                else if(mouseEvent.isSecondaryButtonDown()) mouseButton = 2;

                int i = (int)(mouseEvent.getY() / cellSize);
                int j = (int)(mouseEvent.getX() / cellSize);
                startingCell = computeCurrentCell(mouseEvent);
                currentCell = startingCell;

                if(mouseButton == 1)
                    setFocused(null, currentCell);

                if(mouseButton == 2) {
                    setFlag(i, j);
                }

            }
        });

        grid.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(gameFinished)
                    return;

                Cell cell = computeCurrentCell(mouseEvent);

                if(mouseButton == 1) {
                    setFocused(currentCell, cell);
                    currentCell = cell;
                }
            }
        });

        grid.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(gameFinished)
                    return;

                Cell cell = computeCurrentCell(mouseEvent);

                if(mouseButton == 1) {
                    setFocused(cell, null);

                    int i = (int)(mouseEvent.getY() / cellSize);
                    int j = (int)(mouseEvent.getX() / cellSize);
                    revealBlock(i, j);

                }
            }
        });
    }

    private void setFlag(int i, int j) {
        Cell cell = cells[i][j];
        try {
            if(cell.getState() == Cell.HIDDEN) {
                cell.setState(Cell.FLAG);
                noFlags ++;
                if(a[i][j] == -1) {
                    remainingMines --;
                }
            }

            else if(cell.getState() == Cell.FLAG) {
                cell.setState(Cell.HIDDEN);
                noFlags --;
                if(a[i][j] == -1) {
                    remainingMines ++;
                }
            }
            if(difficulty != EXTREME)
                minesCountLabel.setText(Integer.toString(Math.max(0, mines - noFlags)));
        }
        catch (Exception ex) {

        }
    }

    private void setFocused(Cell lastCell, Cell currentCell) {
        try{
            if(lastCell.getState() == Cell.HIDDEN)
                lastCell.setSelected(Cell.UNSELECTED);
        }
        catch (Exception ex){}
        try {
            if(currentCell.getState() == Cell.HIDDEN)
                currentCell.setSelected(Cell.SELECTED);
        }
        catch (Exception ex){}
    }

    private void revealCell(int i, int j) {
        try {
            if(cells[i][j].getState() != Cell.HIDDEN)
                return;

            if(a[i][j] == -1) {
                loseGame();
            }
            else
                cells[i][j].setState(a[i][j]);
            exposedCells ++;

            if(exposedCells == rows * columns - mines) {
                winGame();
            }
        }
        catch (Exception ignored){}

    }
     // for revealing a block
    private void revealBlock(int i, int j) {
        if(!(0 <= i && i < rows && 0 <= j && j < columns))
            return;
        if(cells[i][j].getState() != Cell.HIDDEN)
            return;

        revealCell(i, j);

        if(a[i][j] == 0) {
            revealBlock(i-1, j-1);
            revealBlock(i-1, j);
            revealBlock(i-1, j+1);
            revealBlock(i, j-1);
            revealBlock(i, j+1);
            revealBlock(i+1, j-1);
            revealBlock(i+1, j);
            revealBlock(i+1, j+1);
        }

    }

    private void loseGame() {
        gameFinished = true;
        timer.cancel();
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++){
                if(a[i][j] == -1) {
                    cells[i][j].setState(Cell.BOMB);
                }
            }
        }
    }

    private void winGame() {
        gameFinished = true;
        timer.cancel();
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


        a = new int[rows][columns];
        for(int m = 0; m < mines; m ++) {
            int i = (int)(Math.random() * rows);
            int j = (int)(Math.random() * columns);

            if(a[i][j] == 0)
                a[i][j] = -1;
            else
                m --;
        }

        int[] di = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dj = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};

        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(a[i][j] == -1)
                for(int k = 0; k < 8; k ++){
                    int ii = i + di[k];
                    int jj = j + dj[k];
                    if(0 <= ii && ii < rows && 0 <= jj && jj < columns && a[ii][jj] != -1)
                        a[ii][jj] ++;
                }
            }
        }
    }

    private void initializeTimer() {
        Date startDate = new Date();
        TimerTask updateTimer = new TimerTask() {
            @Override
            public void run() {
                Date currentDate = new Date();
                long duration = currentDate.getTime() - startDate.getTime();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timerView.setText(Long.toString(duration / 1000));
                        gameDuration = (int) (duration / 1000);
                    }
                });

            }
        };

        timer.scheduleAtFixedRate(updateTimer, 0, 1000);
    }

    void printNumberGrid() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++)
                System.out.print (a[i][j] + " ");
            System.out.println();
        }
    }

    public void returnToMainMenu(ActionEvent actionEvent) {
        Minesweeper.setMenuScene();

    }
}
