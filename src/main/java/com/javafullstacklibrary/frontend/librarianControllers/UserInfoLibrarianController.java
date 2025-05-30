package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        usernameFieldLibrarian.setText("");
        firstNameFieldLibrarian.setText("");
        lastNameFieldLibrarian.setText("");
        addressFieldLibrarian.setText("");
        emailFieldLibrarian.setText("");
        phoneNumberFieldLibrarian.setText("");
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
        // Allow user to edit their information
        setFieldsEditable(true);
        setButtonStates(true);

        // Not implemented yet, give alert to user
        notImplementedAlert();
    }

    public void clickedSaveUserInfoButtonLibrarian() {
         // Reset fields to read-only state
        setFieldsEditable(false);
        setButtonStates(false);

        // Not implemented yet, give alert to user
        notImplementedAlert();
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

    private void notImplementedAlert(){
        showInfoAlert("Not Implemented", "This feature is not available in this version.");
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
