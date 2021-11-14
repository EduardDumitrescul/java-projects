package com.example.minesweeper;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cell extends BorderPane {
    public static final int N0 = 0, N1 = 1, N2 = 2, N3 = 3, N4 = 4, N5 = 5, N6 = 6, N7 = 7, N8 = 8, BOMB = 9, FLAG = 10, HIDDEN = 11;
    public static final int UNSELECTED = 100, SELECTED = 101;


    public static Image basicBackground   = new Image(String.valueOf(Minesweeper.class.getResource("/img/basic-tile.png")));
    public static Image pressedBackground = new Image(String.valueOf(Minesweeper.class.getResource("/img/pressed-tile.png")));
    public static Image bombIcon          = new Image(String.valueOf(Minesweeper.class.getResource("/img/bomb.png")));
    public static Image flag              = new Image(String.valueOf(Minesweeper.class.getResource("/img/flag.png")));
    public static Image oneIcon           = new Image(String.valueOf(Minesweeper.class.getResource("/img/one.png")));
    public static Image twoIcon           = new Image(String.valueOf(Minesweeper.class.getResource("/img/two.png")));
    public static Image threeIcon         = new Image(String.valueOf(Minesweeper.class.getResource("/img/three.png")));
    public static Image fourIcon          = new Image(String.valueOf(Minesweeper.class.getResource("/img/four.png")));
    public static Image fiveIcon          = new Image(String.valueOf(Minesweeper.class.getResource("/img/five.png")));
    public static Image sixIcon           = new Image(String.valueOf(Minesweeper.class.getResource("/img/six.png")));
    public static Image sevenIcon         = new Image(String.valueOf(Minesweeper.class.getResource("/img/seven.png")));
    public static Image eightIcon         = new Image(String.valueOf(Minesweeper.class.getResource("/img/eight.png")));

    private int state = HIDDEN, selected = UNSELECTED;
    private WrappedImageView imageView = new WrappedImageView();

    public Cell() {
        setCenter(imageView);
        setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        imageView.setImage(basicBackground);
    }



    public void setState(int state) {
        switch(state) {
            case N0: imageView.setImage(null); break;
            case N1: imageView.setImage(oneIcon); break;
            case N2: imageView.setImage(twoIcon); break;
            case N3: imageView.setImage(threeIcon); break;
            case N4: imageView.setImage(fourIcon); break;
            case N5: imageView.setImage(fiveIcon); break;
            case N6: imageView.setImage(sixIcon); break;
            case N7: imageView.setImage(sevenIcon); break;
            case N8: imageView.setImage(eightIcon); break;
            case FLAG: imageView.setImage(flag); break;
            case BOMB: imageView.setImage(bombIcon); break;
            case HIDDEN: imageView.setImage(basicBackground); break;

        }
        this.state = state;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        if(selected == SELECTED) {
            imageView.setImage(pressedBackground);
        }
        else if(selected == UNSELECTED) {
            imageView.setImage(basicBackground);
        }
    }

    public int getState() {
        return state;
    }
}
