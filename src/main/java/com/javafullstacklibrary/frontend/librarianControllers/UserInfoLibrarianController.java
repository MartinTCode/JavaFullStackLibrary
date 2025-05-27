package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class UserInfoLibrarianController {

    @FXML
    private BorderPane mainPane;

    // --- User Info TextFields from FXML ---
    @FXML
    private TextField usernameFieldLibrarian;
    @FXML
    private TextField firstNameFieldLibrarian;
    @FXML
    private TextField lastNameFieldLibrarian;
    @FXML
    private TextField addressFieldLibrarian;
    @FXML
    private TextField emailFieldLibrarian;
    @FXML
    private TextField phoneNumberFieldLibrarian;

    // --- Buttons from FXML ---
    @FXML
    private Button changeUserInfoButtonLibrarian;
    @FXML
    private Button saveUserInfoButtonLibrarian;

    @FXML
    public void initialize() {
        setUserData();

        // Set fields as not editable by default
        setFieldsEditable(false);

        // Set button states
        changeUserInfoButtonLibrarian.setDisable(false);
        saveUserInfoButtonLibrarian.setDisable(true);
    }

    /** 
     * Sets the user data in the text fields.
     * Currently mock data, switch to real data retrieval later.
     */
    private void setUserData() {
        usernameFieldLibrarian.setText("librarian123");
        firstNameFieldLibrarian.setText("Anna");
        lastNameFieldLibrarian.setText("Smith");
        addressFieldLibrarian.setText("123 Library St");
        emailFieldLibrarian.setText("anna.smith@library.com");
        phoneNumberFieldLibrarian.setText("+1234567890");
    }

    /**
     * Sets the editability of the user info fields.
     * @param editable true to make fields editable, false to make them read-only
     */
    private void setFieldsEditable(boolean editable) {
        // Username is usually not editable
        usernameFieldLibrarian.setEditable(false);
        firstNameFieldLibrarian.setEditable(editable);
        lastNameFieldLibrarian.setEditable(editable);
        addressFieldLibrarian.setEditable(editable);
        emailFieldLibrarian.setEditable(editable);
        phoneNumberFieldLibrarian.setEditable(editable);
    }

    /**
     * Sets the states of the buttons based on whether the user is editing or not.
     * @param editing true if the user is currently editing, false otherwise
     */
    private void setButtonStates(boolean editing) {
        changeUserInfoButtonLibrarian.setDisable(editing);
        saveUserInfoButtonLibrarian.setDisable(!editing);
    }

    public void clickedChangeUserInfoButtonLibrarian() {
        setFieldsEditable(true);
        setButtonStates(true);
    }

    public void clickedSaveUserInfoButtonLibrarian() {
        // Here you would typically save the data to a database or backend service
        // For now, we will just print the values to the console
        System.out.println("Saving user info:");
        System.out.println("Username: " + usernameFieldLibrarian.getText());
        System.out.println("First Name: " + firstNameFieldLibrarian.getText());
        System.out.println("Last Name: " + lastNameFieldLibrarian.getText());
        System.out.println("Address: " + addressFieldLibrarian.getText());
        System.out.println("Email: " + emailFieldLibrarian.getText());
        System.out.println("Phone Number: " + phoneNumberFieldLibrarian.getText());

        // Reset fields to read-only state
        setFieldsEditable(false);
        setButtonStates(false);
    }
    
    // --- Top Menu Methods ---
    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuLibrarian(MouseEvent event) {
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
    private void clickedUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    @FXML
    private void clickedChangePasswordButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SignOut");
    }

}
