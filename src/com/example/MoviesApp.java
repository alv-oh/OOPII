package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoviesApp extends Application {

    @Override
    public void start(Stage stage) {
        GenreStore store = GenreStore.getInstance();

        // Load saved movies from file when app starts
        store.loadFromFile();

        MoviesView view = new MoviesView();
        Scene scene = new Scene(view, 350, 400);
        stage.setTitle("Movie Rental System - Movies");
        stage.setScene(scene);
        stage.setResizable(false);

        // Save movies to file when the window is closed
        stage.setOnCloseRequest(e -> store.saveToFile());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
