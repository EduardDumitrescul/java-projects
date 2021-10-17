package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.effect.Blend;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.awt.image.PixelInterleavedSampleModel;
import java.net.URL;

public class Hexagon extends Pane {
    private Point2D top, topLeft, bottomLeft, bottom, bottomRight, topRight;
    private Point2D center;
    private double radius, height;

    private Polygon polygon;
    private ImageView bullet, source, target;

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
        initImages();
    }

    private void initImages() {
        URL sourceURL = getClass().getResource("/img/source.png");
        source = new ImageView();
        source.setImage(new Image(String.valueOf(sourceURL)));

        URL targetURL = getClass().getResource("/img/target.png");
        target = new ImageView();
        target.setImage(new Image(String.valueOf(targetURL)));

        URL bulletURL = getClass().getResource("/img/bullet.png");
        bullet = new ImageView();
        bullet.setImage(new Image(String.valueOf(bulletURL)));
    }

    public void setCenter(Point2D center) {
        this.center = center;
        drawHex();
        recomputeImages();
    }

    public void setRadius(double radius) {
        this.radius = radius;
        height = radius * Math.sqrt(3) / 2;
        drawHex();
        recomputeImages();
    }

    private void recomputeImages() {
        source.setX(center.getX() - radius / 2);
        source.setY(center.getY() - radius / 2);
        target.setX(center.getX() - radius / 2);
        target.setY(center.getY() - radius / 2);
        bullet.setX(center.getX() - radius / 2);
        bullet.setY(center.getY() - radius / 2);


        source.setFitHeight(radius);
        source.setFitWidth(radius);
        target.setFitHeight(radius);
        target.setFitWidth(radius);
        bullet.setFitHeight(radius);
        bullet.setFitWidth(radius);


        source.setSmooth(true);
        target.setSmooth(true);
        bullet.setSmooth(true);

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
        switch(this.state) {
            case SOURCE: getChildren().remove(source); break;
            case DESTINATION: getChildren().remove(target); break;
        }
        this.state = state;
        polygon.setFill(color[state]);

        switch (state) {
            case SOURCE: getChildren().add(source); break;
            case DESTINATION: getChildren().add(target); break;
        }
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
