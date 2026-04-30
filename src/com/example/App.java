package com.example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    Text lblMsg = new Text("Welcome");
    Text lblName = new Text("Name");
    TextField txtName = new TextField();
    Button btnOk = new Button("OK");
    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage stage) {
        gridPane.setMinSize(400, 200);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(lblMsg, 1, 0);
        gridPane.add(lblName, 0, 1);
        gridPane.add(txtName, 1, 1);
        gridPane.add(btnOk, 0, 2);

        // Add gridpane to scene and set stage
        Scene scene = new Scene(gridPane);
        stage.setTitle("Welcome to OOP II");
        stage.setScene(scene);
        stage.show();

        // Event handling
        btnOk.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = txtName.getText();
                String msg = "Hi " + name + ", welcome to OOP II";
                lblMsg.setText(msg);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}