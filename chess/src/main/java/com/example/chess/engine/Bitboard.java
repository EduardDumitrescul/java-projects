package com.example.chess.engine;

import com.example.chess.BoardTile;
import com.example.chess.pieces.Piece;

import java.util.BitSet;

public class Bitboard {
    public static final int misc = 0;
    public static final int wk = 1, wq = 2, wr = 3, wb = 4, wn = 5, wp = 6;
    public static final int bk = 7, bq = 8, br = 9, bb = 10, bn = 11, bp = 12;

    private BitSet[] bitBoard = new BitSet[]{
            new BitSet(8),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64),
            new BitSet(64)
    };

    public static Bitboard boardToBitBoard(BoardTile[] board) {
        Bitboard bitboard = new Bitboard();
        for(int i = 0; i < 64; i ++) {
            switch(board[i].getPiece()) {
                case Piece.WHITE_KING: bitboard.setPiece(Piece.WHITE_KING, i);
                case Piece.WHITE_QUEEN: bitboard.setPiece(Piece.WHITE_QUEEN, i);
                case Piece.WHITE_ROCK: bitboard.setPiece(Piece.WHITE_ROCK, i);
                case Piece.WHITE_BISHOP: bitboard.setPiece(Piece.WHITE_BISHOP, i);
                case Piece.WHITE_KNIGHT: bitboard.setPiece(Piece.WHITE_KNIGHT, i);
                case Piece.WHITE_PAWN: bitboard.setPiece(Piece.WHITE_PAWN, i);
                case Piece.BLACK_KING: bitboard.setPiece(Piece.BLACK_KING, i);
                case Piece.BLACK_QUEEN: bitboard.setPiece(Piece.BLACK_QUEEN, i);
                case Piece.BLACK_ROCK: bitboard.setPiece(Piece.BLACK_ROCK, i);
                case Piece.BLACK_BISHOP: bitboard.setPiece(Piece.BLACK_BISHOP, i);
                case Piece.BLACK_KNIGHT: bitboard.setPiece(Piece.BLACK_KNIGHT, i);
                case Piece.BLACK_PAWN: bitboard.setPiece(Piece.BLACK_PAWN, i);
            }
        }
        return bitboard;
    }

    public void setPiece(int type, int pos) {
        bitBoard[type].set(pos);
    }

    public void removePiece(int type, int pos) {
        bitBoard[type].set(pos, 0);
    }
}
