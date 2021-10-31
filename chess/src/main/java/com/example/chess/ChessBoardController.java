package com.example.chess;

import com.example.chess.pieces.Piece;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.crypto.spec.PSource;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessBoardController implements Initializable {
    // the board is indexed from black to white (black is on top)
    private BoardTile[] board;

    @FXML
    public GridPane boardPane;
    @FXML
    public StackPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maintainAspectRatio();
        initBoard();
        implementDragAndDrop();
    }

    private void implementDragAndDrop() {

    }

    private void initBoard() {
        board = new BoardTile[64];
        for(int i = 0; i < 8; i ++) {
            for(int j = 0; j < 8; j++) {
                //BLACK or WHITE tiles
                if(((i & 1) ^ (j & 1)) == 0)
                    board[8 * i + j] = new BoardTile(BoardTile.WHITE);
                else
                    board[8 * i + j] = new BoardTile(BoardTile.BLACK);

                boardPane.add(board[8 * i + j], j, i);
            }
        }
        // Add pieces to table

        {

            board[0].setPiece(Piece.BLACK_ROCK);
            board[1].setPiece(Piece.BLACK_KNIGHT);
            board[2].setPiece(Piece.BLACK_BISHOP);
            board[3].setPiece(Piece.BLACK_QUEEN);
            board[4].setPiece(Piece.BLACK_KING);
            board[5].setPiece(Piece.BLACK_BISHOP);
            board[6].setPiece(Piece.BLACK_KNIGHT);
            board[7].setPiece(Piece.BLACK_ROCK);
            board[8].setPiece(Piece.BLACK_PAWN);
            board[9].setPiece(Piece.BLACK_PAWN);
            board[10].setPiece(Piece.BLACK_PAWN);
            board[11].setPiece(Piece.BLACK_PAWN);
            board[12].setPiece(Piece.BLACK_PAWN);
            board[13].setPiece(Piece.BLACK_PAWN);
            board[14].setPiece(Piece.BLACK_PAWN);
            board[15].setPiece(Piece.BLACK_PAWN);

            board[63].setPiece(Piece.WHITE_ROCK);
            board[62].setPiece(Piece.WHITE_KNIGHT);
            board[61].setPiece(Piece.WHITE_BISHOP);
            board[60].setPiece(Piece.WHITE_KING);
            board[59].setPiece(Piece.WHITE_QUEEN);
            board[58].setPiece(Piece.WHITE_BISHOP);
            board[57].setPiece(Piece.WHITE_KNIGHT);
            board[56].setPiece(Piece.WHITE_ROCK);
            board[55].setPiece(Piece.WHITE_PAWN);
            board[54].setPiece(Piece.WHITE_PAWN);
            board[53].setPiece(Piece.WHITE_PAWN);
            board[52].setPiece(Piece.WHITE_PAWN);
            board[51].setPiece(Piece.WHITE_PAWN);
            board[50].setPiece(Piece.WHITE_PAWN);
            board[49].setPiece(Piece.WHITE_PAWN);
            board[48].setPiece(Piece.WHITE_PAWN);

        }



        boardPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
    }

    private void maintainAspectRatio() {

        for(int i = 0; i < 8; i ++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(12.5);
            //rowConstraints.setVgrow(Priority.NEVER);
            boardPane.getRowConstraints().add(rowConstraints);

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(12.5);
            //columnConstraints.setHgrow(Priority.NEVER);
            boardPane.getColumnConstraints().add(columnConstraints);
        }



        rootPane.widthProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            boardPane.setPrefSize(min, min);
        });

        boardPane.setGridLinesVisible(true);

        boardPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        rootPane.heightProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            boardPane.setPrefSize(min, min);
        });
    }
}
