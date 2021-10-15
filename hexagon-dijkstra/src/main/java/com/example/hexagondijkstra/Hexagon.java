package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.effect.Blend;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon extends Region {
    private Point2D top, topLeft, bottomLeft, bottom, bottomRight, topRight;
    private Polygon polygon;

    public final static int EMPTY = 0, BLOCKED = 1, SOURCE = 2, DESTINATION = 3, PATH = 4, REACHABLE = 5;
    public final static int SELECTED = 100, DESELECTED = 101;

    private int state = EMPTY, selected = DESELECTED;

    private final static Color[] color = new Color[]{
            Color.web("7FBA93"), // EMPTY
            Color.web("315c3f"), // BLOCKED
            Color.web("ba7fa6"), // SOURCE
            Color.web("ba7fa6"), // DESTINATION
            Color.web("ba7fa6"), // PATH
            Color.web("e0a6cd")  // REACHABLE
    };

    public Hexagon(int x, int y) {
        polygon = new Polygon();
        //setStroke(Color.LIGHTBLUE);
        //setStrokeWidth(1);
        polygon.setFill(color[EMPTY]);

        setPickOnBounds(false);

        getChildren().add(polygon);

        initPoints();
        addAllPoints();
    }

    private void addAllPoints() {
        polygon.getPoints().addAll(
                top.getX(), top.getY(),
                topLeft.getX(), topLeft.getY(),
                bottomLeft.getX(), bottomLeft.getY(),
                bottom.getX(), bottom.getY(),
                bottomRight.getX(), bottomRight.getY(),
                topRight.getX(), topRight.getY());
    }

    private void initPoints() {
        top = new Point2D(0, 0);
        topLeft = new Point2D(0, 0);
        bottomLeft = new Point2D(0, 0);
        bottom = new Point2D(0, 0);
        bottomRight = new Point2D(0, 0);
        topRight = new Point2D(0, 0);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        polygon.setFill(color[state]);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setBottom(Point2D bottom) {
        polygon.getPoints().clear();
        this.bottom = bottom;
        addAllPoints();
    }

    public void setBottomLeft(Point2D bottomLeft) {
        polygon.getPoints().clear();
        this.bottomLeft = bottomLeft;
        addAllPoints();
    }

    public void setBottomRight(Point2D bottomRight) {
        polygon.getPoints().clear();
        this.bottomRight = bottomRight;
        addAllPoints();
    }

    public void setTop(Point2D top) {
        polygon.getPoints().clear();
        this.top = top;
        addAllPoints();
    }

    public void setTopLeft(Point2D topLeft) {
        polygon.getPoints().clear();
        this.topLeft = topLeft;
        addAllPoints();
    }

    public void setTopRight(Point2D topRight) {
        polygon.getPoints().clear();
        this.topRight = topRight;
        addAllPoints();
    }
}
