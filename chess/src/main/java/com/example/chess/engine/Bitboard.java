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
    private static long empty = 0;
    private static long[] fileSet = new long[8];   // a..h   - vertical
    private static long[] rankSet = new long[8];   // 0..7   - horizontal
    private static long[] diagonalSet = new long[16];  // a1..h8
    private static long[] antiDiagonalSet = new long[16]; //a8..h1
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

        // Compute bit values
        bit[0] |= 1;
        for(int i = 1; i < 64; i ++)
            bit[i] = bit[i-1] << 1;

        // Compute bit indexes
        for(int i = 0; i < 64; i ++) {
            int mod = (int)(bit[i] % 67);
            if(mod < 0) mod += 67;
            bitIndex[mod] = i;
        }

        //compute rankSet
        rankSet[0] ^= (bit[8] - 1);
        for(int i = 1; i < 8; i ++)
            rankSet[i] = rankSet[i-1] << 8;

        //compute fileSet
        for(int i = 0; i < 8; i++)
            fileSet[0] |= bit[8 * i];
        for(int i = 1; i < 8; i++)
            fileSet[i] = fileSet[i-1] << 1;

        //compute diagonals
        for(int rank = 0; rank < 8; rank ++) {
            for(int file = 0; file < 8; file ++) {
                diagonalSet[toDiagonalIndex(rank, file)] |= bit[toSquareIndex(rank, file)];
                antiDiagonalSet[toAntiDiagonalIndex(rank, file)] |= bit[toSquareIndex(rank, file)];
            }
        }

        //testPrecomputedValues();
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

    private void testPrecomputedValues() {
        System.out.println("universal");
        printBitTable(universal);

        System.out.println("empty");
        printBitTable(empty);

        for(int i = 0; i < 64; i ++){
            System.out.println("bit " + i);
            printBitTable(bit[i]);
        }

        for(int i = 0; i < 8; i ++) {
            System.out.println("rank " + i);
            printBitTable(rankSet[i]);
        }

        for(int i = 0; i < 8; i ++) {
            System.out.println("file " + i);
            printBitTable(fileSet[i]);
        }

        for(int i = 0; i < 16; i ++) {
            System.out.println("diagonal " + i);
            printBitTable(diagonalSet[i]);
        }

        for(int i = 0; i < 16; i ++) {
            System.out.println("anti diagonal " + i);
            printBitTable(antiDiagonalSet[i]);
        }
    }

    private static void printBitTable(long value) {
        long[][] x = new long[8][8];
        for(int rank = 0; rank < 8; rank ++) {
            for(int file = 0 ; file < 8; file ++) {
                if((value & bit[8*rank+file]) != 0)
                    x[7 - rank][file] = 1;
            }
        }

        for(int i = 0; i < 8; i ++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(x[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("*******************");

    }
}
