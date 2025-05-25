package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class ManageUsersLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField modifyUserSSNField;

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
    private void clickedAccountMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // Continue to add new user
    @FXML
    private void clickedContinueNewUserButton() {
        String userType = userTypeComboBox.getValue();
        System.out.println("Continue to add new user of type: " + userType);
        // Navigate to add new user form, pass userType if needed
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "CreateUser");
    }

    // Continue to modify existing user
    @FXML
    private void clickedContinueModifyUserButton() {
        String ssn = modifyUserSSNField.getText();
        System.out.println("Continue to modify user with SSN: " + ssn);
        // Navigate to modify user form, pass ssn if needed
        if (ssn.isEmpty()) {
            System.out.println("SSN field is empty. Please enter a valid SSN.");
            // Optionally, show an error message to the user
        } else {
            System.out.println("Valid SSN entered. Proceeding with modification."); 
            // Proceed with the modification logic
            // For example, load user data based on SSN and navigate to modify form
            MenuNavigationHelper.buttonClickLibrarian(mainPane, "EditUser");
        }
    }

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll(
            "Public",
            "Student",
            "Researcher",
            "University_Employee"
        );
    }
}
