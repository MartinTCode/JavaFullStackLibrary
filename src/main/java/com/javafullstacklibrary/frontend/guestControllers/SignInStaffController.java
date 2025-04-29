package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.io.IOException;

import com.javafullstacklibrary.utils.ViewLoader;

public class SignInStaffController {

    @FXML
    private Pane mainPane;

    // access input fields
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

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
    private void clickedSearchMenuGuest() {
        try {
            // Load fxml to stage
            SearchMenuGuestController controller = new SearchMenuGuestController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Search_Menu_Guest.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSignInButtonAdmin() {
        // Print a message indicating the button was clicked
        System.out.println("Admin Sign-in button clicked");

        // Print the values entered in the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    @FXML
    private void clickedSignInButtonLibrarian() {
        // Print a message indicating the button was clicked
        System.out.println("Librarian Sign-in button clicked");

        // Print the values entered in the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("SSN: " + username);
        System.out.println("Password: " + password);
    }

    @FXML
    private void clickedfForgotPasswordText() {
        // Functionality not implemented in current version. 
    }

    @FXML
    private void clickedUserButton() {
        try {
            System.out.println("User button clicked");
            // Load fxml to stage
            SignInUserController controller = new SignInUserController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Sign_In_User.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
