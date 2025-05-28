package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.ObservableList;
import com.javafullstacklibrary.services.ItemManagementService;
import com.javafullstacklibrary.services.GenreManagementService;
import com.javafullstacklibrary.services.CreatorManagementService;
import com.javafullstacklibrary.services.KeywordManagementService;
import com.javafullstacklibrary.services.LanguageManagementService;
import com.javafullstacklibrary.services.LocationManagementService;
import com.javafullstacklibrary.services.ActorManagementService;
import com.javafullstacklibrary.model.Actor;
import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javafx.scene.control.Alert;

public class CreateDvdLibrarianController {

    @FXML
    private BorderPane mainPane;

    // TextFields for form fields
    @FXML private TextField dvdTitleTextFieldLibrarian;
    @FXML private TextField dvdImdbcTextFieldLibrarian;
    @FXML private TextField dvdPublisherTextFieldLibrarian;
    @FXML private TextField dvdCountryTextFieldLibrarian;
    @FXML private TextField dvdAgeLimitTextFieldLibrarian;

    // ComboBoxes for form fields
    @FXML private ComboBox<String> dvdLanguageComboBoxLibrarian;
    @FXML private ComboBox<String> dvdActorComboBox1;
    @FXML private ComboBox<String> dvdActorComboBox2;
    @FXML private ComboBox<String> dvdActorComboBox3;
    @FXML private ComboBox<String> dvdDirectorComboBox1;
    @FXML private ComboBox<String> dvdDirectorComboBox2;
    @FXML private ComboBox<String> dvdDirectorComboBox3;
    @FXML private ComboBox<String> dvdGenreComboBox1;
    @FXML private ComboBox<String> dvdGenreComboBox2;
    @FXML private ComboBox<String> dvdGenreComboBox3;
    @FXML private ComboBox<String> dvdKeywordComboBox1;
    @FXML private ComboBox<String> dvdKeywordComboBox2;
    @FXML private ComboBox<String> dvdKeywordComboBox3;
    @FXML private ComboBox<String> dvdFloorComboBoxLibrarian;
    @FXML private ComboBox<String> dvdSectionComboBoxLibrarian;
    @FXML private ComboBox<String> dvdShelfComboBoxLibrarian;
    @FXML private ComboBox<String> dvdPositionComboBoxLibrarian;

    // Services for managing data
    private final ItemManagementService itemManagementService = new ItemManagementService();
    private final GenreManagementService genreManagementService = new GenreManagementService();
    private final CreatorManagementService creatorManagementService = new CreatorManagementService();
    private final KeywordManagementService keywordManagementService = new KeywordManagementService();
    private final LanguageManagementService languageManagementService = new LanguageManagementService();
    private final LocationManagementService locationManagementService = new LocationManagementService();
    private final ActorManagementService actorManagementService = new ActorManagementService();

    /**
     * This method is called when the controller is initialized.
     * It calls the applicable methods to initialize the view.
     */
    public void initialize() {
        populateComboBoxes();
    }

