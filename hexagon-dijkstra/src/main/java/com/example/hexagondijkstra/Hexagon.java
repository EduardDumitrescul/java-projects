package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.effect.Blend;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.awt.image.PixelInterleavedSampleModel;

public class Hexagon extends Region {
    private Point2D top, topLeft, bottomLeft, bottom, bottomRight, topRight;
    private Point2D center;
    private double radius, height;

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

    public Hexagon() {
        polygon = new Polygon();
        polygon.setFill(color[EMPTY]);
        polygon.setStroke(Color.LIGHTBLUE);
        polygon.setStrokeWidth(2);

        //setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderStroke.THIN)));

        getChildren().add(polygon);

        initPoints();
    }

    public void setCenter(Point2D center) {
        this.center = center;
        drawHex();
    }

    public void setRadius(double radius) {
        this.radius = radius;
        height = radius * Math.sqrt(3) / 2;
        drawHex();
    }

    private void drawHex() {
        polygon.getPoints().clear();

        top         = new Point2D(   center.getX(),          center.getY() - radius);
        topLeft     = new Point2D(center.getX() - height, center.getY() - radius / 2);
        bottomLeft  = new Point2D(center.getX() - height, center.getY() + radius / 2);
        bottom      = new Point2D(   center.getX(),          center.getY() + radius);
        bottomRight = new Point2D(center.getX() + height, center.getY() + radius / 2);
        topRight    = new Point2D(center.getX() + height, center.getY() - radius / 2);
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

    public Point2D getCenter() {
        return center;
    }
}
