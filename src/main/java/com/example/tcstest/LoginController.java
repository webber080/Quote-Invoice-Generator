package com.example.tcstest;

import com.example.tcstest.errors.ErrorModal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class LoginController {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void handleFormLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Define the URL of your Spring Boot backend
        String url = "http://localhost:8080/users/formLogin";

        // (I can also use RestTemplate as another way of doing HTTP calls)
        // POST request to check if email exists, if so, check if password matches
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("email=" + email + "&password=" + password))
                .build();

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Send the HTTP GET request and process the response asynchronously
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body) // Same thing as ".thenApply(response -> response.body())"
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        int currentId = Integer.parseInt(response);
                        if(currentId != -1){
                            Main.currentID = currentId;
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setTitle("Dashboard");
                                stage.setScene(new Scene(root));
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace(); // Handle the exception appropriately
                            }
                        } else{
                            ErrorModal.showErrorModal("Incorrect email or password");
                        }
                    });
                }) // Can also do ".thenAccept(System.out::println)" if I just want to print
                .join(); // Waits for the asynchronous operation to complete
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
