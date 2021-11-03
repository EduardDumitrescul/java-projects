package com.example.chess.engine;

import com.example.chess.Board;
import com.example.chess.pieces.Piece;

import java.util.BitSet;

public class Bitboard {
    public static final int misc = 0;
    public static final int wk = 1, wq = 3, wr = 5, wb = 7, wn = 9, wp = 11;
    public static final int bk = 2, bq = 4, br = 5, bb = 8, bn = 10, bp = 12;

    private BitSet[] bitboard = new BitSet[]{
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

    //Try not to use because it loses info about castling, en-passant
    public static Bitboard boardToBitboard(Board board) {
        Bitboard bitboard = new Bitboard();
        for(int i = 0; i < 64; i++) {
            switch(board.getBoardTile(i).getPiece()) {
                case Piece.WHITE_KING: bitboard.setPiece(Piece.WHITE_KING, i); break;
                case Piece.WHITE_QUEEN: bitboard.setPiece(Piece.WHITE_QUEEN, i); break;
                case Piece.WHITE_ROOK: bitboard.setPiece(Piece.WHITE_ROOK, i); break;
                case Piece.WHITE_BISHOP: bitboard.setPiece(Piece.WHITE_BISHOP, i); break;
                case Piece.WHITE_KNIGHT: bitboard.setPiece(Piece.WHITE_KNIGHT, i); break;
                case Piece.WHITE_PAWN: bitboard.setPiece(Piece.WHITE_PAWN, i); break;
                case Piece.BLACK_KING: bitboard.setPiece(Piece.BLACK_KING, i); break;
                case Piece.BLACK_QUEEN: bitboard.setPiece(Piece.BLACK_QUEEN, i); break;
                case Piece.BLACK_ROOK: bitboard.setPiece(Piece.BLACK_ROOK, i); break;
                case Piece.BLACK_BISHOP: bitboard.setPiece(Piece.BLACK_BISHOP, i); break;
                case Piece.BLACK_KNIGHT: bitboard.setPiece(Piece.BLACK_KNIGHT, i); break;
                case Piece.BLACK_PAWN: bitboard.setPiece(Piece.BLACK_PAWN, i); break;
            }
        }
        return bitboard;
    }

    public void makeMove(Board board, Move move) {
        if(move.getPiece() == Piece.EMPTY) return;

        int start = move.getStartIndex();
        int dest = move.getDestIndex();
        bitboard[move.getPiece()].set(start, start, false);
        bitboard[move.getEndPiece()].set(dest, dest, false);
        bitboard[move.getPiece()].set(dest, dest, true);
        board.getBoardTile(dest).setPiece(move.getPiece());

    }

    public Board toBoard() {
        Board board = new Board();
        for(int i = 1; i <= 12; i++) {
            for(int j = bitboard[i].nextSetBit(0); j >= 0; j = bitboard[i].nextSetBit(j + 1))
                board.getBoardTile(j).setPiece(i);
        }
        return board;
    }

    public void setPiece(int type, int pos) {
        bitboard[type].set(pos);
    }

    public void removePiece(int type, int pos) {
        bitboard[type].set(pos, 0);
    }

    public void print() {
        for(int i = 0; i < 13; i++) {
            System.out.println(bitboard[i]);
        }
        System.out.println("_________________________________");
    }
}
