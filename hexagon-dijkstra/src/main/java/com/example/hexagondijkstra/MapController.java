package com.example.hexagondijkstra;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.Path;

public class MapController {
    private MapView mapView;
    private Hexagon[][] hexagons;
    private int rows, columns;
    private boolean isClear = true, isReset = true;


    public MapController(MapView mapView) {

        this.mapView = mapView;
        rows = mapView.getRows();
        columns = mapView.getColumns();
        hexagons = mapView.getHexagon();

        createSource();
        createDestination();

        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                initGestureHandlers(hexagons[i][j]);
            }
        }
    }

    private void createSource() {
        int i = rows / 2;
        int j = columns / 4;
        hexagons[i][j].setSelected(Hexagon.SOURCE);
    }

    private void createDestination() {
        int i = rows / 2;
        int j = 3 * columns / 4;
        hexagons[i][j].setSelected(Hexagon.DESTINATION);
    }

    private void clearMap() {
        if(isClear == true)
            return;
        isClear = true;
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j ++) {
                if(hexagons[i][j].getState() == Hexagon.REACHABLE || hexagons[i][j].getState() == Hexagon.PATH)
                    hexagons[i][j].setSelected(Hexagon.EMPTY);
            }
        }
    }

    private void resetMap() {
        if(isReset == true)
            return;
        isReset = true;
        for(int i = 0; i < rows; i ++) {
            for(int j = 0; j < columns; j++) {
                hexagons[i][j].setSelected(Hexagon.EMPTY);
            }
        }

        createSource();
        createDestination();
    }

    private void initGestureHandlers(Hexagon hexagon) {

        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearMap();
                if(hexagon.getState() == Hexagon.EMPTY)
                    hexagon.setState(Hexagon.BLOCKED);
                else if(hexagon.getState() == Hexagon.BLOCKED)
                    hexagon.setState(Hexagon.EMPTY);
            }
        });



        hexagon.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dragboard db;
                ClipboardContent content = new ClipboardContent();
                System.out.println("START");

                db = hexagon.startDragAndDrop(TransferMode.ANY);
                content.putString(Integer.toString(hexagon.getState()));
                db.setContent(content);

                mouseEvent.consume();
            }
        });



        hexagon.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);

                if(hexagon.getState() == Hexagon.EMPTY) {
                    hexagon.setState(Hexagon.BLOCKED);
                }
                else if(hexagon.getState() == Hexagon.BLOCKED) {
                    hexagon.setState(Hexagon.EMPTY);
                }

                dragEvent.consume();
            }
        });





        hexagon.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                //if(!dragEvent.getDragboard().hasString()) return;

                System.out.println("STOP");
                dragEvent.acceptTransferModes(TransferMode.ANY);

                int startState = Integer.parseInt(dragEvent.getDragboard().getString());

                dragEvent.setDropCompleted(true);
                dragEvent.consume();
            }
        });



        hexagon.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
            }
        });
    }
}
