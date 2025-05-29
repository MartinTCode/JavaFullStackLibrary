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
import com.javafullstacklibrary.utils.LoanList;

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
        
        // Clear previous error message
        errorLabelBarcodeSearch.setVisible(false);
        errorLabelBarcodeSearch.setText("");
        
        // Validate barcode for loan eligibility
        ValidationResult<ItemCopy> result = loanValidationService.validateBarcodeForLoan(barcode);
        
        if (result.isSuccess()) {
            // Barcode is valid for loan
            ItemCopy itemCopy = result.getData();
            System.out.println("Valid item found: " + itemCopy.getItem().getTitle());
            

            // Check if the user can loan more items with null safety
            boolean allowed2loan = false;
            try {
                if (UserSession.getCurrentUser() != null) {
                    
                    allowed2loan = loanValidationService.canLoanMore(UserSession.getCurrentUser());
                    System.out.println("User can loan more items: " + allowed2loan);
                } else {
                    System.out.println("User session or borrower profile is null");
                    String errorMessage = "User session error. Please log in again.";
                    errorLabelBarcodeSearch.setText(errorMessage);
                    errorLabelBarcodeSearch.setVisible(true);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error checking loan eligibility: " + e.getMessage());
                e.printStackTrace();
                String errorMessage = "Error checking loan eligibility. Please try again.";
                errorLabelBarcodeSearch.setText(errorMessage);
                errorLabelBarcodeSearch.setVisible(true);
                return;
            }
            
            if (allowed2loan) {
                // put the item copy into the LoanList
                LoanList.getInstance().addItemToLoan(itemCopy);
                System.out.println("Item added to pending loans: " + itemCopy.getItem().getTitle());
                // Navigate to the LoanView
                MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanView");
            } else {
                // User has reached the maximum number of loans allowed
                String errorMessage = "You have reached the maximum number of loans allowed for your user type.";
                System.out.println("Loan validation failed: " + errorMessage);
                errorLabelBarcodeSearch.setText(errorMessage);
                errorLabelBarcodeSearch.setVisible(true);
            }
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