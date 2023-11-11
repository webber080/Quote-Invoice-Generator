package com.example.tcstest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml")); // Adjust the path to your FXML file if needed
        Scene scene = new Scene(root);

        primaryStage.setTitle("Quote/Invoice Generator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // This line makes the stage non-resizable
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
