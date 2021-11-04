package com.example.chess.engine;

import com.example.chess.Board;
import com.example.chess.WrappedImageView;
import com.example.chess.pieces.Piece;

import java.text.ParseException;
import java.util.ArrayList;

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
        initializeDefaultBitboard();
    }

    private void initializeDefaultBitboard() {
        setPiece(Piece.WHITE_ROOK, 0);
        setPiece(Piece.WHITE_KNIGHT, 1);
        setPiece(Piece.WHITE_BISHOP, 2);
        setPiece(Piece.WHITE_QUEEN, 3);
        setPiece(Piece.WHITE_KING, 4);
        setPiece(Piece.WHITE_BISHOP, 5);
        setPiece(Piece.WHITE_KNIGHT, 6);
        setPiece(Piece.WHITE_ROOK, 7);
        setPiece(Piece.WHITE_PAWN, 8);
        setPiece(Piece.WHITE_PAWN, 9);
        setPiece(Piece.WHITE_PAWN, 10);
        setPiece(Piece.WHITE_PAWN, 11);
        setPiece(Piece.WHITE_PAWN, 12);
        setPiece(Piece.WHITE_PAWN, 13);
        setPiece(Piece.WHITE_PAWN, 14);
        setPiece(Piece.WHITE_PAWN, 15);

        setPiece(Piece.BLACK_ROOK, 63);
        setPiece(Piece.BLACK_KNIGHT, 62);
        setPiece(Piece.BLACK_BISHOP, 61);
        setPiece(Piece.BLACK_KING, 60);
        setPiece(Piece.BLACK_QUEEN, 59);
        setPiece(Piece.BLACK_BISHOP, 58);
        setPiece(Piece.BLACK_KNIGHT, 57);
        setPiece(Piece.BLACK_ROOK, 56);
        setPiece(Piece.BLACK_PAWN, 55);
        setPiece(Piece.BLACK_PAWN, 54);
        setPiece(Piece.BLACK_PAWN, 53);
        setPiece(Piece.BLACK_PAWN, 52);
        setPiece(Piece.BLACK_PAWN, 51);
        setPiece(Piece.BLACK_PAWN, 50);
        setPiece(Piece.BLACK_PAWN, 49);
        setPiece(Piece.BLACK_PAWN, 48);

        bitboard[0] |= 1; // who moves:  White = 1, Black = 0
        bitboard[0] |= 2; // white king can king castle
        bitboard[0] |= 4; // white king can queen castle
        bitboard[0] |= 8; // black king can king castle
        bitboard[0] |= 16; // black king can queen castle

        bitboard[0] &= (universal ^ 32); // en passant
        // 6-11 position of pawn

        computeBitboard();

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

    public ArrayList<Integer> computeAttackingSquares(int squareIndex) {
        int piece = 0;
        for(int i = 1; i < 13; i ++) {
            if((bitboard[i] & bit[squareIndex]) != 0) {
                piece = i;
                break;
            }

        }

       if(piece == Piece.WHITE_PAWN)
           return computeAttackingWhitePawn(squareIndex);
       return new ArrayList<>();
    }

    private ArrayList<Integer> computeAttackingWhitePawn(int squareIndex) {
        ArrayList<Integer> att = new ArrayList<>();
        int rankIndex = toRankIndex(squareIndex);
        int fileIndex = toFileIndex(squareIndex);
        int pos;

        //Forward 1 move
        pos = squareIndex + 8;
        if((occupied & bit[pos]) == 0)
            att.add(pos);

        //Left take
        if(fileIndex != 0) {
            pos = squareIndex + 7;
            System.out.println("OK");
            if((black & bit[pos]) != 0)
                att.add(pos);
            else if(getEnPassantIndex() == pos - 8)
                att.add(pos);
        }

        //Right take
        if(fileIndex != 7) {
            pos = squareIndex + 9;
            if((black & bit[pos]) != 0)
                att.add(pos);
            else if(getEnPassantIndex() == pos - 8)
                att.add(pos);
        }

        return att;
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
        System.out.println("move");
        if(move.getPiece() == Piece.EMPTY) return;

        int start = move.getStartIndex();
        int dest = move.getDestIndex();
        setFalse(move.getPiece(), start);
        setFalse(move.getEndPiece(), dest);
        setTrue(move.getPiece(), dest);
        board.getBoardTile(dest).setPiece(move.getPiece());


        //   CASTLING
        if(move.getPiece() == Piece.WHITE_KING){
            setFalse(0, 1);
            setFalse(0, 2);
        }
        if(move.getPiece() == Piece.WHITE_ROOK && start == 7)
            setFalse(0, 1);
        if(move.getPiece() == Piece.WHITE_ROOK && start == 0)
            setFalse(0, 2);

        if(move.getPiece() == Piece.BLACK_KING){
            setFalse(0, 3);
            setFalse(0, 4);
        }
        if(move.getPiece() == Piece.BLACK_ROOK && start == 63)
            setFalse(0, 3);
        if(move.getPiece() == Piece.BLACK_ROOK && start == 56)
            setFalse(0, 4);

        //EN PASSANT
        setFalse(0, 5);
        if(move.getPiece() == Piece.WHITE_PAWN && start + 16 == dest) {
            setTrue(0, 5);
            for(int i = 11; i >= 6; i --, dest >>= 1)
                if((dest & 1) == 1)
                    setTrue(0, i);
                else
                    setFalse(0, i);
        }
        if(move.getPiece() == Piece.BLACK_PAWN && start - 16 == dest) {
            setTrue(0, 5);
            for(int i = 11; i >= 6; i --, dest >>= 1)
                if((dest & 1) == 1)
                    setTrue(0, i);
                else
                    setFalse(0, i);
        }


        // Change who moves
        bitboard[0] ^= 1;

        computeBitboard();

    }

    public void toBoard(Board board) {
        for(int i = 1; i <= 12; i++) {
            for(long value = bitboard[i]; value != 0; value = removeLSB(value)){
                int squareIndex = findBitIndex(findLSB(value));
                board.getBoardTile(squareIndex).setPiece(i);
            }
        }
    }

    public void setPiece(int piece, int squareIndex) {
        setTrue(piece, squareIndex);
        if((bitboard[piece] & bit[squareIndex]) != 0)
            System.out.println("OK");
    }

    public void removePiece(int piece, int squareIndex) {
        setFalse(piece, squareIndex);
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

    private void computeBitboard() {
        white = bitboard[Piece.WHITE_PAWN] |
                bitboard[Piece.WHITE_KNIGHT] |
                bitboard[Piece.WHITE_BISHOP] |
                bitboard[Piece.WHITE_ROOK] |
                bitboard[Piece.WHITE_QUEEN] |
                bitboard[Piece.WHITE_KING];
        black = bitboard[Piece.BLACK_PAWN] |
                bitboard[Piece.BLACK_KNIGHT] |
                bitboard[Piece.BLACK_BISHOP] |
                bitboard[Piece.BLACK_ROOK] |
                bitboard[Piece.BLACK_QUEEN] |
                bitboard[Piece.BLACK_KING];

        occupied = white | black;
    }

    private int getEnPassantIndex() {
        int en_passant = -1;
        if((bitboard[0] & bit[5]) != 0) {
            en_passant = 0;
            for(int i = 6; i < 12; i++) {
                en_passant = 2 * en_passant;
                if((bitboard[0] & bit[i]) != 0)
                    en_passant ++;
            }
        }
        return en_passant;
    }

    private void setFalse(int type , int pos) {
        bitboard[type] &= (universal ^ bit[pos]);
    }
    private void setTrue(int type, int pos) {
        bitboard[type] |= bit[pos];
    }
}
