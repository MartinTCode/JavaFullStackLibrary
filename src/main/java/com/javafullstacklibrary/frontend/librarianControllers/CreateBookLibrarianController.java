package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.function.Function;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.services.CreatorManagementService;
import com.javafullstacklibrary.services.GenreManagementService;
import com.javafullstacklibrary.services.KeywordManagementService;
import com.javafullstacklibrary.services.LanguageManagementService;
import com.javafullstacklibrary.services.LocationManagementService;

public class CreateBookLibrarianController {

    @FXML
    private Pane mainPane;

    // TextFields
    @FXML private TextField bookTitleTextFieldLibrarian;
    @FXML private TextField bookIsbn13TextFieldLibrarian;
    @FXML private TextField bookIsbn10TextFieldLibrarian;
    @FXML private TextField bookPublisherTextFieldLibrarian;

    // Comboboxes
    @FXML private ComboBox<String> bookLanguageComboBoxLibrarian;
    @FXML private ComboBox<String> bookFloorComboBoxLibrarian;
    @FXML private ComboBox<String> bookSectionComboBoxLibrarian;
    @FXML private ComboBox<String> bookShelfComboBoxLibrarian;
    @FXML private ComboBox<String> bookPositionComboBoxLibrarian;
    @FXML private ComboBox<String> bookAuthorComboBoxLibrarian1;
    @FXML private ComboBox<String> bookAuthorComboBoxLibrarian2;
    @FXML private ComboBox<String> bookAuthorComboBoxLibrarian3;
    @FXML private ComboBox<String> bookGenreComboBoxLibrarian1;
    @FXML private ComboBox<String> bookGenreComboBoxLibrarian2;
    @FXML private ComboBox<String> bookGenreComboBoxLibrarian3;
    @FXML private ComboBox<String> bookKeywordComboBoxLibrarian1;
    @FXML private ComboBox<String> bookKeywordComboBoxLibrarian2;
    @FXML private ComboBox<String> bookKeywordComboBoxLibrarian3;

    //Services
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();
    

    // Top menu icons
    @FXML private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }
    @FXML private void clickedSearchMenyLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }
    @FXML private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }
    @FXML private void clickedManageUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }
    @FXML private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }
    @FXML private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // Cancel button handler
    @FXML
    private void clickedCancelNewBookButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /** 
     * Initializes the controller after the FXML file has been loaded.
     * This method is called by the FXMLLoader when the controller is created.
     * It populates the combo boxes with mock data for demonstration purposes.
     */
    @FXML
    public void initialize() {
        populateComboBoxes();
    }

    /** 
     * Handles the click event for the "Save New Book" button.
     * This method reads input from text fields and combo boxes,
     * prints the values to the console for now 
     * #TODO: Replace with actual save logic when database integration is implemented
     * @param event the mouse event that triggered this method
     */
    @FXML
    private void clickedSaveNewBookButtonLibrarian(MouseEvent event) {
        // Get text field values
        String title = bookTitleTextFieldLibrarian.getText();
        String isbn13 = bookIsbn13TextFieldLibrarian.getText();
        String isbn10 = bookIsbn10TextFieldLibrarian.getText();
        String publisher = bookPublisherTextFieldLibrarian.getText();
        
        // Get location values
        String floor = bookFloorComboBoxLibrarian.getValue();
        String section = bookSectionComboBoxLibrarian.getValue();
        String shelf = bookShelfComboBoxLibrarian.getValue();
        String position = bookPositionComboBoxLibrarian.getValue();

        // Add new input to combo boxes if not already present
        addNewInputToComboBox(bookFloorComboBoxLibrarian);
        addNewInputToComboBox(bookSectionComboBoxLibrarian);
        addNewInputToComboBox(bookShelfComboBoxLibrarian);
        addNewInputToComboBox(bookPositionComboBoxLibrarian);

        // Get lists of related entities
        List<Genre> genres = collectGenres();
        List<Keyword> keywords = collectKeywords();
        List<Language> languages = collectLanguages();
        List<Creator> authors = collectAuthors();
        
        // TODO: Add logic to save book to database using collected values
    }

    /**
     * Collects all genres selected in the genre combo boxes.
     * @return List of Genre objects from the selected values
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new java.util.ArrayList<>();
        addIfNotEmpty(bookGenreComboBoxLibrarian1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(bookGenreComboBoxLibrarian2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(bookGenreComboBoxLibrarian3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all keywords selected in the keyword combo boxes.
     * @return List of Keyword objects from the selected values
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new java.util.ArrayList<>();
        addIfNotEmpty(bookKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(bookKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(bookKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     * @return List of Language objects from the selected value
     */
    private List<Language> collectLanguages() {
        List<Language> languages = new java.util.ArrayList<>();
        addIfNotEmpty(bookLanguageComboBoxLibrarian.getValue(), languages, languageManagementService::findByName);
        return languages;
    }

    /**
     * Collects all authors selected in the author combo boxes.
     * @return List of Creator objects from the selected values
     */
    private List<Creator> collectAuthors() {
        List<Creator> authors = new java.util.ArrayList<>();
        addIfNotEmpty(bookAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(bookAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(bookAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
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
     * Populates the combo boxes with mock data.
     * This method is called during the initialization of the controller.
     * #TODO: Replace mock data with actual data from the database when implemented.
     */
    private void populateComboBoxes() {
        // Mock data for demonstration
        ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "4");
        ObservableList<String> sections = FXCollections.observableArrayList("A", "B", "C", "D");
        ObservableList<String> shelves = FXCollections.observableArrayList("Shelf 1", "Shelf 2", "Shelf 3");
        ObservableList<String> positions = FXCollections.observableArrayList("1", "2", "3", "4", "5");

        // Fetching data from services
        ObservableList<String> languages = languageManagementService.getAllStrings();
        ObservableList<String> authors = creatorManagementService.getAllFullNames();
        ObservableList<String> keywords = keywordManagementService.getAllStrings();
        ObservableList<String> genres = genreManagementService.getAllStrings();

        bookLanguageComboBoxLibrarian.setItems(languages);
        bookFloorComboBoxLibrarian.setItems(floors);
        bookSectionComboBoxLibrarian.setItems(sections);
        bookShelfComboBoxLibrarian.setItems(shelves);
        bookPositionComboBoxLibrarian.setItems(positions);

        bookAuthorComboBoxLibrarian1.setItems(authors);
        bookAuthorComboBoxLibrarian2.setItems(authors);
        bookAuthorComboBoxLibrarian3.setItems(authors);

        bookGenreComboBoxLibrarian1.setItems(genres);
        bookGenreComboBoxLibrarian2.setItems(genres);
        bookGenreComboBoxLibrarian3.setItems(genres);

        bookKeywordComboBoxLibrarian1.setItems(keywords);
        bookKeywordComboBoxLibrarian2.setItems(keywords);
        bookKeywordComboBoxLibrarian3.setItems(keywords);
    }

    /**
     * Adds the value from the ComboBox to its items if it is a new user input.
     * @param comboBox The ComboBox to check and add to.
     * #TODO: Implement actual database save logic when database integration is done.
     */
    private void addNewInputToComboBox(ComboBox<String> comboBox) {
        String value = comboBox.getValue();
        if (value != null && !value.isEmpty() && !comboBox.getItems().contains(value)) {
            comboBox.getItems().add(value);
            System.out.println("Added new value to ComboBox: " + value);
            // Add logic to save to database here when implemented
        }
    }
}
