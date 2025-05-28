package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Controller for the Modify Course Literature view for librarians.
 */
public class ModifyCourseLitLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField CourseLitTitleTextFieldLibrarian;

    @FXML
    private TextField CourseLitIsbn13TextFieldLibrarian;

    @FXML
    private TextField CourseLitIsbn10TextFieldLibrarian;

    @FXML
    private TextField CourseLitPublisherTextFieldLibrarian;

    @FXML
    private ComboBox<String> CourseLitLanguageComboBoxLibrarian;

    @FXML
    private ComboBox<String> CourseLitFloorComboBoxLibrarian;

    @FXML
    private ComboBox<String> CourseLitSectionComboBoxLibrarian;

    @FXML
    private ComboBox<String> CourseLitShelfComboBoxLibrarian;

    @FXML
    private ComboBox<String> CourseLitPositionComboBoxLibrarian;

    @FXML
    private ComboBox<String> CourseLitAuthorComboBoxLibrarian1;

    @FXML
    private ComboBox<String> CourseLitAuthorComboBoxLibrarian2;

    @FXML
    private ComboBox<String> CourseLitAuthorComboBoxLibrarian3;

    @FXML
    private ComboBox<String> CourseLitGenreComboBoxLibrarian1;

    @FXML
    private ComboBox<String> CourseLitGenreComboBoxLibrarian2;

    @FXML
    private ComboBox<String> CourseLitGenreComboBoxLibrarian3;

    @FXML
    private ComboBox<String> CourseLitKeywordComboBoxLibrarian1;

    @FXML
    private ComboBox<String> CourseLitKeywordComboBoxLibrarian2;

    @FXML
    private ComboBox<String> CourseLitKeywordComboBoxLibrarian3;

    @FXML
    private Button cancelChangeCourseLitButtonLibrarian;

    @FXML
    private Button saveChangesCourseLitButtonLibrarian;

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
    private void clickedCancelChangeCourseLitButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesCourseLitButtonLibrarian(MouseEvent event) {
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
        CourseLitLanguageComboBoxLibrarian.getItems().setAll("English", "French", "German", "Spanish");
        CourseLitFloorComboBoxLibrarian.getItems().setAll("1", "2", "3");
        CourseLitSectionComboBoxLibrarian.getItems().setAll("A", "B", "C");
        CourseLitShelfComboBoxLibrarian.getItems().setAll("Shelf 1", "Shelf 2", "Shelf 3");
        CourseLitPositionComboBoxLibrarian.getItems().setAll("1", "2", "3");
        CourseLitAuthorComboBoxLibrarian1.getItems().setAll("Author A", "Author B", "Author C");
        CourseLitAuthorComboBoxLibrarian2.getItems().setAll("Author D", "Author E", "Author F");
        CourseLitAuthorComboBoxLibrarian3.getItems().setAll("Author G", "Author H", "Author I");
        CourseLitGenreComboBoxLibrarian1.getItems().setAll("Science", "Mathematics", "Literature");
        CourseLitGenreComboBoxLibrarian2.getItems().setAll("Engineering", "Medicine", "Law");
        CourseLitGenreComboBoxLibrarian3.getItems().setAll("History", "Philosophy", "Art");
        CourseLitKeywordComboBoxLibrarian1.getItems().setAll("Required", "Optional", "Reference");
        CourseLitKeywordComboBoxLibrarian2.getItems().setAll("Exam", "Project", "Reading");
        CourseLitKeywordComboBoxLibrarian3.getItems().setAll("Core", "Supplementary", "Workshop");
    }
}
