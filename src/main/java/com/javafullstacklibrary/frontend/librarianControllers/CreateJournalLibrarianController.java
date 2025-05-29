package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.services.CreatorManagementService;
import com.javafullstacklibrary.services.ItemManagementService;
import com.javafullstacklibrary.services.KeywordManagementService;
import com.javafullstacklibrary.services.LanguageManagementService;
import com.javafullstacklibrary.services.LocationManagementService;

public class CreateJournalLibrarianController {

    @FXML
    private BorderPane mainPane;

    // TextFields for form fields
    @FXML private TextField journalTitleTextFieldLibrarian;
    @FXML private TextField journalIssnTextFieldLibrarian;
    @FXML private TextField journalPublisherTextFieldLibrarian;

    // ComboBoxes for form fields
    @FXML private ComboBox<String> journalLanguageComboBoxLibrarian;
    @FXML private ComboBox<String> journalFloorComboBoxLibrarian;
    @FXML private ComboBox<String> journalSectionComboBoxLibrarian;
    @FXML private ComboBox<String> journalShelfComboBoxLibrarian;
    @FXML private ComboBox<String> journalPositionComboBoxLibrarian;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian1;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian2;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian3;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian1;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian2;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian3;

    // Services
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();

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
    private void clickedManageUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    /**
     * Handles the click event for the Cancel button.
     * Returns to the Manage Library view.
     * @param event The mouse event that triggered this method
     */
    @FXML
    private void clickedCancelNewJournalButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles the click event for the "Save New Journal" button.
     * Creates and saves a new journal to the database using the form input.
     * @param event The mouse event that triggered this method
     */
    @FXML
    private void clickedSaveNewJournalButtonLibrarian(MouseEvent event) {
        try {
            // Get text field values
            String title = journalTitleTextFieldLibrarian.getText();
            String issn = journalIssnTextFieldLibrarian.getText();
            String publisher = journalPublisherTextFieldLibrarian.getText();

            // Convert Lists to Sets
            Set<Creator> authors = new HashSet<>(collectAuthors());
            Set<Keyword> keywords = new HashSet<>(collectKeywords());
            Language language = collectLanguage();
            Location location = collectLocation();

            // Create a new journal
            Item newJournal = itemManagementService.createItem(
                "journal",
                location,
                language,
                keywords,
                authors,
                null, // actors not applicable for journals
                null, // genres not applicable for journals
                issn,
                null, // secondary identifier not applicable for journals
                title,
                publisher,
                null, // age limit not applicable for journals
                null  // country of production not applicable for journals
            );
            // Save the new journal item to the database
            itemManagementService.addItem(newJournal);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Journal Created");
            alert.setContentText("The journal \"" + title + "\" has been successfully created and saved.");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Create Journal");
            alert.setContentText("An error occurred while creating the journal: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Populates all combo boxes with data from the database.
     */
    @FXML
    public void initialize() {
        populateComboBoxes();
    }

    /**
     * Collects all keywords selected in the keyword combo boxes.
     * @return List of Keyword objects from the selected values
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new java.util.ArrayList<>();
        addIfNotEmpty(journalKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(journalKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(journalKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     * @return Language object from the selected value
     * @throws IllegalArgumentException if no language is selected
     */
    private Language collectLanguage() {
        String languageName = journalLanguageComboBoxLibrarian.getValue();
        if (languageName != null && !languageName.isEmpty()) {
            return languageManagementService.findByName(languageName);
        }
        else {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
    }

    /**
     * Collects all authors selected in the author combo boxes.
     * @return List of Creator objects from the selected values
     */
    private List<Creator> collectAuthors() {
        List<Creator> authors = new java.util.ArrayList<>();
        addIfNotEmpty(journalAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(journalAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(journalAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
    }

    /**
     * Collects location information from the location combo boxes.
     * @return Location object created from the selected values
     */
    private Location collectLocation() {
        String floor = journalFloorComboBoxLibrarian.getValue();
        String section = journalSectionComboBoxLibrarian.getValue();
        String shelf = journalShelfComboBoxLibrarian.getValue();
        String position = journalPositionComboBoxLibrarian.getValue();

        return locationManagementService.findOrCreate(floor, section, shelf, position);
    }

    /**
     * Helper method to add a value to a list if the value is not null or empty.
     * @param <T> The type of object to be added to the list
     * @param value The string value to check
     * @param list The list to add the converted value to
     * @param finder The function to convert the string value to type T
     */
    private <T> void addIfNotEmpty(String value, List<T> list, Function<String, T> finder) {
        if (value != null && !value.isEmpty()) {
            list.add(finder.apply(value));
        }
    }

    /**
     * Populates all combo boxes with data from the database.
     * Called during controller initialization.
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
