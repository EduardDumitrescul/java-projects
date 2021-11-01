package com.example.chess;

import com.example.chess.pieces.Piece;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Board extends GridPane {
    private BoardTile board[] = new BoardTile[64];

    public Board() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j ++) {
                board[8*i+j] = new BoardTile((i+j)%2);
                add(board[8*i+j], j, 7-i);

                board[8*i+j].getChildren().add(new Label(Integer.toString(8*i+j)));
            }
        }

        setStartingPosition();
    }

    public void setStartingPosition() {
        board[0].setPiece(Piece.WHITE_ROCK);
        board[1].setPiece(Piece.WHITE_KNIGHT);
        board[2].setPiece(Piece.WHITE_BISHOP);
        board[3].setPiece(Piece.WHITE_QUEEN);
        board[4].setPiece(Piece.WHITE_KING);
        board[5].setPiece(Piece.WHITE_BISHOP);
        board[6].setPiece(Piece.WHITE_KNIGHT);
        board[7].setPiece(Piece.WHITE_ROCK);
        board[8].setPiece(Piece.WHITE_PAWN);
        board[9].setPiece(Piece.WHITE_PAWN);
        board[10].setPiece(Piece.WHITE_PAWN);
        board[11].setPiece(Piece.WHITE_PAWN);
        board[12].setPiece(Piece.WHITE_PAWN);
        board[13].setPiece(Piece.WHITE_PAWN);
        board[14].setPiece(Piece.WHITE_PAWN);
        board[15].setPiece(Piece.WHITE_PAWN);

        board[63].setPiece(Piece.BLACK_ROCK);
        board[62].setPiece(Piece.BLACK_KNIGHT);
        board[61].setPiece(Piece.BLACK_BISHOP);
        board[60].setPiece(Piece.BLACK_KING);
        board[59].setPiece(Piece.BLACK_QUEEN);
        board[58].setPiece(Piece.BLACK_BISHOP);
        board[57].setPiece(Piece.BLACK_KNIGHT);
        board[56].setPiece(Piece.BLACK_ROCK);
        board[55].setPiece(Piece.BLACK_PAWN);
        board[54].setPiece(Piece.BLACK_PAWN);
        board[53].setPiece(Piece.BLACK_PAWN);
        board[52].setPiece(Piece.BLACK_PAWN);
        board[51].setPiece(Piece.BLACK_PAWN);
        board[50].setPiece(Piece.BLACK_PAWN);
        board[49].setPiece(Piece.BLACK_PAWN);
        board[48].setPiece(Piece.BLACK_PAWN);
    }
}
