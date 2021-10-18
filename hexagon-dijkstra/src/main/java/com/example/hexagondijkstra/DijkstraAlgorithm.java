package com.example.hexagondijkstra;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class DijkstraAlgorithm {
    private ArrayDeque<CubeCoordinates> deque;
    private int rows, columns, xOffset;
    private Hexagon[][] hexagons;
    private int[][] dist;

    private Timeline timeline;

    public DijkstraAlgorithm(int rows, int columns, Hexagon[][] hexagons) {
        this.rows = rows;
        this.columns = columns;
        this.hexagons = hexagons;
        xOffset = (columns - 1) / 2;

        deque = new ArrayDeque<CubeCoordinates>();
        dist = new int[rows + columns][columns];

        findSource();
        initTimeline();
    }

    public void start() {
        timeline.playFromStart();
    }

    private void initTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                while(!deque.isEmpty()) {
                    CubeCoordinates current = deque.getFirst();
                    deque.removeFirst();

                    for(int k = 0; k < 6; k++) {
                        CubeCoordinates next = CubeCoordinates.nextCoordinate(current, k);
                        int x = (int)next.getX();
                        int y = (int)next.getY();
                        if(!hexagonExists(x, y))
                            continue;
                        x += xOffset;
                        if(dist[x][y] == 0 && hexagons[x][y].getState() != Hexagon.SOURCE) {
                            deque.addLast(next);
                            dist[x][y] = dist[(int)current.getX() + xOffset][(int)current.getY()] + 1;

                            if(hexagons[x][y].getState() == Hexagon.DESTINATION) {
                                timeline.stop();
                            }

                            hexagons[x][y].setState(Hexagon.REACHABLE);

                            return;
                        }
                    }
                }
            }
        }));
    }

    private void findSource() {
        for(int y = 0; y < rows; y ++) {
            for(int x = firstColumn(y); x < lastColumn(y); x ++) {
                if(hexagons[x + xOffset][y].getState() == Hexagon.SOURCE) {
                    deque.addFirst(new CubeCoordinates(x, y, -x-y));
                }
            }
        }
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
