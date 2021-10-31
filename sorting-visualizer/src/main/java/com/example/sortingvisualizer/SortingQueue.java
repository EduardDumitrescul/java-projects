package com.example.sortingvisualizer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayDeque;

public class SortingQueue {
    public static final int CHECK = 0, SWAP = 1;

    private ArrayDeque<Integer> index1, index2;
    private ArrayDeque<Integer> type;
    private Timeline timeline = new Timeline();

    private RectangleBar[] rectangles;
    private boolean running = false;


    public SortingQueue(RectangleBar[] rectangles) {
        this.rectangles = rectangles;
        index1 = new ArrayDeque<>();
        index2 = new ArrayDeque<>();
        type = new ArrayDeque<>();
    }

    public void addEntry(int pos1, int pos2, int type) {
        index1.addLast(pos1);
        index2.addLast(pos2);
        this.type.addLast(type);
    }

    public void startAnimation() {
        if(running) return;
        running = true;
        index1.addFirst(0);
        index2.addFirst(0);
        type.addFirst(CHECK);

        timeline.setCycleCount(index1.size() - 1);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rectangles[index1.getFirst()].setBackground(RectangleBar.normalBackground);
                rectangles[index2.getFirst()].setBackground(RectangleBar.normalBackground);
                index1.removeFirst();
                index2.removeFirst();
                type.removeFirst();

               int pos1 = index1.getFirst();
               int pos2 = index2.getFirst();
               int t = type.getFirst();

               rectangles[pos1].setBackground(RectangleBar.focusedBackground);
               rectangles[pos2].setBackground(RectangleBar.focusedBackground);

               if(t == SWAP) {
                   RectangleBar.swap(rectangles[pos1], rectangles[pos2]);
               }


            }
        }));
        timeline.playFromStart();
        timeline.setOnFinished(actionEvent -> running = false);
    }

    public void clear() {
        index1.clear();
        index2.clear();
        type.clear();
    }

    public void stopAnimation() {
        timeline.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
