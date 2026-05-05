package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoviesApp extends Application {

    @Override
    public void start(Stage stage) {
        MoviesView view = new MoviesView();
        Scene scene = new Scene(view, 350, 400);
        stage.setTitle("Movie Rental System - Movies");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}