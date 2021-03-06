package com.example.chess.engine;

import com.example.chess.pieces.Piece;

import java.nio.LongBuffer;
import java.util.BitSet;

public class Move {
    private BitSet start = new BitSet(6);
    private BitSet dest = new BitSet(6);
    private int piece, endPiece;

    public void setStart(int i, int j) {
        start.clear();
        if(!(0 <= i && i <= 7 && 0 <= j && j <= 7)) {
            System.out.println("Out of bounds move!");
            System.exit(1);
        }

        if((i & 1) == 1) start.set(2);
        if((i & 2) == 2) start.set(1);
        if((i & 4) == 4) start.set(0);

        if((j & 1) == 1) start.set(5);
        if((j & 2) == 2) start.set(4);
        if((j & 4) == 4) start.set(3);
    }

    public void setDest(int i, int j) {
        dest.clear();
        if(!(0 <= i && i <= 7 && 0 <= j && j <= 7)) {
            System.out.println("Out of bounds move!");
            System.exit(1);
        }
        if((i & 1) == 1) dest.set(2);
        if((i & 2) == 2) dest.set(1);
        if((i & 4) == 4) dest.set(0);

        if((j & 1) == 1) dest.set(5);
        if((j & 2) == 2) dest.set(4);
        if((j & 4) == 4) dest.set(3);
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public void setEndPiece(int endPiece) {
        this.endPiece = endPiece;
    }

    public int getStartIndex() {
        int p = 0;
        for(int i = 0; i < 6; i++) {
            p = p << 1;
            if(start.get(i))
                p ^= 1;
        }
        return p;
    }

    public int getDestIndex() {
        int p = 0;
        for(int i = 0; i < 6; i++) {
            p = p << 1;
            if(dest.get(i))
                p ^= 1;
        }
        return p;
    }

    public int getPiece() {
        return piece;
    }

    public int getEndPiece() {
        return endPiece;
    }
}
