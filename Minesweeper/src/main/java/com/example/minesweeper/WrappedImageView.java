package com.example.minesweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WrappedImageView extends ImageView {
    public WrappedImageView() {
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        setFitHeight(height);
        setFitWidth(width);
    }

    @Override
    public double prefWidth(double height)
    {
        Image I=getImage();
        if (I==null) return minWidth(height);
        return I.getWidth();
    }

    @Override
    public double prefHeight(double width)
    {
        Image I=getImage();
        if (I==null) return minHeight(width);
        return I.getHeight();
    }

    @Override
    public double minWidth(double v) {
        return 0;
    }

    @Override
    public double minHeight(double v) {
        return 0;
    }
}
