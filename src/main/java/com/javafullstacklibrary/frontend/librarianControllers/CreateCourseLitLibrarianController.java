package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class CreateCourseLitLibrarianController {

    @FXML
    private BorderPane mainPane;

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

    @FXML
    private void clickedSaveNewCourseLitButtonLibrarian(MouseEvent event) {
        // Implement saving logic here
        System.out.println("Save button clicked");
    }
}
