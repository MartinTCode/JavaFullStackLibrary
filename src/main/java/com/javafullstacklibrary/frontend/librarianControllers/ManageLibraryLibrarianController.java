package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class ManageLibraryLibrarianController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private ComboBox<String> itemTypeComboBox;

    @FXML
    private TextField modifyItemSearchFieldLibrarian;

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

    @FXML
    private void initialize() {
        // Populate itemTypeComboBox with item types
        itemTypeComboBox.getItems().addAll(
            "Book",
            "Course Litterature",
            "Journal",
            "DVD"
        );
    }

    // --- Button Handlers ---

    @FXML
    private void continueNewItemButtonClicked(MouseEvent event) {
        // Implement logic to continue adding a new item based on selected type
        String selectedType = itemTypeComboBox.getValue();
        // Navigate to the appropriate create item view
        if (selectedType == null) {
            System.out.println("No item type selected.");
            return;
        }
        
        switch (selectedType) {
            case "Book":
                MenuNavigationHelper.buttonClickLibrarian(mainPane, "CreateBook");
                break;
            case "Course Litterature":
                MenuNavigationHelper.buttonClickLibrarian(mainPane, "CreateCourseLit");
                break;
            case "Journal":
                MenuNavigationHelper.buttonClickLibrarian(mainPane, "CreateJournal");
                break;
            case "DVD":
                MenuNavigationHelper.buttonClickLibrarian(mainPane, "CreateDvd");
                break;
            default:
                System.out.println("Unknown item type selected: " + selectedType);
                break;
        }
    }

    @FXML
    private void continueModifyItemButtonClicked(MouseEvent event) {
        // Implement logic to search and modify item
        String query = modifyItemSearchFieldLibrarian.getText();
        // Example: Print or handle search
        System.out.println("Modify item search: " + query);
        // Naviagate to modify item view
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SearchView", query);
    }
}
