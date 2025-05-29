package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

//import com.javafullstacklibrary.services.AuthenticationService;
import com.javafullstacklibrary.services.LoanValidationService;
import com.javafullstacklibrary.services.ValidationResult;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
public class LoanMenuBorrowerController {

    private LoanValidationService loanValidationService;

    public void initialize() {        
        // Initialize the loan validation service
        this.loanValidationService = new LoanValidationService();
        
        // Hardwire test data for development
        prefillTestData();
    }

    /**
     * Prefills the form with test data for development purposes
     */
    private void prefillTestData() {
        // barcode already on loan:
        // 7PTK3A94
        // barcode for a journal not allowd to loan:
        // R5BX0Q31
        // barcode for a reference item not allowed to loan:
        // R5BX0Q30
        // barcode for a valid item copy:
        // X82DMJQ1
        barcodeFieldBorrower.setText("X82DMJQ1");
    }

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeFieldBorrower;

    @FXML
    private Label errorLabelBarcodeSearch;


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
    // This method is called when the inital "Loan" button is clicked
    private void clickedLoanButtonBorrower() {
        String barcode = barcodeFieldBorrower.getText();
        System.out.println("Loan button clicked with barcode: " + barcode);
        
        // Validate barcode for loan eligibility
        ValidationResult<ItemCopy> result = loanValidationService.validateBarcodeForLoan(barcode);
        
        if (result.isSuccess()) {
            // Barcode is valid for loan - proceed to loan view
            ItemCopy itemCopy = result.getData();
            System.out.println("Valid item found: " + itemCopy.getItem().getTitle());
            // TODO: Pass the validated ItemCopy to the LoanView
            MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanView");
        } else {
            // Barcode is not valid - show error message
            String errorMessage = result.getMessage();
            System.out.println("Loan validation failed: " + errorMessage);
            errorLabelBarcodeSearch.setText(errorMessage);
            errorLabelBarcodeSearch.setVisible(true);
            // For now, don't navigate to LoanView
        }
    }

    @FXML
    private void clickedAddLoanButton() {
        //String barcode = barcodeFieldBorrower.getText();
        //System.out.println("Add Loan button clicked with barcode: " + barcode);
        // Implement the logic for adding a loan
    }

    @FXML
    private void clickedConfirmLoansButtonBorrower() {
        System.out.println("Confirm Loans button clicked");
        // Implement the logic for confirming loans and getting the info for the reciept
        // Switch to the Loan_Receipt_Borrower view
        MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanReceipt");
    }
}