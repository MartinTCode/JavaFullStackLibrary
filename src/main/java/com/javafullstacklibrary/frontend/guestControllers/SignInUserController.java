package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
            // Load Start_View_Guest.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/guestViews/Start_View_Guest.fxml"));
            Parent root = loader.load();


            // TODO: Reflect on why root.getScene().getWindow() does not work.
            // Get the current stage from mainPane
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSearchMenuGuest() {
        try {
            // Load Start_View_Guest.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/guestViews/Search_Menu_Guest.fxml"));
            Parent root = loader.load();

            // Get the current stage from mainPane
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
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
    private void clickedStaffButton() {
        System.out.println("Staff button clicked");
    }
}