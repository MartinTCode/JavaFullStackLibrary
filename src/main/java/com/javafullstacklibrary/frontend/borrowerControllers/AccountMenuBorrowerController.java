package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

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
        System.out.println("User Info button clicked");
        // Add logic to display user information
    }

    @FXML
    private void clickedActiveLoansButtonBorrower() {
        System.out.println("Active Loans button clicked");
        // Add logic to display active loans
    }

    @FXML
    private void clickedChangePasswordButtonBorrower() {
        System.out.println("Change Password button clicked");
        // Add logic to handle password change
    }

    @FXML
    private void clickedSignOutButtonBorrower() {
        System.out.println("Sign Out button clicked");
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    private void clickedChangeInfoButtonBorrower() {
        System.out.println("Change Info button clicked");
    }

    @FXML
    private void clickedSaveInfoButtonBorrower() {
        System.out.println("Save Info button clicked");
    }
}