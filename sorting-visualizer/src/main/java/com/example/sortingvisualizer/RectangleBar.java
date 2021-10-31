package com.example.sortingvisualizer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class RectangleBar extends Pane {
    public static final Background normalBackground = new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background focusedBackground = new Background(new BackgroundFill(Color.VIOLET, CornerRadii.EMPTY, Insets.EMPTY));

    public static DoubleProperty width = new SimpleDoubleProperty(0);
    public DoubleProperty height = new SimpleDoubleProperty(0);
    public static DoubleProperty maxHeight = new SimpleDoubleProperty();

    public RectangleBar() {
        setBackground(normalBackground);

        height.addListener((observableValue, number, t1) -> updateSize());
        maxHeight.addListener((observableValue, number, t1) -> updateSize());
    }

    private void updateSize() {
        setPrefHeight(height.get() * maxHeight.get());
        setMaxHeight(height.get() * maxHeight.get());
        setPrefWidth(width.get());
        setMaxWidth(width.get());
    }

    public static void swap(RectangleBar r1, RectangleBar r2) {
        double h1 = r1.height.get();
        double h2 = r2.height.get();
        r1.height.set(h2);
        r2.height.set(h1);
    }

    public static void setRectMaxHeight(double value) {
        maxHeight.set(value);
    }

    public void setRectHeight(double value) {
        height.set(value);
    }

    public static void setRectWidth(double value) {
        width.set(value);
    }
}
