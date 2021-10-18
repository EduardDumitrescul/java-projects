package com.example.hexagondijkstra;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayDeque;

public class DijkstraAlgorithm {
    private final ArrayDeque<CubeCoordinates> deque;
    private final int rows;
    private final int columns;
    private final int xOffset;
    private final Hexagon[][] hexagons;
    private final int[][] dist;

    private Timeline timeline;

    private final PathComputeAlgorithm pathComputeAlgorithm;

    public DijkstraAlgorithm(int rows, int columns, Hexagon[][] hexagons) {
        this.rows = rows;
        this.columns = columns;
        this.hexagons = hexagons;
        xOffset = (columns - 1) / 2;

        deque = new ArrayDeque<>();
        dist = new int[rows + columns][columns];
        pathComputeAlgorithm = new PathComputeAlgorithm(rows, columns, hexagons, dist);


        findSource();
        initTimeline();
    }

    public void start() {
        timeline.playFromStart();
    }

    private void initTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
                CubeCoordinates current = deque.getFirst();
                deque.removeFirst();

                if (hexagons[(int) current.getX() + xOffset][(int) current.getY()].getState() == Hexagon.EMPTY) {
                    hexagons[(int) current.getX() + xOffset][(int) current.getY()].setState(Hexagon.REACHABLE);
                } else if (hexagons[(int) current.getX() + xOffset][(int) current.getY()].getState() == Hexagon.DESTINATION) {
                    timeline.stop();
                    System.out.println("FINISH");
                    pathComputeAlgorithm.start();
                    return;
                }

                for (int k = 0; k < 6; k++) {
                    CubeCoordinates next = CubeCoordinates.nextCoordinate(current, k);
                    int x = (int) next.getX();
                    int y = (int) next.getY();
                    if (!hexagonExists(x, y))
                        continue;
                    x += xOffset;
                    if (dist[x][y] == 0 && (hexagons[x][y].getState() == Hexagon.EMPTY || hexagons[x][y].getState() == Hexagon.DESTINATION)) {
                        deque.addLast(next);
                        dist[x][y] = dist[(int) current.getX() + xOffset][(int) current.getY()] + 1;
                    }
                }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void stop() {
        timeline.stop();
        pathComputeAlgorithm.stop();
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
        return firstColumn(j) <= i && i < lastColumn(j);
    }
}
