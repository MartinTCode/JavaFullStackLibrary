package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    @FXML private ComboBox<String> bookKeywordComboBoxLibrarian4;

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
     * #TODO: Replace with actual save logic when database integration is implemented.
     * @param event the mouse event that triggered this method
     */
    @FXML
    private void clickedSaveNewBookButtonLibrarian(MouseEvent event) {
        // Read all input from textfields and comboboxes
        String title = bookTitleTextFieldLibrarian.getText();
        String isbn13 = bookIsbn13TextFieldLibrarian.getText();
        String isbn10 = bookIsbn10TextFieldLibrarian.getText();
        String publisher = bookPublisherTextFieldLibrarian.getText();

        String language = bookLanguageComboBoxLibrarian.getValue();
        String floor = bookFloorComboBoxLibrarian.getValue();
        String section = bookSectionComboBoxLibrarian.getValue();
        String shelf = bookShelfComboBoxLibrarian.getValue();
        String position = bookPositionComboBoxLibrarian.getValue();

        String author1 = bookAuthorComboBoxLibrarian1.getValue();
        String author2 = bookAuthorComboBoxLibrarian2.getValue();
        String author3 = bookAuthorComboBoxLibrarian3.getValue();

        String genre1 = bookGenreComboBoxLibrarian1.getValue();
        String genre2 = bookGenreComboBoxLibrarian2.getValue();
        String genre3 = bookGenreComboBoxLibrarian3.getValue();

        String keyword1 = bookKeywordComboBoxLibrarian1.getValue();
        String keyword2 = bookKeywordComboBoxLibrarian2.getValue();
        String keyword3 = bookKeywordComboBoxLibrarian3.getValue();
        String keyword4 = bookKeywordComboBoxLibrarian4.getValue();

        // Print all values to the console
        System.out.println("Save New Book button clicked. Input values:");
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

        // Add new inputs to ComboBoxes if they are not already in the list
        addNewInputToComboBox(bookLanguageComboBoxLibrarian);
        addNewInputToComboBox(bookFloorComboBoxLibrarian);
        addNewInputToComboBox(bookSectionComboBoxLibrarian);
        addNewInputToComboBox(bookShelfComboBoxLibrarian);
        addNewInputToComboBox(bookPositionComboBoxLibrarian);
        addNewInputToComboBox(bookAuthorComboBoxLibrarian1);
        addNewInputToComboBox(bookAuthorComboBoxLibrarian2);
        addNewInputToComboBox(bookAuthorComboBoxLibrarian3);
        addNewInputToComboBox(bookGenreComboBoxLibrarian1);
        addNewInputToComboBox(bookGenreComboBoxLibrarian2);
        addNewInputToComboBox(bookGenreComboBoxLibrarian3);
        addNewInputToComboBox(bookKeywordComboBoxLibrarian1);
        addNewInputToComboBox(bookKeywordComboBoxLibrarian2);
        addNewInputToComboBox(bookKeywordComboBoxLibrarian3);
        addNewInputToComboBox(bookKeywordComboBoxLibrarian4);
    }

    /**
     * Populates the combo boxes with mock data.
     * This method is called during the initialization of the controller.
     * #TODO: Replace mock data with actual data from the database when implemented.
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
        bookKeywordComboBoxLibrarian4.setItems(keywords);
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
