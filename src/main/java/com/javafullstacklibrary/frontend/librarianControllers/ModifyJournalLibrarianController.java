package com.javafullstacklibrary.frontend.librarianControllers;

import java.util.Map;

import com.javafullstacklibrary.services.CreatorManagementService;
import com.javafullstacklibrary.services.ItemManagementService;
import com.javafullstacklibrary.services.KeywordManagementService;
import com.javafullstacklibrary.services.LanguageManagementService;
import com.javafullstacklibrary.services.LocationManagementService;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ModifyJournalLibrarianController {

    @FXML
    private Pane mainPane;

    // --- TextFields for forms ---
    @FXML
    private TextField journalTitleTextFieldLibrarian;

    @FXML
    private TextField journalIssnTextFieldLibrarian;

    @FXML
    private TextField journalPublisherTextFieldLibrarian;

    // --- ComboBoxes for form ---
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

    // --- Buttons for actions ---
    @FXML
    private Button cancelChangeJournalButtonLibrarian;

    @FXML
    private Button saveChangesJournalButtonLibrarian;

    
    // --- Services for data management ---
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();


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

    @FXML
    private void clickedDeleteJournalButtonLibrarian(MouseEvent event) {
        // Logic to delete the journal entry
        // This could involve confirmation dialog and then removing the entry from the database
        System.out.println("Delete Journal button clicked");
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

        // Fetch data from services
        ObservableList<String> languages = languageManagementService.getAllStrings();
        ObservableList<String> authors = creatorManagementService.getAllFullNames();
        ObservableList<String> keywords = keywordManagementService.getAllStrings();

        // Set items for each ComboBox
        journalLanguageComboBoxLibrarian.setItems(languages);
        journalFloorComboBoxLibrarian.setItems(floors);
        journalSectionComboBoxLibrarian.setItems(sections);
        journalShelfComboBoxLibrarian.setItems(shelves);
        journalPositionComboBoxLibrarian.setItems(positions);

        journalKeywordComboBoxLibrarian1.setItems(keywords);
        journalKeywordComboBoxLibrarian2.setItems(keywords);
        journalKeywordComboBoxLibrarian3.setItems(keywords);

        journalAuthorComboBoxLibrarian1.setItems(authors);
        journalAuthorComboBoxLibrarian2.setItems(authors);
        journalAuthorComboBoxLibrarian3.setItems(authors);
    }
}
