package com.example.chess.engine;

import com.example.chess.Board;
import com.example.chess.pieces.Piece;

public class Bitboard {
    public static final int misc = 0;
    public static final int wk = 1, wq = 3, wr = 5, wb = 7, wn = 9, wp = 11;
    public static final int bk = 2, bq = 4, br = 5, bb = 8, bn = 10, bp = 12;

    private long[] bitboard = new long[13];

    private boolean valuesInitialized = false;
    private static long universal = -1;
    private static long empty;
    private static long[] fileSet;
    private static long[] rankSet;
    private static long[] bit = new long[64];
    private static int[] bitIndex = new int[67];

    private long occupied;
    private long white;
    private long black;


    public Bitboard() {
        initializeValues();
    }

    private void initializeValues() {
        if (valuesInitialized) return;
        valuesInitialized = true;

        bit[0] |= 1;
        bitIndex[1] = 0;
        for(int i = 1; i < 64; i ++) {
            bit[i] = bit[i-1] << 1;

            int mod = (int)(bit[i] % 67);
            if(mod < 0) mod += 67;
            bitIndex[mod] = i;
        }
    }

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
        bitboard[move.getPiece()] &= (universal ^ bit[start]);   // set to false
        bitboard[move.getEndPiece()] &= (universal ^ bit[dest]);   // set to false
        bitboard[move.getPiece()] |= (bit[dest]);             // set to true
        board.getBoardTile(dest).setPiece(move.getPiece());

    }

    public Board toBoard() {
        Board board = new Board();
        for(int i = 1; i <= 12; i++) {
            for(long value = bitboard[i]; value > 0; value = removeLSB(value)){
                long lsb = findLSB(value);
                int squareIndex = bitIndex[(int)(lsb % 67)];
                board.getBoardTile(squareIndex).setPiece(i);
            }
        }
        return board;
    }

    public void setPiece(int piece, int squareIndex) {
        bitboard[piece] |= bit[squareIndex];
    }

    public void removePiece(int piece, int squareIndex) {
        bitboard[piece] &= (universal ^ bit[squareIndex]);
    }

    public void print() {
        for(int i = 0; i < 13; i++) {
            System.out.println(bitboard[i]);
        }
        System.out.println("_________________________________");
    }

    public static int toSquareIndex(int rankIndex, int fileIndex){
        return 8 * rankIndex + fileIndex;
    }

    public static int toRankIndex(int squareIndex) {
        return squareIndex / 8;
    }

    public static int toFileIndex(int squareIndex) {
        return squareIndex % 8;
    }

    public static int toDiagonalIndex(int rankIndex, int fileIndex) {
        return (rankIndex - fileIndex) & 15;
    }

    public static int toAntiDiagonalIndex(int rankIndex, int fileIndex) {
        return (rankIndex + fileIndex) ^ 7;
    }

    public static long findLSB(long number) {
        return number & (-number);
    }

    public static long removeLSB(long number) {
        return number ^ findLSB((number));
    }

    public static int findBitIndex(long p2) {
        int mod  = (int)(p2 % 67);
        if(mod < 0) mod += 67;
        return bitIndex[mod];
    }
}
