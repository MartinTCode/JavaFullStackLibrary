package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class CreateUserLibrarianController {

    public static String initialUserType = null; // Static variable to hold the user type passed from ManageUsers

    @FXML
    private BorderPane mainPane;

    // --- Form Fields from FXML ---
    @FXML
    private ComboBox<String> createBorrowerTypeComboBoxLibrarian;

    @FXML
    private TextField createUserSSNFieldLibrarian;

    @FXML
    private TextField createUserFirstNameFieldLibrarian;

    @FXML
    private TextField createUserLastNameFieldLibrarian;

    @FXML
    private TextField createUserAddressFieldLibrarian;

    @FXML
    private TextField createUserEmailFieldLibrarian;

    @FXML
    private TextField editUserPhoneFieldLibrarian;

    // --- Top Menu Navigation Handlers ---

    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenyLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    @FXML
    private void clickedLibraryMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // --- Form Button Handlers ---

    @FXML
    private void clickedCancelNewUsersButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedSaveNewUsersButtonLibrarian(MouseEvent event) {
        // Implement saving logic here

        System.out.println("Save New User button clicked.");
    }

    @FXML
    private void initialize() {
        createBorrowerTypeComboBoxLibrarian.getItems().addAll(
            "Public",
            "Student",
            "Researcher",
            "University_Employee"
        );
        // Set the initial value if passed from ManageUsers
        if (initialUserType != null) {
            createBorrowerTypeComboBoxLibrarian.setValue(initialUserType);
            initialUserType = null; // Reset after use
        }
    }
}
