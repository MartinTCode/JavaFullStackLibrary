package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.UserSession;

public class AccountMenuBorrowerController {

    @FXML
    private Pane mainPane;

    @FXML
    private void clickedHomeMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "Search");
    }

    @FXML
    private void clickedLoanMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "Loan");
    }

    @FXML
    private void clickedReturnMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "Return");
    }

    @FXML
    private void clickedAccountMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    private void clickedUserInfoButtonBorrower() {
        MenuNavigationHelper.buttonClickBorrower(mainPane, "UserInfo");
    }

    @FXML
    private void clickedActiveLoansButtonBorrower() {
        MenuNavigationHelper.buttonClickBorrower(mainPane, "ActiveLoans");
    }

    @FXML
    private void clickedChangePasswordButtonBorrower() {
        MenuNavigationHelper.buttonClickBorrower(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonBorrower() {
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    private void clickedChangeUserInfoButtonBorrower() {
        MenuNavigationHelper.buttonClickBorrower(mainPane, "ChangeInfo");
    }

    @FXML
    private void clickedSaveUserInfoButtonBorrower() {
        //Implementation for saving user info
        MenuNavigationHelper.buttonClickBorrower(mainPane, "UserInfo");
    }

    @FXML
    private void clickedConfirmNewPasswordButtonBorrower() {
        //Implementation for changing password
        System.out.println("Confirm New Password button clicked");
    }

    @FXML
    private void clickedConfirmSignOutButtonBorrower() {
        // Get current user info for logging
        String currentUser = UserSession.getCurrentUsername();
        String currentRole = UserSession.getCurrentUserRole();
        
        // Clear the user session
        UserSession.logout();
        
        // Log the logout action
        System.out.println("User logged out: " + currentUser + " (Role: " + currentRole + ")");
        
        // Navigate to guest home page
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    @FXML
    private void clickedCancelSignOutButtonBorrower() {
        // No session changes needed - just navigate back
        System.out.println("Sign out cancelled");
        MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
    }
}