package com.example.tcstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml")); // Adjust the path to your FXML file if needed
        Scene scene = new Scene(root);

        primaryStage.setTitle("Quote/Invoice Generator Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // This line makes the stage non-resizable
        primaryStage.show();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        launch(args); // calls the "start()" method above which starts the FXML window
    }
}
