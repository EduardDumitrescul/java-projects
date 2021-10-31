package com.example.chess.pieces;

import com.example.chess.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King {
    public static final ImageView whiteImage = new ImageView(), blackImage = new ImageView();

    public King() {
        whiteImage.setImage(new Image(String.valueOf(Game.class.getResource("img/white-king.png"))));
        blackImage.setImage(new Image(String.valueOf(Game.class.getResource("img/black-king.png"))));
    }
}
