package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.model.CourseLitterature;
import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.services.*; // Importing all services at once
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Controller for the Modify Course Literature view for librarians.
 */
public class ModifyCourseLitLibrarianController {
    private final Item itemToModify;

    @FXML
    private Pane mainPane;

    // --- TextFields for forms ---
    @FXML
    private TextField CourseLitTitleTextFieldLibrarian;

    @FXML
    private TextField CourseLitIsbn13TextFieldLibrarian;

    @FXML
    private TextField CourseLitIsbn10TextFieldLibrarian;

    @FXML
    private TextField CourseLitPublisherTextFieldLibrarian;

    // --- ComboBoxes for form ---
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

    // --- Buttons for actions ---
    @FXML
    private Button cancelChangeCourseLitButtonLibrarian;

    @FXML
    private Button saveChangesCourseLitButtonLibrarian;

    // --- Services for data management ---
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();

    /**
     * Constructor for ModifyCourseLitLibrarianController.
     * Initializes the controller with an Item to modify.
     * @param item
     */
    public ModifyCourseLitLibrarianController(Item item) {
        this.itemToModify = item;
    }

    // No-arg constructor for FXML loader
    public ModifyCourseLitLibrarianController() {
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
    private void clickedCancelChangeCourseLitButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesCourseLitButtonLibrarian(MouseEvent event) {
        try {
            // Get text field values
            String title = CourseLitTitleTextFieldLibrarian.getText();
            String isbn13 = CourseLitIsbn13TextFieldLibrarian.getText();
            String isbn10 = CourseLitIsbn10TextFieldLibrarian.getText();
            String publisher = CourseLitPublisherTextFieldLibrarian.getText();
            
            // Convert Lists to Sets
            Set<Creator> authors = new HashSet<>(collectAuthors());
            Set<Genre> genres = new HashSet<>(collectGenres());
            Set<Keyword> keywords = new HashSet<>(collectKeywords());     
            Language language = collectLanguage();
            Location location = collectLocation();       

            // Update existing course literature instead of creating new one
            if (itemToModify != null) {
                // Update the existing item's fields
                itemToModify.setTitle(title);
                itemToModify.setPublisher(publisher);
                itemToModify.setLocation(location);
                itemToModify.setLanguage(language);
                itemToModify.setKeywords(keywords);
                itemToModify.setCreators(authors);
                itemToModify.setGenres(genres);

                // If it's a CourseLitterature, update ISBN numbers
                if (itemToModify instanceof CourseLitterature courseLit) {
                    courseLit.setISBN13(isbn13);
                    courseLit.setISBN10(isbn10);
                }

                // Update item in database
                itemManagementService.updateItem(itemToModify);
            } else {
                // Create new course literature if itemToModify is null
                Item newCourseLit = itemManagementService.createItem(
                    "course_litterature",
                    location,
                    language,
                    keywords,
                    authors,
                    null, // actors not applicable
                    genres,
                    isbn13,
                    isbn10,
                    title,
                    publisher,
                    null, // ageLimit not applicable
                    null  // countryOfProduction not applicable
                );
                // Save new item in database
                itemManagementService.addItem(newCourseLit);
            }

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(itemToModify != null ? "Course Literature Updated" : "Course Literature Created");
            alert.setContentText("The course literature \"" + title + "\" has been successfully " + 
                               (itemToModify != null ? "updated" : "created") + ".");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(itemToModify != null ? "Failed to Update Course Literature" : "Failed to Create Course Literature");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void clickedDeleteCourseLitButtonLibrarian(MouseEvent event) {
        if (itemToModify == null) {
            // Can't delete an item that doesn't exist yet
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Delete");
            alert.setHeaderText("No Course Literature Selected");
            alert.setContentText("Cannot delete course literature that hasn't been saved yet.");
            alert.showAndWait();
            return;
        }

        // Show confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Delete Course Literature");
        confirmDialog.setContentText("Are you sure you want to delete the course literature \"" + 
                           itemToModify.getTitle() + "\"? This action cannot be undone.");

        // Get user's response, proceed if OK is clicked
        if (confirmDialog.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                // User clicked OK, proceed with deletion
                itemManagementService.deleteItem(itemToModify);

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Course Literature Deleted");
                successAlert.setContentText("The course literature \"" + itemToModify.getTitle() + 
                                     "\" has been successfully deleted.");
                successAlert.showAndWait();

                // Navigate back to manage library view
                MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

            } catch (Exception e) {
                // Show error message if deletion fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to Delete Course Literature");
                errorAlert.setContentText("An error occurred while deleting the course literature: " + 
                                    e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Handles click on the Manage Copies button.
     * Navigates to the Manage Copies view for the selected book.
     * @param event
     */
    @FXML
    private void clickedManageCopiesButtonLibrarian(MouseEvent event) {
        // Navigate to the Manage Copies view for the selected book
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "ManageCopies", itemToModify);
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
            populateAuthorFields();
            populateGenreFields();
            populateKeywordFields();
        }
    }

    /**
     * Populates the basic fields (title, publisher, ISBN) with item data
     */
    private void populateBasicFields() {
        CourseLitTitleTextFieldLibrarian.setText(itemToModify.getTitle());
        CourseLitPublisherTextFieldLibrarian.setText(itemToModify.getPublisher());
        
        if (itemToModify instanceof CourseLitterature courseLit) {
            CourseLitIsbn13TextFieldLibrarian.setText(courseLit.getISBN13());
            CourseLitIsbn10TextFieldLibrarian.setText(courseLit.getISBN10());
        }

        if (itemToModify.getLanguage() != null) {
            CourseLitLanguageComboBoxLibrarian.setValue(itemToModify.getLanguage().getLanguage());
        }
    }

    /**
     * Populates the location fields with item data
     */
    private void populateLocationFields() {
        if (itemToModify.getLocation() != null) {
            CourseLitFloorComboBoxLibrarian.setValue(itemToModify.getLocation().getFloor());
            CourseLitSectionComboBoxLibrarian.setValue(itemToModify.getLocation().getSection());
            CourseLitShelfComboBoxLibrarian.setValue(itemToModify.getLocation().getShelf());
            CourseLitPositionComboBoxLibrarian.setValue(itemToModify.getLocation().getPosition());
        }
    }

    /**
     * Populates the author fields with item data
     */
    private void populateAuthorFields() {
        if (itemToModify.getCreators() != null && !itemToModify.getCreators().isEmpty()) {
            List<Creator> creatorList = itemToModify.getCreators().stream().toList();
            if (creatorList.size() > 0) {
                CourseLitAuthorComboBoxLibrarian1.setValue(creatorList.get(0).getFullName());
            }
            if (creatorList.size() > 1) {
                CourseLitAuthorComboBoxLibrarian2.setValue(creatorList.get(1).getFullName());
            }
            if (creatorList.size() > 2) {
                CourseLitAuthorComboBoxLibrarian3.setValue(creatorList.get(2).getFullName());
            }
        }
    }

    /**
     * Populates the genre fields with item data
     */
    private void populateGenreFields() {
        if (itemToModify.getGenres() != null && !itemToModify.getGenres().isEmpty()) {
            List<Genre> genreList = itemToModify.getGenres().stream().toList();
            if (genreList.size() > 0) {
                CourseLitGenreComboBoxLibrarian1.setValue(genreList.get(0).getGenre());
            }
            if (genreList.size() > 1) {
                CourseLitGenreComboBoxLibrarian2.setValue(genreList.get(1).getGenre());
            }
            if (genreList.size() > 2) {
                CourseLitGenreComboBoxLibrarian3.setValue(genreList.get(2).getGenre());
            }
        }
    }

    /**
     * Populates the keyword fields with item data
     */
    private void populateKeywordFields() {
        if (itemToModify.getKeywords() != null && !itemToModify.getKeywords().isEmpty()) {
            List<Keyword> keywordList = itemToModify.getKeywords().stream().toList();
            if (keywordList.size() > 0) {
                CourseLitKeywordComboBoxLibrarian1.setValue(keywordList.get(0).getKeyword());
            }
            if (keywordList.size() > 1) {
                CourseLitKeywordComboBoxLibrarian2.setValue(keywordList.get(1).getKeyword());
            }
            if (keywordList.size() > 2) {
                CourseLitKeywordComboBoxLibrarian3.setValue(keywordList.get(2).getKeyword());
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

        // Fetching data from services
        ObservableList<String> languages = languageManagementService.getAllStrings();
        ObservableList<String> authors = creatorManagementService.getAllFullNames();
        ObservableList<String> keywords = keywordManagementService.getAllStrings();
        ObservableList<String> genres = genreManagementService.getAllStrings();

        // Populate location comboboxes
        CourseLitLanguageComboBoxLibrarian.setItems(languages);
        CourseLitFloorComboBoxLibrarian.setItems(floors);
        CourseLitSectionComboBoxLibrarian.setItems(sections);
        CourseLitShelfComboBoxLibrarian.setItems(shelves);
        CourseLitPositionComboBoxLibrarian.setItems(positions);

        // Populate author comboboxes
        CourseLitAuthorComboBoxLibrarian1.setItems(authors);
        CourseLitAuthorComboBoxLibrarian2.setItems(authors);
        CourseLitAuthorComboBoxLibrarian3.setItems(authors);

        // Populate genre comboboxes
        CourseLitGenreComboBoxLibrarian1.setItems(genres);
        CourseLitGenreComboBoxLibrarian2.setItems(genres);
        CourseLitGenreComboBoxLibrarian3.setItems(genres);

        // Populate keyword comboboxes
        CourseLitKeywordComboBoxLibrarian1.setItems(keywords);
        CourseLitKeywordComboBoxLibrarian2.setItems(keywords);
        CourseLitKeywordComboBoxLibrarian3.setItems(keywords);
    }

    /**
     * Collects all genres selected in the genre combo boxes.
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new ArrayList<>();
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all keywords selected in the keyword combo boxes.
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     */
    private Language collectLanguage() {
        String languageName = CourseLitLanguageComboBoxLibrarian.getValue();
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
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
    }

    /**
     * Collects the location from the location combo boxes.
     */
    private Location collectLocation() {
        String floor = CourseLitFloorComboBoxLibrarian.getValue();
        String section = CourseLitSectionComboBoxLibrarian.getValue();
        String shelf = CourseLitShelfComboBoxLibrarian.getValue();
        String position = CourseLitPositionComboBoxLibrarian.getValue();
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
