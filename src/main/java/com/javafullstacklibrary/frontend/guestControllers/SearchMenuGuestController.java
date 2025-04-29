package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.javafullstacklibrary.utils.ViewLoader;

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
            // Load fxml to stage
            StartViewGuestController controller = new StartViewGuestController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Start_View_Guest.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSignInMenuGuest() {
        try {
            // Load fxml to stage
            SignInUserController controller = new SignInUserController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Sign_In_User.fxml", controller);
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