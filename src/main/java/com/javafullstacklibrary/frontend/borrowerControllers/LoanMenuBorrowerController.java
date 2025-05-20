package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class LoanMenuBorrowerController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeFieldBorrower;

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
    private void clickedLoanButtonBorrower() {
        String barcode = barcodeFieldBorrower.getText();
        System.out.println("Loan button clicked with barcode: " + barcode);
       
        //Implementation for getting the info about loaned book and showing it on the loan view
        MenuNavigationHelper.menuClickBorrower(mainPane, "LoanView");
    }

    @FXML
    private void clickedAddLoanButton() {
        // Implement the logic for adding a loan
        System.out.println("Add Loan button clicked");
    }

    @FXML
    private void clickedConfirmLoansButtonBorrower() {
        // Implement the logic for confirming loans
        System.out.println("Confirm Loans button clicked");
    }
}