package com.example.hexagondijkstra;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.*;

public class MapView extends BorderPane {
    private int rows, columns;
    private double aspectRatio, radius, length;

    private Hexagon[][] hexagon;
    private Group hexagonGroup;

    private Pane map;

    public MapView(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        aspectRatio = (3 * rows + 2) / (2 * Math.sqrt(3) * columns);
        System.out.println(aspectRatio);

        map = new Pane();
        map.maxHeightProperty().bind(Bindings.min(
                widthProperty().subtract(100).multiply(aspectRatio),
                heightProperty().subtract(100)));
        map.maxWidthProperty().bind(Bindings.min(
                heightProperty().subtract(100).divide(aspectRatio),
                widthProperty().subtract(100)));



        //map.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));



        hexagon = new Hexagon[rows][columns];
        hexagonGroup = new Group();

        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                hexagon[i][j] = new Hexagon(i, j);
                map.getChildren().add(hexagon[i][j]);
            }
        }

        map.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                radius =  2 * map.getHeight() / (3 * rows + 2);
                length = map.getWidth() / columns;

                addTopPoints();
                addTopLeftPoints();
                addBottomLeftPoints();
                addBottomPoints();
                addBottomRightPoints();
                addTopRightPoints();

                System.out.println(hexagon[0][0].getPoints());
            }
        });




        setCenter(map);

        //System.out.println(map.getChildren());

    }

    private void addTopPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = (2.0 * j + 1) / 2 * length;
                else x = (j + 1) * length;
                y = (3.0 * i) / 2 * radius;

                hexagon[i][j].setTop(new Point2D(x, y));
            }
        }
    }

    private void addTopLeftPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = j * length;
                else x = (2.0 * j + 1) / 2 * length;
                y = (3.0 * i + 1) / 2 * radius;

                hexagon[i][j].setTopLeft(new Point2D(x, y));
            }
        }
    }
    private void addBottomLeftPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = j * length;
                else x = (2.0 * j + 1) / 2 * length;
                y = (3.0 * i + 3) / 2 * radius;

                hexagon[i][j].setBottomLeft(new Point2D(x, y));
            }
        }
    }
    private void addBottomPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = (2.0 * j + 1) / 2 * length;
                else x = (j + 1) * length;
                y = (3.0 * i + 4) / 2 * radius;

                hexagon[i][j].setBottom(new Point2D(x, y));
            }
        }
    }
    private void addBottomRightPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = (j + 1) * length;
                else x = (2.0 * j + 3) / 2 * length;
                y = (3.0 * i + 3) / 2 * radius;

                hexagon[i][j].setBottomRight(new Point2D(x, y));
            }
        }
    }


    private void addTopRightPoints() {
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(i % 2 == 1 && j == columns - 1)
                    break;

                double x, y;
                if(i % 2 == 0) x = (j + 1) * length;
                else x = (2.0 * j + 3) / 2 * length;
                y = (3.0 * i + 1) / 2 * radius;

                hexagon[i][j].setTopRight(new Point2D(x, y));
            }
        }
    }
}
