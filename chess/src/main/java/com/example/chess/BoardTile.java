package com.example.chess;

import com.example.chess.pieces.Piece;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.security.PublicKey;

public class BoardTile extends StackPane {
    public static final int BLACK = 1, WHITE = 0;

    public static final Color[] color = new Color[]{
            Color.web("BDE5B9"),  //WHITE
            Color.web("67A27B")  // BLACK
    };

    public static final Background[] tileBackground = new Background[]{
            new Background(new BackgroundFill(color[WHITE], CornerRadii.EMPTY, Insets.EMPTY)),
            new Background(new BackgroundFill(color[BLACK], CornerRadii.EMPTY, Insets.EMPTY))
    };

    private int currentPiece = Piece.EMPTY;


    public BoardTile(int type) {
        setBackground(tileBackground[type]);
        setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        actions();
    }

    private void actions() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("click");
            }
        });
        setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
                System.out.println("drag over");

                dragEvent.consume();
            }
        });
    }

    public void setPiece(int type) {
        currentPiece = type;
        getChildren().clear();
        getChildren().add(Piece.getImage(type));
    }

    public int getCurrentPiece() {
        return currentPiece;
    }
}
