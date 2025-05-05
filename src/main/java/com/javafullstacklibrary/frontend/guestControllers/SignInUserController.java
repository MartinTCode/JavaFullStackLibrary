package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class SignInUserController {

    @FXML
    private Pane mainPane;

    // access input fields
    @FXML
    private TextField ssnField;

    @FXML
    private TextField passwordField;

    @FXML
    private void clickedHomeMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"Home");
    }

    @FXML
    private void clickedSearchMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"Search");
    }

    @FXML
    private void clickedSignInButton() {
        // Print a message indicating the button was clicked
        System.out.println("Sign-in button clicked");

        // Print the values entered in the text fields
        String ssn = ssnField.getText();
        String password = passwordField.getText();
        System.out.println("SSN: " + ssn);
        System.out.println("Password: " + password);
    }

    @FXML
    private void clickedfForgotPasswordText() {
        // Functionality not implemented in current version. 
    }

    @FXML
    private void clickedStaffButton() {
        MenuNavigationHelper.menuClickGuest(mainPane,"SignInStaff");
    }
}