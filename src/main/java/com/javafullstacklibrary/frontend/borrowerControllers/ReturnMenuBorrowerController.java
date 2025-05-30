package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.services.ReturnValidationService;
import com.javafullstacklibrary.services.ValidationResult;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager;
import com.javafullstacklibrary.utils.UserSession;

public class ReturnMenuBorrowerController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeFieldBorrower;

    @FXML
    private Label errorLabelBarcodeSearch;

    public void initialize() {
        // Prefill test data for development
        prefillTestData();
    }

    /**
     * Prefills the form with test data for development purposes
     */
    private void prefillTestData() {
        // Add a barcode for an item that is currently on loan to the current user
        barcodeFieldBorrower.setText("X82DMJQ1");
    }

    @FXML
    private void clickedHomeMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Search");
    }

    @FXML
    private void clickedLoanMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Loan");
    }

    @FXML
    private void clickedReturnMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Return");
    }

    @FXML
    private void clickedAccountMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuBorrower() {
        PendingTransactionManager.getInstance().clearPending();
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    private void clickedReturnButtonBorrower() {
        String barcode = barcodeFieldBorrower.getText();
        
        // Clear previous error message
        clearErrorMessage();
        
        // Use the validation service with try-with-resources
        try (ReturnValidationService returnValidationService = new ReturnValidationService()) {
            ValidationResult<ItemCopy> result = returnValidationService.validateBarcodeForReturn(
                barcode, UserSession.getCurrentUser()
            );
            
            if (!result.isSuccess()) {
                showErrorMessage(result.getMessage());
                return;
            }
            
            ItemCopy itemCopy = result.getData();
            
            // Check if the item is already in the pending returns
            if (PendingTransactionManager.getInstance().getPending().contains(itemCopy)) {
                showErrorMessage("Item is already in your return list");
                return;
            }

            // Add item to return list and navigate to return view
            PendingTransactionManager.getInstance().addItemToPending(itemCopy);
            MenuNavigationHelper.buttonClickBorrower(mainPane, "ReturnView");
        } catch (Exception e) {
            System.err.println("Error validating return: " + e.getMessage());
            showErrorMessage("Error processing return request");
        }
    }
    
    private void clearErrorMessage() {
        if (errorLabelBarcodeSearch != null) {
            errorLabelBarcodeSearch.setVisible(false);
            errorLabelBarcodeSearch.setText("");
        }
    }
    
    private void showErrorMessage(String message) {
        if (errorLabelBarcodeSearch != null) {
            errorLabelBarcodeSearch.setText(message);
            errorLabelBarcodeSearch.setVisible(true);
        }
    }
}