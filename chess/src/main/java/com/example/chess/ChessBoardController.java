package com.example.chess;

import com.example.chess.engine.Bitboard;
import com.example.chess.engine.Move;
import com.example.chess.pieces.Piece;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.channels.OverlappingFileLockException;
import java.util.BitSet;
import java.util.ResourceBundle;

public class ChessBoardController implements Initializable {
    @FXML
    public Pane boardOverlay;
    @FXML
    public Pane rootPane;
    @FXML
    private Board board;

    private Bitboard bitboard;
    private Move move = new Move();
    private ImageView pieceImage = new ImageView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBoard();
        addUserControl();
    }

    private void initBoard() {
        bitboard = Bitboard.boardToBitboard(board);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);

        for(int i = 0; i < 8; i ++) {
            board.getRowConstraints().add(rowConstraints);
            board.getColumnConstraints().add(columnConstraints);
        }

        rootPane.widthProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            board.setPrefSize(min, min);
            boardOverlay.setPrefSize(min, min);
        });
        rootPane.heightProperty().addListener((observableValue, number, t1) -> {
            double min = Math.min(rootPane.getWidth(), rootPane.getHeight());
            board.setPrefSize(min, min);
            boardOverlay.setPrefSize(min, min);
        });

        board.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        boardOverlay.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    private void addUserControl() {
        board.setOnMouseClicked(mouseEvent -> {
            mouseClick();
            mouseEvent.consume();
        });
        board.setOnMousePressed(mouseEvent -> {
            mousePressed(mouseEvent);
            mouseEvent.consume();
        });
        board.setOnMouseReleased(mouseEvent -> {
            mouseReleased(mouseEvent);
            mouseEvent.consume();
        });
        board.setOnMouseDragged(mouseEvent -> {
            mouseDragged(mouseEvent);
            mouseEvent.consume();
        });
    }

    private void mouseClick() {

    }
    private void mousePressed(MouseEvent mouseEvent) {
        int i = 7 - (int)(8 * mouseEvent.getY() / board.getHeight());
        int j =     (int)(8 * mouseEvent.getX() / board.getWidth());
        move.setStart(i, j);
        move.setPiece(board.getBoardTile(8*i+j).getPiece());
        board.getBoardTile(8*i+j).setPiece(Piece.EMPTY);

        pieceImage.setImage(Piece.getImage(move.getPiece()));
        pieceImage.setFitHeight(board.getHeight() / 8);
        pieceImage.setFitWidth(board.getWidth() / 8);
        boardOverlay.getChildren().add(pieceImage);
    }
    private void mouseReleased(MouseEvent mouseEvent) {
        int i = 7 - (int)(8 * mouseEvent.getY() / board.getHeight());
        int j =     (int)(8 * mouseEvent.getX() / board.getWidth());

        if(!(0 <= i && i < 8 && 0 <= j && j < 8 && mouseEvent.getX() >= 0 && mouseEvent.getY() >= 0)){
            board.getBoardTile(move.getStartIndex()).setPiece(move.getPiece());
            boardOverlay.getChildren().remove(pieceImage);
            return;
        }

        move.setDest(i, j);
        bitboard.makeMove(board, move);

        boardOverlay.getChildren().remove(pieceImage);
    }
    private void mouseDragged(MouseEvent mouseEvent) {
        pieceImage.setX(mouseEvent.getX() - pieceImage.getFitWidth() / 2);
        pieceImage.setY(mouseEvent.getY() - pieceImage.getFitHeight() / 2);
    }
}
