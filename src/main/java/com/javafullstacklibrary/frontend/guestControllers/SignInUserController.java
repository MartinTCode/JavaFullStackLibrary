package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.javafullstacklibrary.utils.ViewLoader;

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
        try {
            // Load fxml to stage
            SignInStaffController controller = new SignInStaffController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Sign_In_Staff.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}