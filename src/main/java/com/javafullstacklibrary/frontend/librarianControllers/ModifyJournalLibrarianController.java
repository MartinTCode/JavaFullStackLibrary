package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ModifyJournalLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField journalTitleTextFieldLibrarian;

    @FXML
    private TextField journalIssnTextFieldLibrarian;

    @FXML
    private TextField journalPublisherTextFieldLibrarian;

    @FXML
    private ComboBox<String> journalLanguageComboBoxLibrarian;

    @FXML
    private ComboBox<String> journalFloorComboBoxLibrarian;

    @FXML
    private ComboBox<String> journalSectionComboBoxLibrarian;

    @FXML
    private ComboBox<String> journalShelfComboBoxLibrarian;

    @FXML
    private ComboBox<String> journalPositionComboBoxLibrarian;

    @FXML
    private ComboBox<String> journalKeywordComboBoxLibrarian1;

    @FXML
    private ComboBox<String> journalKeywordComboBoxLibrarian2;

    @FXML
    private ComboBox<String> journalKeywordComboBoxLibrarian3;

    @FXML
    private ComboBox<String> journalAuthorComboBoxLibrarian1;

    @FXML
    private ComboBox<String> journalAuthorComboBoxLibrarian2;

    @FXML
    private ComboBox<String> journalAuthorComboBoxLibrarian3;

    @FXML
    private Button cancelChangeJournalButtonLibrarian;

    @FXML
    private Button saveChangesJournalButtonLibrarian;

    // --- Top Menu Handlers ---

    /**
     * Handles click on the Home menu button.
     * Navigates to the Home view for librarians.
     */
    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    /**
     * Handles click on the Search menu button.
     * Navigates to the Search view for librarians.
     */
    @FXML
    private void clickedSearchMenyLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    /**
     * Handles click on the Overdue menu button.
     * Navigates to the Overdue view for librarians.
     */
    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    /**
     * Handles click on the Manage Users menu button.
     * Navigates to the Manage Users view for librarians.
     */
    @FXML
    private void clickedManageUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    /**
     * Handles click on the Account menu button.
     * Navigates to the Account view for librarians.
     */
    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    /**
     * Handles click on the Sign Out menu button.
     * Signs out the librarian.
     */
    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // --- Button Handlers ---

    /**
     * Handles click on the Cancel button.
     * Navigates back to the Manage Library view.
     */
    @FXML
    private void clickedCancelChangeJournalButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedsaveChangesJournalButtonLibrarian(MouseEvent event) {
        // Save logic here (validation, update DB, etc.)
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Initializes the controller after the root element has been completely processed.
     * Calls the method to populate combo boxes.
     */
    @FXML
    private void initialize() {
        populateComboBoxes();
    }

    /**
     * Populates all ComboBox fields with sample data.
     * This method is called from initialize().
     */
    private void populateComboBoxes() {
        journalLanguageComboBoxLibrarian.getItems().setAll("English", "French", "German", "Spanish");
        journalFloorComboBoxLibrarian.getItems().setAll("1", "2", "3");
        journalSectionComboBoxLibrarian.getItems().setAll("A", "B", "C");
        journalShelfComboBoxLibrarian.getItems().setAll("Shelf 1", "Shelf 2", "Shelf 3");
        journalPositionComboBoxLibrarian.getItems().setAll("1", "2", "3");
        journalKeywordComboBoxLibrarian1.getItems().setAll("Science", "Medicine", "Engineering");
        journalKeywordComboBoxLibrarian2.getItems().setAll("Biology", "Physics", "Chemistry");
        journalKeywordComboBoxLibrarian3.getItems().setAll("AI", "Robotics", "Ecology");
        journalAuthorComboBoxLibrarian1.getItems().setAll("Author A", "Author B", "Author C");
        journalAuthorComboBoxLibrarian2.getItems().setAll("Author D", "Author E", "Author F");
        journalAuthorComboBoxLibrarian3.getItems().setAll("Author G", "Author H", "Author I");
    }
}
