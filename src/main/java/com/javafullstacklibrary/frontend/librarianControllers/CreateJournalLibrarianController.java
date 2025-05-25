package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class CreateJournalLibrarianController {

    @FXML
    private BorderPane mainPane;

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
    private void clickedCancelNewBookButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedSaveNewBookButtonLibrarian(MouseEvent event) {
        // Logic to save the new journal entry
        System.out.println("Clicked Save New Journal Button");
    }
}
