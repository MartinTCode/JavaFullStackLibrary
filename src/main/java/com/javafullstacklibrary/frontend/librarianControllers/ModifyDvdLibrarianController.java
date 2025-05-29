package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.model.Actor;
import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.DVD;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.services.*; // Importing all services

/**
 * Controller for the Modify DVD view for librarians.
 */
public class ModifyDvdLibrarianController {
    private final Item itemToModify;

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

    // Constructor to initialize the controller with the item to modify
    public ModifyDvdLibrarianController(Item item) {
        this.itemToModify = item;
    }

    // Empty constructor for FXML loading
    public ModifyDvdLibrarianController() {
        this.itemToModify = null; // No item to modify, used for FXML loading
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
    private void clickedCancelChangeDvdButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handles click on the Save Changes button.
     * Saves the changes and navigates back to the Manage Library view.
     */
    @FXML
    private void clickedSaveChangesDvdButtonLibrarian(MouseEvent event) {
        try {
            // Get text field values
            String title = dvdTitleTextFieldLibrarian.getText();
            String imdbc = dvdImdbcTextFieldLibrarian.getText();
            String publisher = dvdPublisherTextFieldLibrarian.getText();
            String countryOfProduction = dvdCountryTextFieldLibrarian.getText();
            Short ageLimit = dvdAgeLimitTextFieldLibrarian.getText().isEmpty() ? null : Short.valueOf(dvdAgeLimitTextFieldLibrarian.getText());
            
            // Convert Lists to Sets
            Set<Creator> directors = new HashSet<>(collectDirectors());
            Set<Genre> genres = new HashSet<>(collectGenres());
            Set<Keyword> keywords = new HashSet<>(collectKeywords()); 
            Set<Actor> actors = new HashSet<>(collectActors());    
            Language language = collectLanguage();
            Location location = collectLocation();       

            // Update existing DVD instead of creating new one
            if (itemToModify != null) {
                // Update the existing item's fields
                itemToModify.setTitle(title);
                itemToModify.setPublisher(publisher);
                itemToModify.setLocation(location);
                itemToModify.setLanguage(language);
                itemToModify.setKeywords(keywords);
                itemToModify.setCreators(directors);
                itemToModify.setGenres(genres);
                itemToModify.setActors(actors);

                // If it's a DVD, update specific fields
                if (itemToModify instanceof DVD dvd) {
                    dvd.setIMDBC(imdbc);
                    dvd.setAgeLimit(ageLimit);
                    dvd.setCountryOfProduction(countryOfProduction);
                }

                // Update item in database
                itemManagementService.updateItem(itemToModify);
            } else {
                // Create new DVD if itemToModify is null
                Item newDvd = itemManagementService.createItem(
                    "dvd",
                    location,
                    language,
                    keywords,
                    directors,
                    actors,
                    genres,
                    imdbc,
                    null, // identifier2 not applicable for DVDs
                    title,
                    publisher,
                    ageLimit,
                    countryOfProduction
                );
                // Save new item in database
                itemManagementService.addItem(newDvd);
            }

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(itemToModify != null ? "DVD Updated" : "DVD Created");
            alert.setContentText("The DVD \"" + title + "\" has been successfully " + 
                               (itemToModify != null ? "updated" : "created") + ".");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(itemToModify != null ? "Failed to Update DVD" : "Failed to Create DVD");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML 
    private void clickedDeleteDvdButtonLibrarian(MouseEvent event) {
        if (itemToModify == null) {
            // Can't delete an item that doesn't exist yet
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Delete");
            alert.setHeaderText("No DVD Selected");
            alert.setContentText("Cannot delete a DVD that hasn't been saved yet.");
            alert.showAndWait();
            return;
        }

        // Show confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Delete DVD");
        confirmDialog.setContentText("Are you sure you want to delete the DVD \"" + 
                           itemToModify.getTitle() + "\"? This action cannot be undone.");

        // Get user's response, proceed if OK is clicked
        if (confirmDialog.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                // User clicked OK, proceed with deletion
                itemManagementService.deleteItem(itemToModify);

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("DVD Deleted");
                successAlert.setContentText("The DVD \"" + itemToModify.getTitle() + 
                                     "\" has been successfully deleted.");
                successAlert.showAndWait();

                // Navigate back to manage library view
                MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

            } catch (Exception e) {
                // Show error message if deletion fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to Delete DVD");
                errorAlert.setContentText("An error occurred while deleting the DVD: " + 
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
            populateGenres();
            populateKeywords();
            populateActors();
            populateDirectors();
        }
    }

    /**
     * Populates the basic DVD information fields
     */
    private void populateBasicFields() {
        dvdTitleTextFieldLibrarian.setText(itemToModify.getTitle());
        dvdPublisherTextFieldLibrarian.setText(itemToModify.getPublisher());
        
        if (itemToModify instanceof DVD dvd) {
            dvdImdbcTextFieldLibrarian.setText(dvd.getIMDBC());
            dvdCountryTextFieldLibrarian.setText(dvd.getCountryOfProduction());
            if (dvd.getAgeLimit() != null) {
                dvdAgeLimitTextFieldLibrarian.setText(dvd.getAgeLimit().toString());
            }
        }

        if (itemToModify.getLanguage() != null) {
            dvdLanguageComboBoxLibrarian.setValue(itemToModify.getLanguage().getLanguage());
        }
    }

    /**
     * Populates the location fields
     */
    private void populateLocationFields() {
        if (itemToModify.getLocation() != null) {
            dvdFloorComboBoxLibrarian.setValue(itemToModify.getLocation().getFloor());
            dvdSectionComboBoxLibrarian.setValue(itemToModify.getLocation().getSection());
            dvdShelfComboBoxLibrarian.setValue(itemToModify.getLocation().getShelf());
            dvdPositionComboBoxLibrarian.setValue(itemToModify.getLocation().getPosition());
        }
    }

    /**
     * Populates the genre combo boxes
     */
    private void populateGenres() {
        if (itemToModify.getGenres() != null && !itemToModify.getGenres().isEmpty()) {
            List<Genre> genreList = itemToModify.getGenres().stream().toList();
            if (genreList.size() > 0) dvdGenreComboBox1.setValue(genreList.get(0).getGenre());
            if (genreList.size() > 1) dvdGenreComboBox2.setValue(genreList.get(1).getGenre());
            if (genreList.size() > 2) dvdGenreComboBox3.setValue(genreList.get(2).getGenre());
        }
    }

    /**
     * Populates the keyword combo boxes
     */
    private void populateKeywords() {
        if (itemToModify.getKeywords() != null && !itemToModify.getKeywords().isEmpty()) {
            List<Keyword> keywordList = itemToModify.getKeywords().stream().toList();
            if (keywordList.size() > 0) dvdKeywordComboBox1.setValue(keywordList.get(0).getKeyword());
            if (keywordList.size() > 1) dvdKeywordComboBox2.setValue(keywordList.get(1).getKeyword());
            if (keywordList.size() > 2) dvdKeywordComboBox3.setValue(keywordList.get(2).getKeyword());
        }
    }

    /**
     * Populates the actor combo boxes
     */
    private void populateActors() {
        if (itemToModify.getActors() != null && !itemToModify.getActors().isEmpty()) {
            List<Actor> actorList = itemToModify.getActors().stream().toList();
            if (actorList.size() > 0) dvdActorComboBox1.setValue(actorList.get(0).getFullName());
            if (actorList.size() > 1) dvdActorComboBox2.setValue(actorList.get(1).getFullName());
            if (actorList.size() > 2) dvdActorComboBox3.setValue(actorList.get(2).getFullName());
        }
    }

    /**
     * Populates the director combo boxes
     */
    private void populateDirectors() {
        if (itemToModify.getCreators() != null && !itemToModify.getCreators().isEmpty()) {
            List<Creator> directorList = itemToModify.getCreators().stream().toList();
            if (directorList.size() > 0) dvdDirectorComboBox1.setValue(directorList.get(0).getFullName());
            if (directorList.size() > 1) dvdDirectorComboBox2.setValue(directorList.get(1).getFullName());
            if (directorList.size() > 2) dvdDirectorComboBox3.setValue(directorList.get(2).getFullName());
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

    /**
     * Collects all genres selected in the genre combo boxes.
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new ArrayList<>();
        addIfNotEmpty(dvdGenreComboBox1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(dvdGenreComboBox2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(dvdGenreComboBox3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all keywords selected in the keyword combo boxes.
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        addIfNotEmpty(dvdKeywordComboBox1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(dvdKeywordComboBox2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(dvdKeywordComboBox3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the language selected in the language combo box.
     */
    private Language collectLanguage() {
        String languageName = dvdLanguageComboBoxLibrarian.getValue();
        if (languageName != null && !languageName.isEmpty()) {
            return languageManagementService.findByName(languageName);
        }
        throw new IllegalArgumentException("Language cannot be null or empty");
    }

    /**
     * Collects all directors selected in the director combo boxes.
     */
    private List<Creator> collectDirectors() {
        List<Creator> directors = new ArrayList<>();
        addIfNotEmpty(dvdDirectorComboBox1.getValue(), directors, creatorManagementService::findByFullName);
        addIfNotEmpty(dvdDirectorComboBox2.getValue(), directors, creatorManagementService::findByFullName);
        addIfNotEmpty(dvdDirectorComboBox3.getValue(), directors, creatorManagementService::findByFullName);
        return directors;
    }

    /**
     * Collects all actors selected in the actor combo boxes.
     */
    private List<Actor> collectActors() {
        List<Actor> actors = new ArrayList<>();
        addIfNotEmpty(dvdActorComboBox1.getValue(), actors, actorManagementService::findByFullName);
        addIfNotEmpty(dvdActorComboBox2.getValue(), actors, actorManagementService::findByFullName);
        addIfNotEmpty(dvdActorComboBox3.getValue(), actors, actorManagementService::findByFullName);
        return actors;
    }

    /**
     * Collects the location from the location combo boxes.
     */
    private Location collectLocation() {
        String floor = dvdFloorComboBoxLibrarian.getValue();
        String section = dvdSectionComboBoxLibrarian.getValue();
        String shelf = dvdShelfComboBoxLibrarian.getValue();
        String position = dvdPositionComboBoxLibrarian.getValue();
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
