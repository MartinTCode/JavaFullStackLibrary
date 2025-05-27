package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateJournalLibrarianController {

    @FXML
    private BorderPane mainPane;

    // TextFields for form fields
    @FXML private TextField journalTitleTextFieldLibrarian;
    @FXML private TextField journalIssnTextFieldLibrarian;
    @FXML private TextField journalPublisherTextFieldLibrarian;

    // ComboBoxes for form fields
    @FXML private ComboBox<String> journalLanguageComboBoxLibrarian;
    @FXML private ComboBox<String> journalFloorComboBoxLibrarian;
    @FXML private ComboBox<String> journalSectionComboBoxLibrarian;
    @FXML private ComboBox<String> journalShelfComboBoxLibrarian;
    @FXML private ComboBox<String> journalPositionComboBoxLibrarian;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian1;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian2;
    @FXML private ComboBox<String> journalKeywordComboBoxLibrarian3;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian1;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian2;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian3;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian4;
    @FXML private ComboBox<String> journalAuthorComboBoxLibrarian5;


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
    private void clickedCancelNewJournalButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * Handler for the "Save New Journal" button.
     * Reads input from the form fields and currently prints them to the console.
     * #TODO Replace with actual save logic later.
     * @param event
     */
    @FXML
    private void clickedSaveNewJournalButtonLibrarian(MouseEvent event) {
        // Read all input from textfields and comboboxes
        String title = journalTitleTextFieldLibrarian.getText();
        String issn = journalIssnTextFieldLibrarian.getText();
        String publisher = journalPublisherTextFieldLibrarian.getText();

        String language = journalLanguageComboBoxLibrarian.getValue();
        String floor = journalFloorComboBoxLibrarian.getValue();
        String section = journalSectionComboBoxLibrarian.getValue();
        String shelf = journalShelfComboBoxLibrarian.getValue();
        String position = journalPositionComboBoxLibrarian.getValue();

        String keyword1 = journalKeywordComboBoxLibrarian1.getValue();
        String keyword2 = journalKeywordComboBoxLibrarian2.getValue();
        String keyword3 = journalKeywordComboBoxLibrarian3.getValue();

        String author1 = journalAuthorComboBoxLibrarian1.getValue();
        String author2 = journalAuthorComboBoxLibrarian2.getValue();
        String author3 = journalAuthorComboBoxLibrarian3.getValue();
        String author4 = journalAuthorComboBoxLibrarian4.getValue();
        String author5 = journalAuthorComboBoxLibrarian5.getValue();

        // Print all values to the console, changed to actual save logic later
        System.out.println("Save New Journal button clicked. Input values:");
        System.out.println("Title: " + title);
        System.out.println("ISSN: " + issn);
        System.out.println("Publisher: " + publisher);
        System.out.println("Language: " + language);
        System.out.println("Floor: " + floor);
        System.out.println("Section: " + section);
        System.out.println("Shelf: " + shelf);
        System.out.println("Position: " + position);
        System.out.println("Keyword 1: " + keyword1);
        System.out.println("Keyword 2: " + keyword2);
        System.out.println("Keyword 3: " + keyword3);
        System.out.println("Author 1: " + author1);
        System.out.println("Author 2: " + author2);
        System.out.println("Author 3: " + author3);
        System.out.println("Author 4: " + author4);
        System.out.println("Author 5: " + author5);
    }

    /**
     * Initializes the controller and calls applicable methods
     */
    @FXML
    public void initialize() {
        populateComboBoxes();
    }

    /**
     * Populates the combo boxes with mock data.
     * This method is called during the initialization of the controller.
     * #TODO Change this to fetch actual data from the database when implemented
     */
    private void populateComboBoxes() {
        // Mock data for demonstration, change to fetch actual data later
        ObservableList<String> languages = FXCollections.observableArrayList("English", "Swedish", "German", "French");
        ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "4");
        ObservableList<String> sections = FXCollections.observableArrayList("A", "B", "C", "D");
        ObservableList<String> shelves = FXCollections.observableArrayList("Shelf 1", "Shelf 2", "Shelf 3");
        ObservableList<String> positions = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        ObservableList<String> keywords = FXCollections.observableArrayList("Keyword1", "Keyword2", "Keyword3", "Keyword4");
        ObservableList<String> authors = FXCollections.observableArrayList("Author A", "Author B", "Author C", "Author D", "Author E");

        // Set the items for each ComboBox
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
        journalAuthorComboBoxLibrarian4.setItems(authors);
        journalAuthorComboBoxLibrarian5.setItems(authors);
    }
}
