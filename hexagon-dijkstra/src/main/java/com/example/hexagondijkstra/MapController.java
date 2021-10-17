package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;

import javax.sound.sampled.Clip;

public class MapController {
    private MapView mapView;
    private Pane map;
    private Hexagon[][] hexagons;
    private int rows, columns, xOffset;
    private boolean isClear = true, isReset = true;


    public MapController(MapView mapView) {
        this.mapView = mapView;
        map = mapView.getMap();
        rows = mapView.getRows();
        columns = mapView.getColumns();
        hexagons = mapView.getHexagon();
        xOffset = mapView.getxOffset();

        createSource();
        createDestination();

        initGestureHandlers();
    }

    private void createSource() {
        int i = 0;
        int j = rows / 2;
        hexagons[i + xOffset][j].setState(Hexagon.SOURCE);
    }

    private void createDestination() {
        int i = columns - rows / 2 - 1;
        int j = rows / 2;
        hexagons[i + xOffset][j].setState(Hexagon.DESTINATION);
    }

    private boolean hexagonExists(int i, int j) {
        if(!(0 <= j && j < rows))
            return false;
        if(!(mapView.firstColumn(j) <= i && i < mapView.lastColumn(j)))
            return false;
        return true;
    }

    private void initGestureHandlers() {
        final int[] sourceState = {-1};
        final Hexagon[] hexDrag = new Hexagon[1];
        hexDrag[0] = null;

        map.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("CLICK");
                if(sourceState[0] != -1)
                    return;
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                CubeCoordinates cubeCoordinates = mapView.pixelToHex(x, y);
                int i = (int)cubeCoordinates.getX();
                int j = (int)cubeCoordinates.getY();
                System.out.println(i + " "  + j);

                if(hexagonExists(i, j)) {
                    i += xOffset;
                    if(hexagons[i][j].getState() == Hexagon.BLOCKED)
                        hexagons[i][j].setState(Hexagon.EMPTY);
                    else if(hexagons[i][j].getState() == Hexagon.EMPTY)
                        hexagons[i][j].setState(Hexagon.BLOCKED);
                }

                mouseEvent.consume();
            }
        });

        map.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                CubeCoordinates cubeCoordinates = mapView.pixelToHex(x, y);
                int i = (int)cubeCoordinates.getX();
                int j = (int)cubeCoordinates.getY();

                if(!hexagonExists(i, j))
                    return;

                i += xOffset;
                Hexagon hex = hexagons[i][j];

                if(sourceState[0] == -1)
                    sourceState[0] = hexagons[i][j].getState();

                if(sourceState[0] == Hexagon.SOURCE) {
                    if(hexDrag[0] == null)
                        hexDrag[0] = hex;
                    if(hexDrag[0] != hex && (hex.getState() == Hexagon.EMPTY || hex.getState() == Hexagon.BLOCKED)) {
                        hexDrag[0].setState(Hexagon.EMPTY);
                        hex.setState(Hexagon.SOURCE);
                        hexDrag[0] = hex;
                    }
                }
                else if(sourceState[0] == Hexagon.DESTINATION) {
                    if(hexDrag[0] == null)
                        hexDrag[0] = hex;
                    if(hexDrag[0] != hex && (hex.getState() == Hexagon.EMPTY || hex.getState() == Hexagon.BLOCKED)) {
                        hexDrag[0].setState(Hexagon.EMPTY);
                        hex.setState(Hexagon.DESTINATION);
                        hexDrag[0] = hex;
                    }
                }
                else if(sourceState[0] == Hexagon.EMPTY) {
                    if(hex.getState() != Hexagon.SOURCE && hex.getState() != Hexagon.DESTINATION)
                        hex.setState(Hexagon.BLOCKED);
                }
                else if(sourceState[0] == Hexagon.BLOCKED) {
                    if(hex.getState() != Hexagon.SOURCE && hex.getState() != Hexagon.DESTINATION)
                        hex.setState(Hexagon.EMPTY);
                }
            }
        });

        map.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                CubeCoordinates cubeCoordinates = mapView.pixelToHex(x, y);
                int i = (int)cubeCoordinates.getX();
                int j = (int)cubeCoordinates.getY();

                if(!hexagonExists(i, j)) {
                    sourceState[0] = -1;
                    hexDrag[0] = null;
                    mouseEvent.consume();
                    return;
                }

                i += xOffset;
                Hexagon hex = hexagons[i][j];

                if(sourceState[0] == Hexagon.EMPTY && hex.getState() == Hexagon.BLOCKED)
                    hex.setState(Hexagon.EMPTY);
                else if(sourceState[0] == Hexagon.BLOCKED && hex.getState() == Hexagon.EMPTY)
                    hex.setState(Hexagon.BLOCKED);

                sourceState[0] = -1;
                hexDrag[0] = null;
                mouseEvent.consume();
            }
        });
    }
}
