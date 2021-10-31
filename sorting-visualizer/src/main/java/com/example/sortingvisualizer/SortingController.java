package com.example.sortingvisualizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SortingController implements Initializable {
    private static final int spacing = 4;
    private final int size = 48;
    private RectangleBar[] rectangles;
    private double[] array;

    private SelectSortAnimation selectSortAnimation;

    @FXML
    public HBox mainPane;

    private void generateArray() {
        array = new double[size];
        for(int i = 0; i < size; i ++) {
            array[i] = Math.random();
        }
    }

    private void generateChart() {
        rectangles = new RectangleBar[size];
        for(int i = 0; i < size; i++) {
            rectangles[i] = new RectangleBar();
            rectangles[i].setRectHeight(array[i]);
        }
    }

    private void resetChart() {
        for(int i = 0; i < size; i ++) {
            rectangles[i].setRectHeight(array[i]);
            rectangles[i].setBackground(RectangleBar.normalBackground);
        }
    }


    private void resizeChart() {
        RectangleBar.setRectMaxHeight( mainPane.getHeight());
        RectangleBar.setRectWidth(mainPane.getWidth() / size - spacing);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateArray();
        generateChart();
        selectSortAnimation = new SelectSortAnimation(array, rectangles);

        for(int i = 0; i < size; i++)
            mainPane.getChildren().add(rectangles[i]);

        mainPane.setSpacing(spacing);

        mainPane.widthProperty().addListener((observableValue, number, t1) -> resizeChart());
        mainPane.heightProperty().addListener((observableValue, number, t1) -> resizeChart());
    }


    public void reset() {
        selectSortAnimation.stop();
        generateArray();
        resetChart();
        resizeChart();
    }

    private boolean checkArraySorted() {
        for(int i = 0; i < array.length - 1; i++)
            if(array[i] > array[i+1])
                return false;
        return true;
    }

    public void start(ActionEvent actionEvent) {
        if(selectSortAnimation.isRunning())
            return;
        if(checkArraySorted())
            reset();
        selectSortAnimation.play();

    }
}
