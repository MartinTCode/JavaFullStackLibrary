package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditUsersLibrarianController {

    public static String initialSSN = null; // Used to pass the SSN of the user to be edited

    @FXML
    private BorderPane mainPane;

    // --- Form Fields ---
    @FXML
    private ComboBox<?> editBorrowerTypeComboBoxLibrarian;

    @FXML
    private TextField editUserSSNFieldLibrarian;

    @FXML
    private TextField editUserFirstNameFieldLibrarian;

    @FXML
    private TextField editUserLastNameFieldLibrarian;

    @FXML
    private TextField editUserAddressFieldLibrarian;

    @FXML
    private TextField editUserEmailFieldLibrarian;

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

    public void initialize() {
        // Initialize any necessary data or state here
        initializeUserFields();
    }

    // --- Form Button Handlers ---

    @FXML
    private void clickedCancelEditUsersButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedSaveEditUsersButtonLibrarian(MouseEvent event) {
        //Implement saving logic here
        System.out.println("Save Edit User button clicked.");
    }

    @FXML
    private void clickedDeleteUserButtonLibrarian(MouseEvent event) {
        //Implement delete logic here
        System.out.println("Delete User button clicked.");
    }

    /**
     * Populates the form fields with mock data for demonstration/testing purposes.
     * Change this method to fetch real data from the database or service layer later
     */
    public void initializeUserFields() {
        if (initialSSN != null) {
            editUserSSNFieldLibrarian.setText(initialSSN);
        // Mock data for demonstration purposes
        } else {
            editUserSSNFieldLibrarian.setText("123-45-6789");
        }
        // Mock data for demonstration purposes
        editUserFirstNameFieldLibrarian.setText("Anna");
        editUserLastNameFieldLibrarian.setText("Smith");
        editUserAddressFieldLibrarian.setText("123 Main St, Springfield");
        editUserEmailFieldLibrarian.setText("anna.smith@example.com");
        editUserPhoneFieldLibrarian.setText("(555) 123-4567");
    }
}
