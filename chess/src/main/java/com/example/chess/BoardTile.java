package com.example.chess;

import com.example.chess.pieces.Piece;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardTile extends StackPane {
    public static final int BLACK = 0, WHITE = 1;
    public static final Color[] color = new Color[]{
            Color.web("BDE5B9"),  //WHITE
            Color.web("67A27B")  // BLACK
    };
    public static final Background[] tileBackground = new Background[]{
            new Background(new BackgroundFill(color[WHITE], CornerRadii.EMPTY, Insets.EMPTY)),
            new Background(new BackgroundFill(color[BLACK], CornerRadii.EMPTY, Insets.EMPTY))
    };

    private int currentPiece = Piece.EMPTY;
    private final WrappedImageView imageView = new WrappedImageView();


    public BoardTile(int type) {
        setBackground(tileBackground[type]);
        setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        getChildren().add(imageView);
    }

    public void setPiece(int type) {
        currentPiece = type;
        imageView.setImage(Piece.getImage(type));
    }

    public int getPiece() {
        return currentPiece;
    }
}
