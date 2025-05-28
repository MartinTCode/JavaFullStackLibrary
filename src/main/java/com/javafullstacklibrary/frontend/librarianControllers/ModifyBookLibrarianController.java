package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Controller for the Modify Book (not Course Literature) view for librarians.
 */
public class ModifyBookLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField bookTitleTextFieldLibrarian;

    @FXML
    private TextField bookIsbn13TextFieldLibrarian;

    @FXML
    private TextField bookIsbn10TextFieldLibrarian;

    @FXML
    private TextField bookPublisherTextFieldLibrarian;

    @FXML
    private ComboBox<String> bookLanguageComboBoxLibrarian;

    @FXML
    private ComboBox<String> bookFloorComboBoxLibrarian;

    @FXML
    private ComboBox<String> bookSectionComboBoxLibrarian;

    @FXML
    private ComboBox<String> bookShelfComboBoxLibrarian;

    @FXML
    private ComboBox<String> bookPositionComboBoxLibrarian;

    @FXML
    private ComboBox<String> bookAuthorComboBoxLibrarian1;

    @FXML
    private ComboBox<String> bookAuthorComboBoxLibrarian2;

    @FXML
    private ComboBox<String> bookAuthorComboBoxLibrarian3;

    @FXML
    private ComboBox<String> bookGenreComboBoxLibrarian1;

    @FXML
    private ComboBox<String> bookGenreComboBoxLibrarian2;

    @FXML
    private ComboBox<String> bookGenreComboBoxLibrarian3;

    @FXML
    private ComboBox<String> bookKeywordComboBoxLibrarian1;

    @FXML
    private ComboBox<String> bookKeywordComboBoxLibrarian2;

    @FXML
    private ComboBox<String> bookKeywordComboBoxLibrarian3;

    @FXML
    private Button cancelChangeBookButtonLibrarian;

    @FXML
    private Button saveChangesBookButtonLibrarian;

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
    private void clickedCancelChangeBookButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesBookButtonLibrarian(MouseEvent event) {
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
        bookLanguageComboBoxLibrarian.getItems().setAll("English", "French", "German", "Spanish");
        bookFloorComboBoxLibrarian.getItems().setAll("1", "2", "3");
        bookSectionComboBoxLibrarian.getItems().setAll("A", "B", "C");
        bookShelfComboBoxLibrarian.getItems().setAll("Shelf 1", "Shelf 2", "Shelf 3");
        bookPositionComboBoxLibrarian.getItems().setAll("1", "2", "3");
        bookAuthorComboBoxLibrarian1.getItems().setAll("Author A", "Author B", "Author C");
        bookAuthorComboBoxLibrarian2.getItems().setAll("Author D", "Author E", "Author F");
        bookAuthorComboBoxLibrarian3.getItems().setAll("Author G", "Author H", "Author I");
        bookGenreComboBoxLibrarian1.getItems().setAll("Fiction", "Non-Fiction", "Science", "Biography");
        bookGenreComboBoxLibrarian2.getItems().setAll("Fantasy", "Mystery", "Romance");
        bookGenreComboBoxLibrarian3.getItems().setAll("History", "Children", "Young Adult");
        bookKeywordComboBoxLibrarian1.getItems().setAll("Classic", "Bestseller", "Award-winning");
        bookKeywordComboBoxLibrarian2.getItems().setAll("New", "Recommended", "Popular");
        bookKeywordComboBoxLibrarian3.getItems().setAll("Reference", "Textbook", "Guide");
    }
}
