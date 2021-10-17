package com.example.hexagondijkstra;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MapView extends BorderPane {
    private int rows, columns, xOffset;
    private double width, height;
    private double aspectRatio, radius, length;

    private Hexagon[][] hexagon;

    private Pane map;

    /*
        Pane coordinates: x - horizontal starting from left
                          y - vertical starting from top
        hexagon[x][y] coordinates: x - horizontal starting from left
                                   y - vertical starting from top
    */

    public MapView(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        xOffset = (columns - 1) / 2;
        aspectRatio = (2 * Math.sqrt(3)  / 3) * (columns - 1) / (rows - 1);

        map = new Pane();
        map.maxHeightProperty().bind(Bindings.min(
                widthProperty().subtract(100).divide(aspectRatio),
                heightProperty().subtract(100)));
        map.maxWidthProperty().bind(Bindings.min(
                heightProperty().subtract(100).multiply(aspectRatio),
                widthProperty().subtract(100)));

        hexagon = new Hexagon[rows + columns][columns];

        for(int y = 0 ; y < rows; y ++) {
            for(int x = firstColumn(y); x < lastColumn(y); x ++) {
                hexagon[x + xOffset][y] = new Hexagon();
                map.getChildren().add(hexagon[x + xOffset][y]);
            }
        }

        map.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                width = map.getWidth();
                height = map.getHeight();
                radius =  2 * height / (3 * (rows - 1));
                length = Math.sqrt(3) / 2 * radius;

                drawHexagons();
            }
        });

        //map.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY ,Insets.EMPTY)));
        setCenter(map);
    }

    public int firstColumn(int row) {
        return -row / 2;
    }

    public int lastColumn(int row) {
        return columns - (row + 1) / 2;
    }

    public Point2D hexToPixel(int x, int y) {
        double px = (2 * length * x + length * y);
        double py = radius * (1.5 * y);
        return new Point2D(px, py);
    }

    public CubeCoordinates pixelToHex(double x, double y) {
        double px = (Math.sqrt(3) / 3 * x - 1.0 / 3 * y) / radius;
        double py = (2.0 / 3 * y) / radius;
        double pz = 0 - px - py;

        CubeCoordinates cubeCoordinates = new CubeCoordinates(px, py, pz);

        return CubeCoordinates.roundCube(cubeCoordinates);
    }

    private void drawHexagons() {
        for(int y = 0 ; y < rows; y ++) {
            for(int x = firstColumn(y); x < lastColumn(y); x ++) {
                Point2D center = hexToPixel(x, y);
                hexagon[x + xOffset][y].setCenter(center);
                hexagon[x + xOffset][y].setRadius(radius);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Hexagon[][] getHexagon() {
        return hexagon;
    }

    public Pane getMap() {
        return map;
    }

    public int getxOffset() {
        return xOffset;
    }
}
