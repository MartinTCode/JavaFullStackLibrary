package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class UserInfoBorrowerController {

    @FXML
    private Pane mainPane;

    @FXML
    private Button changeInfoButtonBorrower;

    @FXML
    private TextField ssnField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

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
    private void clickedChangeInfoButtonBorrower() {
        System.out.println("Change Info button clicked");
        // Enable editing of user information fields
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        addressField.setEditable(true);
        emailField.setEditable(true);
        phoneNumberField.setEditable(true);
    }

    @FXML
    private void clickedUserInfoButtonBorrower() {
        System.out.println("User Info button clicked");
        MenuNavigationHelper.menuClickBorrower(mainPane, "UserInfo");
    }

    @FXML
    private void clickedActiveLoansButtonBorrower() {
        System.out.println("Active Loans button clicked");
        MenuNavigationHelper.menuClickBorrower(mainPane, "ActiveLoans");
    }

    @FXML
    private void clickedChangePasswordButtonBorrower() {
        System.out.println("Change Password button clicked");
        MenuNavigationHelper.menuClickBorrower(mainPane, "ChangePassword");
    }

    @FXML
    private void clickedSignOutButtonBorrower() {
        System.out.println("Sign Out button clicked");
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }
}