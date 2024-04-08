package com.example.tcstest.helpers;

import com.example.tcstest.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class switchScene {
    public static void switchSceneHelper(ActionEvent event, Class c, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(c.getResource(fxmlPath));
            Scene scene = new Scene(root);

            Stage dashboardStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            dashboardStage.setTitle(title);
            dashboardStage.setScene(scene);
            dashboardStage.setResizable(false);
            dashboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
