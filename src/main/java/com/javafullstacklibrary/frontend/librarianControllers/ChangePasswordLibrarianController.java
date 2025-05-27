package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ChangePasswordLibrarianController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private TextField currentPasswordFieldLibrarian;

    @FXML
    private TextField newPasswordFieldLibrarian;

    @FXML
    private TextField confirmNewPasswordFieldLibrarian;

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
    private void clickedUserInfoLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "UserInfo");
    }

    @FXML
    private void clickedSignOutButtonLibrarian(MouseEvent event) {
        MenuNavigationHelper.buttonClickLibrarian(mainPane, "SignOut");
    }

    // --- Password Change Method ---
    @FXML
    private void clickedConfirmNewPasswordButtonLibrarian(MouseEvent event) {
        // Implement logic for confirming new password
        String currentPassword = currentPasswordFieldLibrarian.getText();
        String newPassword = newPasswordFieldLibrarian.getText();
        String confirmNewPassword = confirmNewPasswordFieldLibrarian.getText();
        System.out.println("Current: " + currentPassword + ", New: " + newPassword + ", Confirm: " + confirmNewPassword);
    }

    @FXML
    private void clickedCancelChangePasswordButtonLibrarian(MouseEvent event) {
        // Implement logic for canceling password change
        System.out.println("Cancel Change Password button clicked");
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }
}
