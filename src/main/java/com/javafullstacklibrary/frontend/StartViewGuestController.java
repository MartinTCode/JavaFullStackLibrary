package com.javafullstacklibrary.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartViewGuestController {
    @FXML
    private Pane mainPane;

    @FXML
    private void clickedSearchMenuGuest() {
        System.out.println("Search menu clicked");
    }

    @FXML
    private void clickedSignInMenuGuest() {
        try {
            // Load Sign_In_User.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/Sign_In_User.fxml"));
            Parent root = loader.load();
    
            // Get the current stage from any node in the current scene
            Stage stage = (Stage) mainPane.getScene().getWindow(); // Assuming mainPane is defined in the controller
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}