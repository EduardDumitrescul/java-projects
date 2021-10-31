package com.example.testing;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineTester extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(10);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("KF1");
            }
        }));
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100));
        keyFrame.wait(100);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("KF2");
            }
        }));

        timeline.playFromStart();
    }

    public static void main(String[] args) {
        launch();
    }
}
