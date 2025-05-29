package com.javafullstacklibrary.frontend.librarianControllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Journal;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.services.CreatorManagementService;
import com.javafullstacklibrary.services.ItemManagementService;
import com.javafullstacklibrary.services.KeywordManagementService;
import com.javafullstacklibrary.services.LanguageManagementService;
import com.javafullstacklibrary.services.LocationManagementService;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ModifyJournalLibrarianController {
    private final Item itemToModify; // The item to modify, passed from the previous view

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

    

    /**
     * Constructor to initialize the controller with an item to modify.
     * This constructor is used when navigating from the Manage Library view to modify a specific journal item.
     * @param item
     */
    public ModifyJournalLibrarianController(Item item) {
        this.itemToModify = item;
    }

    // No-arg constructor for FXML loader
    public ModifyJournalLibrarianController() {
        this.itemToModify = null;
    }

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
    private void clickedSaveChangesJournalButtonLibrarian(MouseEvent event) {
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

            // Update existing journal instead of creating new one
            if (itemToModify != null) {
                // Update the existing item's fields
                itemToModify.setTitle(title);
                itemToModify.setPublisher(publisher);
                itemToModify.setLocation(location);
                itemToModify.setLanguage(language);
                itemToModify.setKeywords(keywords);
                itemToModify.setCreators(authors);

                // If it's a Journal, update ISSN
                if (itemToModify instanceof Journal journal) {
                    journal.setISSN(issn);
                }

                // Update item in database
                itemManagementService.updateItem(itemToModify);
            } else {
                // Create new journal if itemToModify is null
                Item newJournal = itemManagementService.createItem(
                    "journal",
                    location,
                    language,
                    keywords,
                    authors,
                    null, // actors not applicable
                    null, // genres not applicable
                    issn,
                    null, // identifier2 not applicable
                    title,
                    publisher,
                    null, // ageLimit not applicable
                    null  // countryOfProduction not applicable
                );
                // Save new item in database
                itemManagementService.addItem(newJournal);
            }

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(itemToModify != null ? "Journal Updated" : "Journal Created");
            alert.setContentText("The journal \"" + title + "\" has been successfully " + 
                               (itemToModify != null ? "updated" : "created") + ".");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(itemToModify != null ? "Failed to Update Journal" : "Failed to Create Journal");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
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
        if (itemToModify != null) {
            populateBasicFields();
            populateLocationFields();
            populateKeywordFields();
            populateAuthorFields();
        }
    }

    /**
     * Populates the basic fields (title, publisher, ISSN, language) with item data
     */
    private void populateBasicFields() {
        journalTitleTextFieldLibrarian.setText(itemToModify.getTitle());
        journalPublisherTextFieldLibrarian.setText(itemToModify.getPublisher());

        if (itemToModify instanceof Journal journal) {
            journalIssnTextFieldLibrarian.setText(journal.getISSN());
        }

        if (itemToModify.getLanguage() != null) {
            journalLanguageComboBoxLibrarian.setValue(itemToModify.getLanguage().getLanguage());
        }
    }

    /**
     * Populates the location fields with item data
     */
    private void populateLocationFields() {
        if (itemToModify.getLocation() != null) {
            journalFloorComboBoxLibrarian.setValue(itemToModify.getLocation().getFloor());
            journalSectionComboBoxLibrarian.setValue(itemToModify.getLocation().getSection());
            journalShelfComboBoxLibrarian.setValue(itemToModify.getLocation().getShelf());
            journalPositionComboBoxLibrarian.setValue(itemToModify.getLocation().getPosition());
        }
    }

    /**
     * Populates the keyword fields with item data
     */
    private void populateKeywordFields() {
        if (itemToModify.getKeywords() != null && !itemToModify.getKeywords().isEmpty()) {
            List<Keyword> keywordList = itemToModify.getKeywords().stream().toList();
            if (keywordList.size() > 0) {
                journalKeywordComboBoxLibrarian1.setValue(keywordList.get(0).getKeyword());
            }
            if (keywordList.size() > 1) {
                journalKeywordComboBoxLibrarian2.setValue(keywordList.get(1).getKeyword());
            }
            if (keywordList.size() > 2) {
                journalKeywordComboBoxLibrarian3.setValue(keywordList.get(2).getKeyword());
            }
        }
    }

    /**
     * Populates the author fields with item data
     */
    private void populateAuthorFields() {
        if (itemToModify.getCreators() != null && !itemToModify.getCreators().isEmpty()) {
            List<Creator> authorList = itemToModify.getCreators().stream().toList();
            if (authorList.size() > 0) {
                journalAuthorComboBoxLibrarian1.setValue(authorList.get(0).getFullName());
            }
            if (authorList.size() > 1) {
                journalAuthorComboBoxLibrarian2.setValue(authorList.get(1).getFullName());
            }
            if (authorList.size() > 2) {
                journalAuthorComboBoxLibrarian3.setValue(authorList.get(2).getFullName());
            }
        }
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

    /**
     * Collects all keywords selected in the keyword combo boxes.
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        addIfNotEmpty(journalKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(journalKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(journalKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     */
    private Language collectLanguage() {
        String languageName = journalLanguageComboBoxLibrarian.getValue();
        if (languageName != null && !languageName.isEmpty()) {
            return languageManagementService.findByName(languageName);
        }
        throw new IllegalArgumentException("Language cannot be null or empty");
    }

    /**
     * Collects all authors selected in the author combo boxes.
     */
    private List<Creator> collectAuthors() {
        List<Creator> authors = new ArrayList<>();
        addIfNotEmpty(journalAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(journalAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(journalAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
    }

    /**
     * Collects the location from the location combo boxes.
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
     */
    private <T> void addIfNotEmpty(String value, List<T> list, Function<String, T> finder) {
        if (value != null && !value.isEmpty()) {
            list.add(finder.apply(value));
        }
    }
}
