package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class AccountMenuLibrarianController {

    @FXML
    private BorderPane mainPane;

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

        @FXML
    private void clickedUserInfoButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "UserInfo");
    }

    @FXML
    private void clickedChangePasswordButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SignOut");
    }

    @FXML
    private void clickedChangeUserInfoButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ChangeInfo");
    }

    @FXML
    private void clickedSaveUserInfoButtonLibrarian(MouseEvent event) {
        // Implement logic for saving user info
        System.out.println("Save User Info button clicked");
    }

    @FXML
    private void clickedConfirmNewPasswordButtonLibrarian(MouseEvent event) {
        // Implement logic for confirming new password
        System.out.println("Confirm New Password button clicked");
    }

    @FXML
    private void clickedCancelNewPasswordButtonLibrarian(MouseEvent event) {
    MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }
}
 
