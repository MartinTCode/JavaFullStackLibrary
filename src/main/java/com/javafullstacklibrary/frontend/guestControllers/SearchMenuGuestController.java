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

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private void clickedHomeMenuGuest() {
        try {
            // Load Start_View_Guest.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/guestViews/Start_View_Guest.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/guestViews/Sign_In_User.fxml"));
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