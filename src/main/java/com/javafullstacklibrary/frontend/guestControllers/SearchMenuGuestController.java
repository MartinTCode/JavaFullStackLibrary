package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchMenuGuestController {

    // Path to the FXML guest files
    // This path is used to load the FXML files for the GUI
    private static final String fxmlPathGuest = "/com/javafullstacklibrary/frontend/guestViews/";

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private void clickedHomeMenuGuest() {
        try {
            // Load fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathGuest + "Start_View_Guest.fxml"));
            loader.setController(new StartViewGuestController());
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
            // Load Sign_In_User.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathGuest + "Sign_In_User.fxml"));
            loader.setController(new SignInUserController());
            Parent root = loader.load();

            // Get the current stage from mainPane
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedFilterButton() {
        // Functionality not implemented in current version. 
    }

    @FXML
    private void clickedSearchButton() {
        // Print the search query
        String query = searchField.getText();
        System.out.println("Search query: " + query);
    }
    
}