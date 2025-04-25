package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartViewGuestController {

    // Path to the FXML guest files
    // This path is used to load the FXML files for the GUI
    private static final String fxmlPathGuest = "/com/javafullstacklibrary/frontend/guestViews/";

    @FXML
    private Pane mainPane;

    @FXML
    private void clickedSearchMenuGuest() {
        try {
            // Load fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathGuest + "Search_Menu_Guest.fxml"));
            loader.setController(new SearchMenuGuestController());
            Parent root = loader.load();


            // Get the current stage from mainPane
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSignInMenuGuest() {
        try {
            // Load fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathGuest + "Sign_In_User.fxml"));
            loader.setController(new SignInUserController());
            Parent root = loader.load();
    
            // Get the current stage from any node in the current scene
            Stage stage = (Stage) mainPane.getScene().getWindow(); // Assuming mainPane is defined in the controller
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}