package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class SignOutLibrarianController {

    @FXML
    private Pane mainPane;

    // Top menu navigation
    @FXML
    private void clickedHomeMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenyLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedOverdueMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    @FXML
    private void clickedLibraryMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedUsersMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedAccountMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    // Side menu actions
    @FXML
    private void clickedUserInfoLibrarian() {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "UserInfo");
    }

    @FXML
    private void clickedChangePasswordButtonLibrarian() {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedConfirmSignOutButtonLibrarian() {
        // Go to guest view after sign out
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    // Cancel sign out, go back to librarian start view
    @FXML
    private void clickedCancelSignOutButtonLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }
}
