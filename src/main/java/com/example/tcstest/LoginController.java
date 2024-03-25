package com.example.tcstest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public void handleFormLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Quote/Invoice Generator");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    public void handleGoogleLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/googleLogin.fxml"));
            Parent root = loader.load();

            Stage webViewStage = new Stage();
            webViewStage.setTitle("Google Login");
            webViewStage.setScene(new Scene(root));
            webViewStage.setResizable(false);

            Stage originalStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            WebViewController.webViewStage = webViewStage;
            WebViewController.originalStage = originalStage;

            webViewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
