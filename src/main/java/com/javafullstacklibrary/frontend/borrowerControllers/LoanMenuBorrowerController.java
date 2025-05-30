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

import com.javafullstacklibrary.utils.UserSession;
import com.javafullstacklibrary.utils.PendingTransactionManager; // Manage pending transactions like loans and returns

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
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuBorrower() {
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Search");
    }

    @FXML
    private void clickedLoanMenuBorrower() {
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Loan");
    }

    @FXML
    private void clickedReturnMenuBorrower() {
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Return");
    }

    @FXML
    private void clickedAccountMenuBorrower() {
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuBorrower() {
        // Flush the LoanList to clear any pending loans
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    // This method is called when the initial "Loan" button is clicked
    private void clickedLoanButtonBorrower() {
        String barcode = barcodeFieldBorrower.getText();
        
        // Clear previous error message
        clearErrorMessage();
        
        // Validate barcode for loan eligibility
        ValidationResult<ItemCopy> result = loanValidationService.validateBarcodeForLoan(barcode);
        
        if (!result.isSuccess()) {
            showErrorMessage(result.getMessage());
            return;
        }
        
        ItemCopy itemCopy = result.getData();
        
        // Validate user session
        if (UserSession.getCurrentUser() == null) {
            showErrorMessage("User session error. Please log in again.");
            return;
        }
        
        // Check if user can loan more items
        if (!loanValidationService.canLoanMore(UserSession.getCurrentUser())) {
            showErrorMessage("You have reached the maximum number of loans allowed for your user type.");
            return;
        }
        
        // Add item to loan list and navigate to loan view
        PendingTransactionManager.getInstance().addItemToPending(itemCopy);
        
        MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanView");
    }
    
    private void clearErrorMessage() {
        errorLabelBarcodeSearch.setVisible(false);
        errorLabelBarcodeSearch.setText("");
    }
    
    private void showErrorMessage(String message) {
        errorLabelBarcodeSearch.setText(message);
        errorLabelBarcodeSearch.setVisible(true);
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