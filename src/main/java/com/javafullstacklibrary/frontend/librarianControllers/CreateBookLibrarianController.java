package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CreateBookLibrarianController {

    @FXML
    private Pane mainPane;

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

    // Save button handler (stub, implement saving logic as needed)
    @FXML
    private void clickedSaveNewBookButtonLibrarian(MouseEvent event) {
        // Implement saving logic here
        // For now, just print a message to the console
        System.out.println("Save New Book button clicked. Implement saving logic here."); 
    }
}
