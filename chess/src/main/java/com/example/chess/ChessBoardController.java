package com.example.chess;

import com.example.chess.pieces.Piece;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ChessBoardController implements Initializable {
    @FXML
    public AnchorPane boardContainer;
    @FXML
    public Pane rootPane;
    @FXML
    private Board board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBoard();
    }


    private void initBoard() {
        board = new Board();
        boardContainer.getChildren().add(board);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);

        for(int i = 0; i < 8; i ++) {
            board.getRowConstraints().add(rowConstraints);
            board.getColumnConstraints().add(columnConstraints);
        }

        AnchorPane.setTopAnchor(board, 0d);
        AnchorPane.setLeftAnchor(board, 0d);
        AnchorPane.setBottomAnchor(board, 0d);
        AnchorPane.setRightAnchor(board, 0d);

        rootPane.widthProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            boardContainer.setPrefSize(min, min);
        });
        rootPane.heightProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            boardContainer.setPrefSize(min, min);
        });

        boardContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
