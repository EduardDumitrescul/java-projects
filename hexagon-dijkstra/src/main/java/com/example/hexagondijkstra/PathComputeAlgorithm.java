package com.example.hexagondijkstra;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class PathComputeAlgorithm {
    private int rows, columns, xOffset;
    private Hexagon[][] hexagons;
    private int[][] dist;
    private Timeline timeline;

    private int dx, dy;


    public PathComputeAlgorithm(int rows, int columns, Hexagon[][] hexagons, int[][] dist) {
        this.rows = rows;
        this.columns = columns;
        this.hexagons = hexagons;
        this.dist = dist;
        xOffset = (columns - 1) / 2;

        findDestination();
        initTimeline();
    }

    public void start() {
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
    }

    private void findDestination() {
        for(int y = 0; y < rows; y ++) {
            for(int x = firstColumn(y); x < lastColumn(y); x ++) {
                if(hexagons[x + xOffset][y].getState() == Hexagon.DESTINATION) {
                    dx = x;
                    dy = y;
                    return;
                }
            }
        }
    }

    private void initTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(hexagons[dx + xOffset][dy].getState() == Hexagon.SOURCE) {
                    timeline.stop();
                    return;
                }
                if(hexagons[dx + xOffset][dy].getState() == Hexagon.REACHABLE) {
                    hexagons[dx + xOffset][dy].setState(Hexagon.PATH);
                }
                CubeCoordinates current = new CubeCoordinates(dx, dy);
                for(int k = 0; k < 6; k ++) {
                    CubeCoordinates next = CubeCoordinates.nextCoordinate(current, k);
                    if(hexagonExists((int)next.getX(), (int)next.getY())) {
                        if(dist[(int)next.getX() + xOffset][(int)next.getY()] == dist[dx + xOffset][dy] - 1) {
                            System.out.println("OK");
                            dx = (int)next.getX();
                            dy = (int)next.getY();
                            return;
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }


    public int firstColumn(int row) {
        return -row / 2;
    }

    public int lastColumn(int row) {
        return columns - (row + 1) / 2;
    }

    private boolean hexagonExists(int i, int j) {
        if(!(0 <= j && j < rows))
            return false;
        if(!(firstColumn(j) <= i && i < lastColumn(j)))
            return false;
        return true;
    }
}