    /**
     * Populates the combo boxes with data from the database.
     * This method is called during initialization of the controller.
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

        dvdLanguageComboBoxLibrarian.setItems(languages);
        dvdFloorComboBoxLibrarian.setItems(floors);
        dvdSectionComboBoxLibrarian.setItems(sections);
        dvdShelfComboBoxLibrarian.setItems(shelves);
        dvdPositionComboBoxLibrarian.setItems(positions);

        dvdActorComboBox1.setItems(actors);
        dvdActorComboBox2.setItems(actors);
        dvdActorComboBox3.setItems(actors);

        dvdDirectorComboBox1.setItems(directors);
        dvdDirectorComboBox2.setItems(directors);
        dvdDirectorComboBox3.setItems(directors);

        dvdGenreComboBox1.setItems(genres);
        dvdGenreComboBox2.setItems(genres);
        dvdGenreComboBox3.setItems(genres);

        dvdKeywordComboBox1.setItems(keywords);
        dvdKeywordComboBox2.setItems(keywords);
        dvdKeywordComboBox3.setItems(keywords);
    }

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

    // --- Form Button Handlers ---

    @FXML
    private void clickedCancelNewDvdButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Method to handle the click event of the "Save New DVD" button.
     * This method reads all input from text fields and combo boxes,
     * and for now prints them to the console.
     */
    @FXML
    private void clickedSaveNewDvdButtonLibrarian(MouseEvent event) {
        try {
            // Get text field values
            String title = dvdTitleTextFieldLibrarian.getText();
            String imdbc = dvdImdbcTextFieldLibrarian.getText();
            String publisher = dvdPublisherTextFieldLibrarian.getText();
            String country = dvdCountryTextFieldLibrarian.getText();
            Short ageLimit = Short.parseShort(dvdAgeLimitTextFieldLibrarian.getText());
            
            // Convert Lists to Sets
            Set<Creator> directors = new HashSet<>(collectDirectors());
            Set<Actor> actors = new HashSet<>(collectActors());
            Set<Genre> genres = new HashSet<>(collectGenres());
            Set<Keyword> keywords = new HashSet<>(collectKeywords());     
            Language language = collectLanguage();
            Location location = collectLocation();       

            // Create a new DVD and save it to database
            itemManagementService.createAndSaveItem(
                "dvd",
                location,
                language,
                keywords,
                directors,
                actors,
                genres,
                imdbc, // identifier1
                null,  // identifier2 not applicable for DVD
                title,
                publisher,
                ageLimit,
                country
            );

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("DVD Created");
            alert.setContentText("The DVD \"" + title + "\" has been successfully created and saved.");
            alert.showAndWait();

            // Navigate back to manage library view
            MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");

        } catch (Exception e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Create DVD");
            alert.setContentText("An error occurred while creating the DVD: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Collects all selected genres from the genre combo boxes.
     * @return List of Genre objects corresponding to the selected values
     */
    private List<Genre> collectGenres() {
        List<Genre> genres = new java.util.ArrayList<>();
        addIfNotEmpty(dvdGenreComboBox1.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(dvdGenreComboBox2.getValue(), genres, genreManagementService::findByName);
        addIfNotEmpty(dvdGenreComboBox3.getValue(), genres, genreManagementService::findByName);
        return genres;
    }

    /**
     * Collects all selected keywords from the keyword combo boxes.
     * @return List of Keyword objects corresponding to the selected values
     */
    private List<Keyword> collectKeywords() {
        List<Keyword> keywords = new java.util.ArrayList<>();
        addIfNotEmpty(dvdKeywordComboBox1.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(dvdKeywordComboBox2.getValue(), keywords, keywordManagementService::findByName);
        addIfNotEmpty(dvdKeywordComboBox3.getValue(), keywords, keywordManagementService::findByName);
        return keywords;
    }

    /**
     * Collects the selected language from the language combo box.
     * @return Language object corresponding to the selected value
     * @throws IllegalArgumentException if no language is selected
     */
    private Language collectLanguage() {
        String languageName = dvdLanguageComboBoxLibrarian.getValue();
        if (languageName != null && !languageName.isEmpty()) {
            return languageManagementService.findByName(languageName);
        }
        else {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
    }

    /**
     * Collects all selected directors from the director combo boxes.
     * @return List of Creator objects corresponding to the selected values
     */
    private List<Creator> collectDirectors() {
        List<Creator> directors = new java.util.ArrayList<>();
        addIfNotEmpty(dvdDirectorComboBox1.getValue(), directors, creatorManagementService::findByFullName);
        addIfNotEmpty(dvdDirectorComboBox2.getValue(), directors, creatorManagementService::findByFullName);
        addIfNotEmpty(dvdDirectorComboBox3.getValue(), directors, creatorManagementService::findByFullName);
        return directors;
    }

    /**
     * Collects all selected actors from the actor combo boxes.
     * @return List of Actor objects corresponding to the selected values
     */
    private List<Actor> collectActors() {
        List<Actor> actors = new java.util.ArrayList<>();
        addIfNotEmpty(dvdActorComboBox1.getValue(), actors, actorManagementService::findByFullName);
        addIfNotEmpty(dvdActorComboBox2.getValue(), actors, actorManagementService::findByFullName);
        addIfNotEmpty(dvdActorComboBox3.getValue(), actors, actorManagementService::findByFullName);
        return actors;
    }

    /**
     * Collects the selected location details from all location combo boxes.
     * Creates a new location if the combination doesn't exist.
     * @return Location object representing the selected floor, section, shelf, and position
     */
    private Location collectLocation() {
        String floor = dvdFloorComboBoxLibrarian.getValue();
        String section = dvdSectionComboBoxLibrarian.getValue();
        String shelf = dvdShelfComboBoxLibrarian.getValue();
        String position = dvdPositionComboBoxLibrarian.getValue();

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
