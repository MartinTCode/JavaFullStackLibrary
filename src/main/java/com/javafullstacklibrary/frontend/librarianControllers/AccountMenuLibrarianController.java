package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class AccountMenuLibrarianController {

    @FXML
    private BorderPane mainPane;

    // --- Top Menu Methods ---
    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    @FXML
    private void clickedLibraryMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // --- Side Button Methods ---
    @FXML
    private void clickedUserInfoButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "UserInfo");
    }

    @FXML
    private void clickedChangePasswordButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SignOut");
    }
}

