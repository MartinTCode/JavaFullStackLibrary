package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.model.Book;
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
import javafx.scene.control.Alert;

/**
 * Controller for the Modify Book (not Course Literature) view for librarians.
 */
public class ModifyBookLibrarianController {
    private final Item itemToModify;


    @FXML
    private Pane mainPane;

    // --- TextFields for forms ---
    @FXML
    private TextField bookTitleTextFieldLibrarian;

    @FXML
    private TextField bookIsbn13TextFieldLibrarian;

    @FXML
    private TextField bookIsbn10TextFieldLibrarian;

    @FXML
    private TextField bookPublisherTextFieldLibrarian;

    // --- ComboBoxes for form ---
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

    // --- Button declarations ---
    @FXML
    private Button cancelChangeBookButtonLibrarian;

    @FXML
    private Button saveChangesBookButtonLibrarian;

    // --- Service declarations ---
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();

    //Constructor
    public ModifyBookLibrarianController(Item item) {
        this.itemToModify = item;
    }

    // Add a no-arg constructor for FXML loader
    public ModifyBookLibrarianController() {
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
    private void clickedCancelChangeBookButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesBookButtonLibrarian(MouseEvent event) {
        try {
            // Get text field values
            String title = bookTitleTextFieldLibrarian.getText();
            String isbn13 = bookIsbn13TextFieldLibrarian.getText();
            String isbn10 = bookIsbn10TextFieldLibrarian.getText();
            String publisher = bookPublisherTextFieldLibrarian.getText();
            
            // Convert Lists to Sets
            Set<Creator> authors = new HashSet<>(collectAuthors());
            Set<Genre> genres = new HashSet<>(collectGenres());
            Set<Keyword> keywords = new HashSet<>(collectKeywords());     
            Language language = collectLanguage();
            Location location = collectLocation();       

            // Update existing book instead of creating new one
            if (itemToModify != null) {
                // Update the existing item's fields
                itemToModify.setTitle(title);
                itemToModify.setPublisher(publisher);
                itemToModify.setLocation(location);
                itemToModify.setLanguage(language);
                itemToModify.setKeywords(keywords);
                itemToModify.setCreators(authors);
                itemToModify.setGenres(genres);

                // If it's a Book, update ISBN numbers
                if (itemToModify instanceof Book book) {
                    book.setISBN13(isbn13);
                    book.setISBN10(isbn10);
                }

                // Update item in database
                itemManagementService.updateItem(itemToModify);
            } else {
                // Create new book if itemToModify is null
                Item newBook = itemManagementService.createItem(
                    "book",
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
                itemManagementService.addItem(newBook);
            }

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(itemToModify != null ? "Book Updated" : "Book Created");
            alert.setContentText("The book \"" + title + "\" has been successfully " + 
                               (itemToModify != null ? "updated" : "created") + ".");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(itemToModify != null ? "Failed to Update Book" : "Failed to Create Book");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Handles click on the Delete Book button.
     * @param event
     */
    @FXML
    private void clickedDeleteBookButtonLibrarian(MouseEvent event) {
        if (itemToModify == null) {
            // Can't delete an item that doesn't exist yet
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Delete");
            alert.setHeaderText("No Book Selected");
            alert.setContentText("Cannot delete a book that hasn't been saved yet.");
            alert.showAndWait();
            return;
        }

        // Show confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Delete Book");
        confirmDialog.setContentText("Are you sure you want to delete the book \"" + 
                               itemToModify.getTitle() + "\"? This action cannot be undone.");

        // Get user's response, proceed if OK is clicked
        if (confirmDialog.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                // User clicked OK, proceed with deletion
                itemManagementService.deleteItem(itemToModify);

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Book Deleted");
                successAlert.setContentText("The book \"" + itemToModify.getTitle() + 
                                     "\" has been successfully deleted.");
                successAlert.showAndWait();

                // Navigate back to manage library view
                MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

            } catch (Exception e) {
                // Show error message if deletion fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to Delete Book");
                errorAlert.setContentText("An error occurred while deleting the book: " + 
                                    e.getMessage());
                errorAlert.showAndWait();
            }
        }
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
            populateCreatorFields();
            populateGenreFields();
            populateKeywordFields();
        }
    }

    /**
     * Populates the basic fields (title, publisher, ISBN) with item data
     */
    private void populateBasicFields() {
        bookTitleTextFieldLibrarian.setText(itemToModify.getTitle());
        bookPublisherTextFieldLibrarian.setText(itemToModify.getPublisher());
        
        if (itemToModify instanceof Book book) {
            bookIsbn13TextFieldLibrarian.setText(book.getISBN13());
            bookIsbn10TextFieldLibrarian.setText(book.getISBN10());
        }

        if (itemToModify.getLanguage() != null) {
            bookLanguageComboBoxLibrarian.setValue(itemToModify.getLanguage().getLanguage());
        }
    }

    /**
     * Populates the location fields with item data
     */
    private void populateLocationFields() {
        if (itemToModify.getLocation() != null) {
            bookFloorComboBoxLibrarian.setValue(itemToModify.getLocation().getFloor());
            bookSectionComboBoxLibrarian.setValue(itemToModify.getLocation().getSection());
            bookShelfComboBoxLibrarian.setValue(itemToModify.getLocation().getShelf());
            bookPositionComboBoxLibrarian.setValue(itemToModify.getLocation().getPosition());
        }
    }

    /**
     * Populates the creator/author fields with item data
     */
    private void populateCreatorFields() {
        if (itemToModify.getCreators() != null && !itemToModify.getCreators().isEmpty()) {
            List<Creator> creatorList = itemToModify.getCreators().stream().toList();
            if (creatorList.size() > 0) {
                bookAuthorComboBoxLibrarian1.setValue(creatorList.get(0).getFullName());
            }
            if (creatorList.size() > 1) {
                bookAuthorComboBoxLibrarian2.setValue(creatorList.get(1).getFullName());
            }
            if (creatorList.size() > 2) {
                bookAuthorComboBoxLibrarian3.setValue(creatorList.get(2).getFullName());
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
                bookGenreComboBoxLibrarian1.setValue(genreList.get(0).getGenre());
            }
            if (genreList.size() > 1) {
                bookGenreComboBoxLibrarian2.setValue(genreList.get(1).getGenre());
            }
            if (genreList.size() > 2) {
                bookGenreComboBoxLibrarian3.setValue(genreList.get(2).getGenre());
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
                bookKeywordComboBoxLibrarian1.setValue(keywordList.get(0).getKeyword());
            }
            if (keywordList.size() > 1) {
                bookKeywordComboBoxLibrarian2.setValue(keywordList.get(1).getKeyword());
            }
            if (keywordList.size() > 2) {
                bookKeywordComboBoxLibrarian3.setValue(keywordList.get(2).getKeyword());
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
        bookLanguageComboBoxLibrarian.setItems(languages);
        bookFloorComboBoxLibrarian.setItems(floors);
        bookSectionComboBoxLibrarian.setItems(sections);
        bookShelfComboBoxLibrarian.setItems(shelves);
        bookPositionComboBoxLibrarian.setItems(positions);

        // Populate author comboboxes
        bookAuthorComboBoxLibrarian1.setItems(authors);
        bookAuthorComboBoxLibrarian2.setItems(authors);
        bookAuthorComboBoxLibrarian3.setItems(authors);

        // Populate genre comboboxes
        bookGenreComboBoxLibrarian1.setItems(genres);
        bookGenreComboBoxLibrarian2.setItems(genres);
        bookGenreComboBoxLibrarian3.setItems(genres);

        // Populate keyword comboboxes
        bookKeywordComboBoxLibrarian1.setItems(keywords);
        bookKeywordComboBoxLibrarian2.setItems(keywords);
        bookKeywordComboBoxLibrarian3.setItems(keywords);
    }

    /**
     * Collects all genres selected in the genre combo boxes.
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new ArrayList<>();
        addIfNotEmpty(bookGenreComboBoxLibrarian1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(bookGenreComboBoxLibrarian2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(bookGenreComboBoxLibrarian3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all keywords selected in the keyword combo boxes.
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        addIfNotEmpty(bookKeywordComboBoxLibrarian1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(bookKeywordComboBoxLibrarian2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(bookKeywordComboBoxLibrarian3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     */
    private Language collectLanguage() {
        String languageName = bookLanguageComboBoxLibrarian.getValue();
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
        addIfNotEmpty(bookAuthorComboBoxLibrarian1.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(bookAuthorComboBoxLibrarian2.getValue(), authors, creatorManagementService::findByFullName);
        addIfNotEmpty(bookAuthorComboBoxLibrarian3.getValue(), authors, creatorManagementService::findByFullName);
        return authors;
    }

    /**
     * Collects the location from the location combo boxes.
     */
    private Location collectLocation() {
        String floor = bookFloorComboBoxLibrarian.getValue();
        String section = bookSectionComboBoxLibrarian.getValue();
        String shelf = bookShelfComboBoxLibrarian.getValue();
        String position = bookPositionComboBoxLibrarian.getValue();
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
