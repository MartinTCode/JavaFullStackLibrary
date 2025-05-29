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
import com.javafullstacklibrary.services.*; // Importing all services at once
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        // Save logic here (validation, update DB, etc.)
        System.out.println("Save Changes button clicked");
        // Navigate back to Manage Library
    }

    /**
     * Handles click on the Delete Book button.
     * @param event
     */
    @FXML
    private void clickedDeleteBookButtonLibrarian(MouseEvent event) {
        // Logic to delete the book (confirmation dialog, delete from DB, etc.)
        System.out.println("Delete Book button clicked");
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
}
