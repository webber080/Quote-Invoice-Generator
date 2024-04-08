package com.example.tcstest.helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class invoiceAddedModal {
    public static void showSuccessModal(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }
}
