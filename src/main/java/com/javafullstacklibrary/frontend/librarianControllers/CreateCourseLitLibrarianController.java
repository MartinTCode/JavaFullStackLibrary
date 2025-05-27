package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    @FXML private ComboBox<String> CourseLitKeywordComboBoxLibrarian4;

    /**
     * This method is called when the controller is initialized.
     * It calls the applicable methods to initialize the view.
     */
    @FXML
    public void initialize() {
        populateComboBoxes();
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
    private void clickedCancelNewCourseLitButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    /**
     * This method is called when the "Save New Course Literature" button is clicked.
     * It reads all input from the text fields and combo boxes, and prints them to the console.
     * #TODO Change this to save the course literature to the database.
     */
    @FXML
    private void clickedSaveNewCourseLitButtonLibrarian(MouseEvent event) {
        // Read all input from textfields and comboboxes
        String title = CourseLitTitleTextFieldLibrarian.getText();
        String isbn13 = CourseLitIsbn13TextFieldLibrarian.getText();
        String isbn10 = CourseLitIsbn10TextFieldLibrarian.getText();
        String publisher = CourseLitPublisherTextFieldLibrarian.getText();

        String language = CourseLitLanguageComboBoxLibrarian.getValue();
        String floor = CourseLitFloorComboBoxLibrarian.getValue();
        String section = CourseLitSectionComboBoxLibrarian.getValue();
        String shelf = CourseLitShelfComboBoxLibrarian.getValue();
        String position = CourseLitPositionComboBoxLibrarian.getValue();

        String author1 = CourseLitAuthorComboBoxLibrarian1.getValue();
        String author2 = CourseLitAuthorComboBoxLibrarian2.getValue();
        String author3 = CourseLitAuthorComboBoxLibrarian3.getValue();

        String genre1 = CourseLitGenreComboBoxLibrarian1.getValue();
        String genre2 = CourseLitGenreComboBoxLibrarian2.getValue();
        String genre3 = CourseLitGenreComboBoxLibrarian3.getValue();

        String keyword1 = CourseLitKeywordComboBoxLibrarian1.getValue();
        String keyword2 = CourseLitKeywordComboBoxLibrarian2.getValue();
        String keyword3 = CourseLitKeywordComboBoxLibrarian3.getValue();
        String keyword4 = CourseLitKeywordComboBoxLibrarian4.getValue();

        // Print all values to the console
        System.out.println("Save New Course Literature button clicked. Input values:");
        System.out.println("Title: " + title);
        System.out.println("ISBN13: " + isbn13);
        System.out.println("ISBN10: " + isbn10);
        System.out.println("Publisher: " + publisher);
        System.out.println("Language: " + language);
        System.out.println("Floor: " + floor);
        System.out.println("Section: " + section);
        System.out.println("Shelf: " + shelf);
        System.out.println("Position: " + position);
        System.out.println("Author 1: " + author1);
        System.out.println("Author 2: " + author2);
        System.out.println("Author 3: " + author3);
        System.out.println("Genre 1: " + genre1);
        System.out.println("Genre 2: " + genre2);
        System.out.println("Genre 3: " + genre3);
        System.out.println("Keyword 1: " + keyword1);
        System.out.println("Keyword 2: " + keyword2);
        System.out.println("Keyword 3: " + keyword3);
        System.out.println("Keyword 4: " + keyword4);

        addNewInputToComboBox(CourseLitLanguageComboBoxLibrarian);
        addNewInputToComboBox(CourseLitFloorComboBoxLibrarian);
        addNewInputToComboBox(CourseLitSectionComboBoxLibrarian);
        addNewInputToComboBox(CourseLitShelfComboBoxLibrarian);
        addNewInputToComboBox(CourseLitPositionComboBoxLibrarian);
        addNewInputToComboBox(CourseLitAuthorComboBoxLibrarian1);
        addNewInputToComboBox(CourseLitAuthorComboBoxLibrarian2);
        addNewInputToComboBox(CourseLitAuthorComboBoxLibrarian3);
        addNewInputToComboBox(CourseLitGenreComboBoxLibrarian1);
        addNewInputToComboBox(CourseLitGenreComboBoxLibrarian2);
        addNewInputToComboBox(CourseLitGenreComboBoxLibrarian3);
        addNewInputToComboBox(CourseLitKeywordComboBoxLibrarian1);
        addNewInputToComboBox(CourseLitKeywordComboBoxLibrarian2);
        addNewInputToComboBox(CourseLitKeywordComboBoxLibrarian3);
        addNewInputToComboBox(CourseLitKeywordComboBoxLibrarian4);
    }

    /**
     * Populates the combo boxes with mock data.
     * This method is called during the initialization of the controller.
     * #TODO Change this to fetch actual data from the database when implemented
     */
    private void populateComboBoxes() {
        // Mock data for demonstration
        ObservableList<String> languages = FXCollections.observableArrayList("English", "Swedish", "German", "French");
        ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "4");
        ObservableList<String> sections = FXCollections.observableArrayList("A", "B", "C", "D");
        ObservableList<String> shelves = FXCollections.observableArrayList("Shelf 1", "Shelf 2", "Shelf 3");
        ObservableList<String> positions = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        ObservableList<String> authors = FXCollections.observableArrayList("Author A", "Author B", "Author C");
        ObservableList<String> genres = FXCollections.observableArrayList("Fiction", "Non-fiction", "Science", "History");
        ObservableList<String> keywords = FXCollections.observableArrayList("Keyword1", "Keyword2", "Keyword3", "Keyword4");

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
        CourseLitKeywordComboBoxLibrarian4.setItems(keywords);
    }

    /**
     * Adds the value from the ComboBox to its items if it is a new user input.
     * @param comboBox The ComboBox to check and add to.
     * #TODO: Change this to save the new input to the database when implemented.
     */
    private void addNewInputToComboBox(ComboBox<String> comboBox) {
        String value = comboBox.getValue();
        if (value != null && !value.isEmpty() && !comboBox.getItems().contains(value)) {
            comboBox.getItems().add(value);
            // Print for now, change to actual save logic later
            System.out.println("Added new value to ComboBox: " + value);
        }
    }

    // Example usage (call this after reading input, for each editable ComboBox):
    // addNewInputToComboBox(CourseLitLanguageComboBoxLibrarian);
    // addNewInputToComboBox(CourseLitAuthorComboBoxLibrarian1);
    // addNewInputToComboBox(CourseLitGenreComboBoxLibrarian1);
    // ...etc.
}
