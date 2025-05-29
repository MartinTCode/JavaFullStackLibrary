package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
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
import com.javafullstacklibrary.services.*; // Importing all services at once
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        // Save logic here (validation, update DB, etc.)
        System.out.println("Save Changes button clicked");
        //Navigate back to Manage Library
    }

    @FXML
    private void clickedDeleteCourseLitButtonLibrarian(MouseEvent event) {
        // Logic to delete the course literature entry
        // This could involve confirmation dialog and then deletion from the database
        System.out.println("Delete Course Literature button clicked.");
    }

    /**
     * Initializes the controller after the root element has been completely processed.
     * Calls the method to populate combo boxes.
     */
    @FXML
    private void initialize() {
        populateComboBoxes();
        
        if (itemToModify != null) {
            // Populate fields with item data
            CourseLitTitleTextFieldLibrarian.setText(itemToModify.getTitle());
            CourseLitPublisherTextFieldLibrarian.setText(itemToModify.getPublisher());
            
            // Cast to CourseLiterature to access ISBN getters
            if (itemToModify instanceof CourseLitterature courseLit) {
                CourseLitIsbn13TextFieldLibrarian.setText(courseLit.getISBN13());
                CourseLitIsbn10TextFieldLibrarian.setText(courseLit.getISBN10());
            }

            // Set language if it exists
            if (itemToModify.getLanguage() != null) {
                CourseLitLanguageComboBoxLibrarian.setValue(itemToModify.getLanguage().getLanguage());
            }

            // Set location if it exists
            if (itemToModify.getLocation() != null) {
                CourseLitFloorComboBoxLibrarian.setValue(itemToModify.getLocation().getFloor());
                CourseLitSectionComboBoxLibrarian.setValue(itemToModify.getLocation().getSection());
                CourseLitShelfComboBoxLibrarian.setValue(itemToModify.getLocation().getShelf());
                CourseLitPositionComboBoxLibrarian.setValue(itemToModify.getLocation().getPosition());
            }

            // Set authors, genres, and keywords if they exist
            if (itemToModify.getCreators() != null && !itemToModify.getCreators().isEmpty()) {
                Set<Creator> creators = itemToModify.getCreators();
                List<Creator> creatorList = creators.stream().toList();
                if (creatorList.size() > 0) {
                    CourseLitAuthorComboBoxLibrarian1.setValue(creatorList.get(0).getFullName());
                }
                if (creatorList.size() > 1) {
                    CourseLitAuthorComboBoxLibrarian2.setValue(creatorList.get(1).getFullName());
                }
                if (creatorList.size() > 2) {
                    CourseLitAuthorComboBoxLibrarian2.setValue(creatorList.get(2).getFullName());
                }
            }

            if (itemToModify.getGenres() != null && !itemToModify.getGenres().isEmpty()) {
                Set<Genre> genres = itemToModify.getGenres();
                List<Genre> genreList = genres.stream().toList();
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

            if (itemToModify.getKeywords() != null && !itemToModify.getKeywords().isEmpty()) {
                Set<Keyword> keywords = itemToModify.getKeywords();
                List<Keyword> keywordList = keywords.stream().toList();
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
}
