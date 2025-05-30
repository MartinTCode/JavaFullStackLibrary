package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.javafullstacklibrary.services.LoanValidationService;
import com.javafullstacklibrary.services.ValidationResult;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager;
import com.javafullstacklibrary.utils.UserSession;

import java.util.List;

public class LoanViewBorrowerController {

    private LoanValidationService loanValidationService;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeField;

    @FXML
    private Button addLoanButton;

    @FXML
    private ScrollPane loanScrollPane;

    @FXML
    private VBox loanContainer;

    @FXML
    private Button confirmLoansButtonBorrower;

    @FXML
    private Label statusLabel;

    public void initialize() {
        this.loanValidationService = new LoanValidationService();
        loadPendingLoans();
    }

    // #region Top menu navigation methods
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

    // #endregion

    /**
     * Load existing pending loans from the LoanList into the UI
     */
    private void loadPendingLoans() {
        List<ItemCopy> pendingLoans = PendingTransactionManager.getInstance().getPending();
        loanContainer.getChildren().clear();
        
        for (ItemCopy itemCopy : pendingLoans) {
            addItemToLoanContainer(itemCopy);
        }
    }

   

    @FXML
    // THESE BARCODES CAN BE USED TO LOAN ITEMS:
    // QW8Z4NME2L, KD9T7P6R, R5BX0Q32, R5BX0Q33, R5BX0Q34, R5BX0Q35, R5BX0Q36, R5BX0Q37, R5BX0Q38 
    private void clickedAddLoanButton() {
        String barcode = barcodeField.getText();
        
        // Clear previous status messages
        clearStatusMessage();
        
        if (barcode == null || barcode.trim().isEmpty()) {
            showErrorMessage("Please enter a barcode");
            return;
        }

        ValidationResult<ItemCopy> result = loanValidationService.validateBarcodeForLoan(barcode);
        
        // Show error if validation for given barcode fails
        if (!result.isSuccess()) {
            showErrorMessage(result.getMessage());
            return;
        }
        
        ItemCopy itemCopy = result.getData();
        
        // Check if the item is already in the pending loans
        if (PendingTransactionManager.getInstance().getPending().contains(itemCopy)) {
            showErrorMessage("Item is already in your loan list");
            return;
        }
        
        if (!loanValidationService.canLoanMore(UserSession.getCurrentUser(), PendingTransactionManager.getInstance())) {
            showErrorMessage("You have reached the maximum number of pending loans allowed for your user type.");
            return;
        }
        
        PendingTransactionManager.getInstance().addItemToPending(itemCopy);
        addItemToLoanContainer(itemCopy);
        barcodeField.clear();
        showSuccessMessage("Item added to loan list");
    }

    @FXML
    private void clickedConfirmLoansButtonBorrower() {
        List<ItemCopy> pendingLoans = PendingTransactionManager.getInstance().getPending();
        
        if (pendingLoans.isEmpty()) {
            showErrorMessage("No items selected for loan");
            return;
        }
        
        MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanReceipt");
    }

    /**
     * Adds an item to the loan container for display
     */
    private void addItemToLoanContainer(ItemCopy itemCopy) {
        HBox itemContainer = new HBox();
        itemContainer.getStyleClass().add("loan-item-container");
        
        Label itemLabel = new Label(
            itemCopy.getItem().getTitle() + " (Barcode: " + itemCopy.getBarcode() + ")"
        );
        itemLabel.getStyleClass().add("loan-item-label");
        itemLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(itemLabel, javafx.scene.layout.Priority.ALWAYS);
        
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> {
            PendingTransactionManager.getInstance().removeItemFromPending(itemCopy);
            loanContainer.getChildren().remove(itemContainer);
            showSuccessMessage("Item removed from loan list");
        });
        
        itemContainer.getChildren().addAll(itemLabel, removeButton);
        loanContainer.getChildren().add(itemContainer);
    }
    
    private void clearStatusMessage() {
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);
        statusLabel.setText("");
    }
    
    private void showErrorMessage(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().clear();
        statusLabel.getStyleClass().add("error-label");
        statusLabel.setVisible(true);
        statusLabel.setManaged(true);
    }
    
    private void showSuccessMessage(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().clear();
        statusLabel.getStyleClass().add("success-label");
        statusLabel.setVisible(true);
        statusLabel.setManaged(true);
    }
    
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}