package com.example.noteapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class TestClass extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TestClass.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        DBUtil dbUtil = new DBUtil();
        DBUtil.showTables();
        DBUtil.createTable("test_table2");
        DBUtil.showTables();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
