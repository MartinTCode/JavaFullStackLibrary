package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

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
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "Search");
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

        //Implement sign in logic here

        // Navigate to the librarian start view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedfForgotPasswordText() {
        // Functionality not implemented in current version. 
    }

    @FXML
    private void clickedUserButton() {
        MenuNavigationHelper.menuClickGuest(mainPane, "SignInUser");
    }
}
