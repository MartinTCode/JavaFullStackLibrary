package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class ReturnMenuBorrowerController {

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
    private void clickedReturnButtonBorrower() {
        String barcode = barcodeFieldBorrower.getText();
        System.out.println("Return button clicked with barcode: " + barcode);
        // Add implementation for getting info about the item an displaying it in next view

        // Switch to the Return_View_Borrower view
        MenuNavigationHelper.menuClickBorrower(mainPane, "ReturnView");
    }

    @FXML
    private void clickedAddReturnButton() {
        // Implement the logic for adding a return
        System.out.println("Add Return button clicked");
    }

    @FXML
    private void clickedConfirmReturnsButtonBorrower() {
        System.out.println("Confirm Returns button clicked");
        // Implement the logic for confirming returns and getting the info for the receipt

        // Switch to the Return_Receipt_Borrower view
        MenuNavigationHelper.menuClickBorrower(mainPane, "ReturnReceipt");
    }
}