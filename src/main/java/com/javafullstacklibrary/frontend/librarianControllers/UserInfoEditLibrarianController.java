package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class UserInfoEditLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    // Top menu navigation
    @FXML
    private void clickedHomeMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenyLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedOverdueMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    @FXML
    private void clickedLibraryMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedUsersMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedSignOutMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // Side menu actions
    @FXML
    private void clickedChangePasswordButtonLibrarian() {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonLibrarian() {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SignOut");
    }

    @FXML
    private void clickedSaveUserInfoButtonBorrowerLibrarian() {
        // Save logic here (update user info in backend or singleton)
        System.out.println("Saving user info for librarian...");
        // Example: print out new info
        System.out.println("First Name: " + firstNameField.getText());
        System.out.println("Last Name: " + lastNameField.getText());
        System.out.println("Address: " + addressField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Phone: " + phoneNumberField.getText());
        // Optionally, show confirmation or reload view
    }
}
