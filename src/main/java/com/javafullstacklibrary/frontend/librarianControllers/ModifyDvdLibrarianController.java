package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Map;

import com.javafullstacklibrary.services.*; // Importing all services

/**
 * Controller for the Modify DVD view for librarians.
 */
public class ModifyDvdLibrarianController {

    @FXML
    private Pane mainPane;

    // --- TextFields for forms ---
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

    // --- ComboBoxes for form ---
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

    // --- Button declarations ---
    @FXML
    private Button cancelChangeDvdButtonLibrarian;

    @FXML
    private Button saveChangesDvdButtonLibrarian;

    // --- Service declarations ---
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();
    private final ActorManagementService actorManagementService = new ActorManagementService();

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

    @FXML 
    private void clickedDeleteDvdButtonLibrarian(MouseEvent event) {
        // Logic to delete the DVD entry
        // This could involve confirmation dialog and then removing the entry from the database
        System.out.println("Delete DVD button clicked.");
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
     * Populates all ComboBox fields with data from the services.
     * This method is called from initialize().
     */
    private void populateComboBoxes() {
        // Get location details from the service
        Map<String, ObservableList<String>> locationDetails = locationManagementService.getLocationDetails();
        ObservableList<String> floors = locationDetails.get("floors");
        ObservableList<String> sections = locationDetails.get("sections");
        ObservableList<String> shelves = locationDetails.get("shelves");
        ObservableList<String> positions = locationDetails.get("positions");

        // Fetching data from services
        ObservableList<String> languages = languageManagementService.getAllStrings();
        ObservableList<String> actors = actorManagementService.getAllActorsFullNames();
        ObservableList<String> directors = creatorManagementService.getAllFullNames();
        ObservableList<String> keywords = keywordManagementService.getAllStrings();
        ObservableList<String> genres = genreManagementService.getAllStrings();

        // Populate location comboboxes
        dvdLanguageComboBoxLibrarian.setItems(languages);
        dvdFloorComboBoxLibrarian.setItems(floors);
        dvdSectionComboBoxLibrarian.setItems(sections);
        dvdShelfComboBoxLibrarian.setItems(shelves);
        dvdPositionComboBoxLibrarian.setItems(positions);

        // Populate actor comboboxes
        dvdActorComboBox1.setItems(actors);
        dvdActorComboBox2.setItems(actors);
        dvdActorComboBox3.setItems(actors);

        // Populate director comboboxes
        dvdDirectorComboBox1.setItems(directors);
        dvdDirectorComboBox2.setItems(directors);
        dvdDirectorComboBox3.setItems(directors);

        // Populate genre comboboxes
        dvdGenreComboBox1.setItems(genres);
        dvdGenreComboBox2.setItems(genres);
        dvdGenreComboBox3.setItems(genres);

        // Populate keyword comboboxes
        dvdKeywordComboBox1.setItems(keywords);
        dvdKeywordComboBox2.setItems(keywords);
        dvdKeywordComboBox3.setItems(keywords);
    }
}
