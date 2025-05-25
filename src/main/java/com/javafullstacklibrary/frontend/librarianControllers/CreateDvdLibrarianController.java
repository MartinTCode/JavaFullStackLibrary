package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class CreateDvdLibrarianController {

    @FXML
    private BorderPane mainPane;

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
    private void clickedCancelNewUsersButtonLibrarian(MouseEvent event) {
        // Go back to Manage Library view
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedSaveNewUsersButtonLibrarian(MouseEvent event) {
        // Logic to save the new DVD details
        System.out.println("Save New DVD button clicked");
    }
}
