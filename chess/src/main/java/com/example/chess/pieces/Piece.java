package com.example.chess.pieces;

import com.example.chess.WrappedImageView;
import javafx.scene.image.Image;

public class Piece {
    public static final int EMPTY = 0;
    public static final int WHITE_KING = 1;
    public static final int BLACK_KING = 2;
    public static final int WHITE_QUEEN = 3;
    public static final int BLACK_QUEEN = 4;
    public static final int WHITE_ROOK = 5;
    public static final int BLACK_ROOK = 6;
    public static final int WHITE_BISHOP = 7;
    public static final int BLACK_BISHOP = 8;
    public static final int WHITE_KNIGHT = 9;
    public static final int BLACK_KNIGHT = 10;
    public static final int WHITE_PAWN = 11;
    public static final int BLACK_PAWN = 12;

    public static final WrappedImageView[] imageView = new WrappedImageView[]{
            new WrappedImageView(),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-king.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-king.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-queen.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-queen.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-rook.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-rook.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-bishop.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-bishop.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-knight.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-knight.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/white-pawn.png")))),
            new WrappedImageView(new Image(String.valueOf(Piece.class.getResource("/img/black-pawn.png")))),
    };

    public static final Image[] image = new Image[]{
            new Image(String.valueOf(Piece.class.getResource("/img/transparent.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-king.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-king.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-queen.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-queen.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-rook.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-rook.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-bishop.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-bishop.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-knight.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-knight.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/white-pawn.png"))),
            new Image(String.valueOf(Piece.class.getResource("/img/black-pawn.png"))),
    };

    public static Image getImage(int type) {
        Image temp = new Image(image[type].getUrl());
        return temp;
    }
}
