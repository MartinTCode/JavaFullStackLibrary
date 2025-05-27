package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    @FXML private ComboBox<String> dvdActorComboBox4;
    @FXML private ComboBox<String> dvdDirectorComboBox1;
    @FXML private ComboBox<String> dvdDirectorComboBox2;
    @FXML private ComboBox<String> dvdDirectorComboBox3;
    @FXML private ComboBox<String> dvdGenreComboBox1;
    @FXML private ComboBox<String> dvdGenreComboBox2;
    @FXML private ComboBox<String> dvdGenreComboBox3;
    @FXML private ComboBox<String> dvdKeywordComboBox1;
    @FXML private ComboBox<String> dvdKeywordComboBox2;
    @FXML private ComboBox<String> dvdKeywordComboBox3;
    @FXML private ComboBox<String> dvdKeywordComboBox4;

    /**
     * This method is called when the controller is initialized.
     * It calls the applicable methods to initialize the view.
     */
    public void initialize() {
        populateComboBoxes();
    }

    /**
     * Populates the ComboBoxes with mock data.
     * #TODO Replace with actual data retrieval logic.
     */
    private void populateComboBoxes() {
        // Mock data for demonstration
        ObservableList<String> languages = FXCollections.observableArrayList("English", "Swedish", "German", "French");
        ObservableList<String> actors = FXCollections.observableArrayList("Actor A", "Actor B", "Actor C", "Actor D");
        ObservableList<String> directors = FXCollections.observableArrayList("Director X", "Director Y", "Director Z");
        ObservableList<String> genres = FXCollections.observableArrayList("Action", "Comedy", "Drama", "Documentary");
        ObservableList<String> keywords = FXCollections.observableArrayList("Keyword1", "Keyword2", "Keyword3", "Keyword4");

        dvdLanguageComboBoxLibrarian.setItems(languages);

        dvdActorComboBox1.setItems(actors);
        dvdActorComboBox2.setItems(actors);
        dvdActorComboBox3.setItems(actors);
        dvdActorComboBox4.setItems(actors);

        dvdDirectorComboBox1.setItems(directors);
        dvdDirectorComboBox2.setItems(directors);
        dvdDirectorComboBox3.setItems(directors);

        dvdGenreComboBox1.setItems(genres);
        dvdGenreComboBox2.setItems(genres);
        dvdGenreComboBox3.setItems(genres);

        dvdKeywordComboBox1.setItems(keywords);
        dvdKeywordComboBox2.setItems(keywords);
        dvdKeywordComboBox3.setItems(keywords);
        dvdKeywordComboBox4.setItems(keywords);
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
     * #TODO Replace with actual save logic to the database or service.
     */
    @FXML
    private void clickedSaveNewDvdButtonLibrarian(MouseEvent event) {
        // Read all input from textfields and comboboxes
        String title = dvdTitleTextFieldLibrarian.getText();
        String imdbc = dvdImdbcTextFieldLibrarian.getText();
        String publisher = dvdPublisherTextFieldLibrarian.getText();
        String country = dvdCountryTextFieldLibrarian.getText();
        String ageLimit = dvdAgeLimitTextFieldLibrarian.getText();

        String language = dvdLanguageComboBoxLibrarian.getValue();

        String actor1 = dvdActorComboBox1.getValue();
        String actor2 = dvdActorComboBox2.getValue();
        String actor3 = dvdActorComboBox3.getValue();
        String actor4 = dvdActorComboBox4.getValue();

        String director1 = dvdDirectorComboBox1.getValue();
        String director2 = dvdDirectorComboBox2.getValue();
        String director3 = dvdDirectorComboBox3.getValue();

        String genre1 = dvdGenreComboBox1.getValue();
        String genre2 = dvdGenreComboBox2.getValue();
        String genre3 = dvdGenreComboBox3.getValue();

        String keyword1 = dvdKeywordComboBox1.getValue();
        String keyword2 = dvdKeywordComboBox2.getValue();
        String keyword3 = dvdKeywordComboBox3.getValue();
        String keyword4 = dvdKeywordComboBox4.getValue();

        // Print all values to the console, switch this out to actual save logic later
        System.out.println("Save New DVD button clicked. Input values:");
        System.out.println("Title: " + title);
        System.out.println("IMDBC: " + imdbc);
        System.out.println("Publisher: " + publisher);
        System.out.println("Country: " + country);
        System.out.println("Age Limit: " + ageLimit);
        System.out.println("Language: " + language);
        System.out.println("Actor 1: " + actor1);
        System.out.println("Actor 2: " + actor2);
        System.out.println("Actor 3: " + actor3);
        System.out.println("Actor 4: " + actor4);
        System.out.println("Director 1: " + director1);
        System.out.println("Director 2: " + director2);
        System.out.println("Director 3: " + director3);
        System.out.println("Genre 1: " + genre1);
        System.out.println("Genre 2: " + genre2);
        System.out.println("Genre 3: " + genre3);
        System.out.println("Keyword 1: " + keyword1);
        System.out.println("Keyword 2: " + keyword2);
        System.out.println("Keyword 3: " + keyword3);
        System.out.println("Keyword 4: " + keyword4);
    }
}
