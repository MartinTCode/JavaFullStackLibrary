package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.services.*;
import java.util.*;
import java.util.function.Function;

public class CreateCourseLitLibrarianController {

    @FXML
    private BorderPane mainPane;

    // TextFields for form fields
    @FXML private TextField CourseLitTitleTextFieldLibrarian;
    @FXML private TextField CourseLitIsbn13TextFieldLibrarian;
    @FXML private TextField CourseLitIsbn10TextFieldLibrarian;
    @FXML private TextField CourseLitPublisherTextFieldLibrarian;

    // ComboBoxes for form fields
    @FXML private ComboBox<String> CourseLitLanguageComboBoxLibrarian;
    @FXML private ComboBox<String> CourseLitFloorComboBoxLibrarian;
    @FXML private ComboBox<String> CourseLitSectionComboBoxLibrarian;
    @FXML private ComboBox<String> CourseLitShelfComboBoxLibrarian;
    @FXML private ComboBox<String> CourseLitPositionComboBoxLibrarian;
    @FXML private ComboBox<String> CourseLitAuthorComboBoxLibrarian1;
    @FXML private ComboBox<String> CourseLitAuthorComboBoxLibrarian2;
    @FXML private ComboBox<String> CourseLitAuthorComboBoxLibrarian3;
    @FXML private ComboBox<String> CourseLitGenreComboBoxLibrarian1;
    @FXML private ComboBox<String> CourseLitGenreComboBoxLibrarian2;
    @FXML private ComboBox<String> CourseLitGenreComboBoxLibrarian3;
    @FXML private ComboBox<String> CourseLitKeywordComboBoxLibrarian1;
    @FXML private ComboBox<String> CourseLitKeywordComboBoxLibrarian2;
    @FXML private ComboBox<String> CourseLitKeywordComboBoxLibrarian3;

    // Add these service declarations after the ComboBox declarations
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();

    /**
     * This method is called when the controller is initialized.
     * It calls the applicable methods to initialize the view by populating all combo boxes.
     */
    @FXML
    public void initialize() {
        populateComboBoxes();
    }

    /**
     * Handles click event on the Home menu item.
     * Navigates to the Home view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    /**
     * Handles click event on the Search menu item.
     * Navigates to the Search view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedSearchMenyLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    /**
     * Handles click event on the Overdue menu item.
     * Navigates to the Overdue view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    /**
     * Handles click event on the Manage Users menu item.
     * Navigates to the Manage Users view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedManageUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    /**
     * Handles click event on the Account menu item.
     * Navigates to the Account view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    /**
     * Handles click event on the Sign Out menu item.
     * Navigates to the Sign Out view for librarians.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    /**
     * Handles click event on the Cancel button.
     * Returns to the Manage Library view without saving any changes.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedCancelNewCourseLitButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click event on the Save button.
     * Collects all form data and creates a new course literature item in the database.
     * @param event The mouse event that triggered this handler
     */
    @FXML
    private void clickedSaveNewCourseLitButtonLibrarian(MouseEvent event) {
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

            // Create a new course literature item
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
            // Save the item to database
            itemManagementService.addItem(newCourseLit);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Course Literature Created");
            alert.setContentText("The course literature \"" + title + "\" has been successfully created and saved.");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Create Course Literature");
            alert.setContentText("An error occurred while creating the course literature: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Populates all combo boxes with data from the database.
     * This includes languages, locations, authors, genres, and keywords.
     * Called during controller initialization.
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

        CourseLitLanguageComboBoxLibrarian.setItems(languages);
        CourseLitFloorComboBoxLibrarian.setItems(floors);
        CourseLitSectionComboBoxLibrarian.setItems(sections);
        CourseLitShelfComboBoxLibrarian.setItems(shelves);
        CourseLitPositionComboBoxLibrarian.setItems(positions);

        CourseLitAuthorComboBoxLibrarian1.setItems(authors);
        CourseLitAuthorComboBoxLibrarian2.setItems(authors);
        CourseLitAuthorComboBoxLibrarian3.setItems(authors);

        CourseLitGenreComboBoxLibrarian1.setItems(genres);
        CourseLitGenreComboBoxLibrarian2.setItems(genres);
        CourseLitGenreComboBoxLibrarian3.setItems(genres);

        CourseLitKeywordComboBoxLibrarian1.setItems(keywords);
        CourseLitKeywordComboBoxLibrarian2.setItems(keywords);
        CourseLitKeywordComboBoxLibrarian3.setItems(keywords);
    }

    /**
     * Collects all selected genres from the genre combo boxes.
     * @return List of Genre objects corresponding to the selected values
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new java.util.ArrayList<>();
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(CourseLitGenreComboBoxLibrarian3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all selected keywords from the keyword combo boxes.
     * @return List of Keyword objects corresponding to the selected values
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new java.util.ArrayList<>();
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(CourseLitKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the selected language from the language combo box.
     * @return Language object corresponding to the selected value
     * @throws IllegalArgumentException if no language is selected
     */
    private Language collectLanguage() {
        String languageName = CourseLitLanguageComboBoxLibrarian.getValue();
        if (languageName != null && !languageName.isEmpty()) {
            return languageManagementService.findByName(languageName);
        }
        else {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
    }

    /**
     * Collects all selected authors from the author combo boxes.
     * @return List of Creator objects corresponding to the selected values
     */
    private List<Creator> collectAuthors() {
        List<Creator> authors = new java.util.ArrayList<>();
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(CourseLitAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
    }

    /**
     * Collects the selected location details from all location combo boxes.
     * Creates a new location if the combination doesn't exist.
     * @return Location object representing the selected floor, section, shelf, and position
     */
    private Location collectLocation() {
        String floor = CourseLitFloorComboBoxLibrarian.getValue();
        String section = CourseLitSectionComboBoxLibrarian.getValue();
        String shelf = CourseLitShelfComboBoxLibrarian.getValue();
        String position = CourseLitPositionComboBoxLibrarian.getValue();

        return locationManagementService.findOrCreate(floor, section, shelf, position);
    }

    /**
     * Helper method to add an item to a list if the value exists.
     * @param <T> The type of object to create from the string value
     * @param value The string value to check
     * @param list The list to add the created object to
     * @param finder Function to convert the string value to type T
     */
    private <T> void addIfNotEmpty(String value, List<T> list, Function<String, T> finder) {
        if (value != null && !value.isEmpty()) {
            list.add(finder.apply(value));
        }
    }
}
