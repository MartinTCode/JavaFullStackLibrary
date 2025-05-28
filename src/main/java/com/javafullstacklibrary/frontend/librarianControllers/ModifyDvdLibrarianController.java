package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Controller for the Modify DVD view for librarians.
 */
public class ModifyDvdLibrarianController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField dvdTitleTextFieldLibrarian;

    @FXML
    private TextField dvdImdbcTextFieldLibrarian;

    @FXML
    private TextField dvdPublisherTextFieldLibrarian;

    @FXML
    private TextField dvdCountryTextFieldLibrarian;

    @FXML
    private TextField dvdAgeLimitTextFieldLibrarian;

    @FXML
    private ComboBox<String> dvdLanguageComboBoxLibrarian;

    @FXML
    private ComboBox<String> dvdActorComboBox1;

    @FXML
    private ComboBox<String> dvdActorComboBox2;

    @FXML
    private ComboBox<String> dvdActorComboBox3;

    @FXML
    private ComboBox<String> dvdDirectorComboBox1;

    @FXML
    private ComboBox<String> dvdDirectorComboBox2;

    @FXML
    private ComboBox<String> dvdDirectorComboBox3;

    @FXML
    private ComboBox<String> dvdGenreComboBox1;

    @FXML
    private ComboBox<String> dvdGenreComboBox2;

    @FXML
    private ComboBox<String> dvdGenreComboBox3;

    @FXML
    private ComboBox<String> dvdKeywordComboBox1;

    @FXML
    private ComboBox<String> dvdKeywordComboBox2;

    @FXML
    private ComboBox<String> dvdKeywordComboBox3;

    @FXML
    private ComboBox<String> dvdFloorComboBoxLibrarian;

    @FXML
    private ComboBox<String> dvdSectionComboBoxLibrarian;

    @FXML
    private ComboBox<String> dvdShelfComboBoxLibrarian;

    @FXML
    private ComboBox<String> dvdPositionComboBoxLibrarian;

    @FXML
    private Button cancelChangeDvdButtonLibrarian;

    @FXML
    private Button saveChangesDvdButtonLibrarian;

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
    private void clickedCancelChangeDvdButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesDvdButtonLibrarian(MouseEvent event) {
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
        dvdLanguageComboBoxLibrarian.getItems().setAll("English", "French", "German", "Spanish");
        dvdActorComboBox1.getItems().setAll("Actor A", "Actor B", "Actor C");
        dvdActorComboBox2.getItems().setAll("Actor D", "Actor E", "Actor F");
        dvdActorComboBox3.getItems().setAll("Actor G", "Actor H", "Actor I");
        dvdDirectorComboBox1.getItems().setAll("Director X", "Director Y", "Director Z");
        dvdDirectorComboBox2.getItems().setAll("Co-Director M", "Co-Director N");
        dvdDirectorComboBox3.getItems().setAll("Co-Director O", "Co-Director P");
        dvdGenreComboBox1.getItems().setAll("Action", "Comedy", "Drama", "Documentary");
        dvdGenreComboBox2.getItems().setAll("Sci-Fi", "Romance", "Thriller");
        dvdGenreComboBox3.getItems().setAll("Animation", "Horror", "Adventure");
        dvdKeywordComboBox1.getItems().setAll("Classic", "Award-winning", "Family");
        dvdKeywordComboBox2.getItems().setAll("New Release", "Cult", "Blockbuster");
        dvdKeywordComboBox3.getItems().setAll("Foreign", "Indie", "Musical");
        dvdFloorComboBoxLibrarian.getItems().setAll("1", "2", "3");
        dvdSectionComboBoxLibrarian.getItems().setAll("A", "B", "C");
        dvdShelfComboBoxLibrarian.getItems().setAll("Shelf 1", "Shelf 2", "Shelf 3");
        dvdPositionComboBoxLibrarian.getItems().setAll("1", "2", "3");
    }
}
