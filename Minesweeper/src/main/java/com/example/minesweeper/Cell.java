package com.example.minesweeper;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Cell extends BorderPane {
    public static final int HIDDEN = 0, EXPOSED = 1;
    public static final int UNSELECTED = 10, SELECTED = 11;


    public static Image basicBackground = new Image(String.valueOf(Minesweeper.class.getResource("/img/basic-tile.png")));
    public static Image pressedBackground = new Image(String.valueOf(Minesweeper.class.getResource("/img/pressed-tile.png")));
    public static Image bombIcon = new Image(String.valueOf(Minesweeper.class.getResource("/img/bomb.png")));
    public static Image flag = new Image(String.valueOf(Minesweeper.class.getResource("/img/flag.png")));

    private int state = HIDDEN, selected = UNSELECTED;
    private WrappedImageView imageView = new WrappedImageView();

    public Cell() {
        setCenter(imageView);
        imageView.setImage(basicBackground);
    }



    public void setState(int state) {
        if(state == HIDDEN) {
            imageView.setImage(basicBackground);
        }
        else if(state == EXPOSED) {

        }
        this.state = state;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        if(state == EXPOSED)
            return;
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
