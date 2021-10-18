package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

public class MapController {
    private MapView mapView;
    private Pane map;
    private Hexagon[][] hexagons;
    private DijkstraAlgorithm dijkstra;
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

        dijkstra = new DijkstraAlgorithm(rows, columns, hexagons);
    }

    public void startDijkstra() {
        dijkstra.stop();
        clearMap();

        isClear = false;
        dijkstra.start();
    }

    public void clearMap() {
        if(isClear)
            return;
        isClear = true;
        dijkstra.stop();
        for(int y = 0; y < rows; y ++) {
            for(int x = mapView.firstColumn(y); x < mapView.lastColumn(y); x ++){
                if(hexagons[x + xOffset][y].getState() == Hexagon.REACHABLE ||
                        hexagons[x + xOffset][y].getState() == Hexagon.PATH)
                    hexagons[x + xOffset][y].setState(Hexagon.EMPTY);
            }
        }
    }

    public void resetMap() {
        dijkstra.stop();
        for(int y = 0; y < rows; y ++) {
            for(int x = mapView.firstColumn(y); x < mapView.lastColumn(y); x ++){
               hexagons[x + xOffset][y].setState(Hexagon.EMPTY);
            }
        }
        createSource();
        createDestination();
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
                clearMap();
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
                clearMap();
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
