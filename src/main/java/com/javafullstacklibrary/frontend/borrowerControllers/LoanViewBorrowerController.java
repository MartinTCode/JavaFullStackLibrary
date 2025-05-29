package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
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
import com.javafullstacklibrary.utils.LoanList;
import com.javafullstacklibrary.utils.UserSession;

import java.util.List;

public class LoanViewBorrowerController {

    private LoanValidationService loanValidationService;

    public void initialize() {
        // Initialize the loan validation service
        this.loanValidationService = new LoanValidationService();
        
        // Load existing pending loans from LoanList
        loadPendingLoans();
    }

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

    /**
     * Load existing pending loans from the LoanList into the UI
     */
    private void loadPendingLoans() {
        List<ItemCopy> pendingLoans = LoanList.getInstance().getPendingLoans();
        loanContainer.getChildren().clear(); // Clear existing items
        
        for (ItemCopy itemCopy : pendingLoans) {
            addItemToLoanContainer(itemCopy);
        }
        
        System.out.println("Loaded " + pendingLoans.size() + " pending loans");
    }

    // Top menu navigation methods
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

    // Action methods for the loan functionality
    @FXML
    private void clickedAddLoanButton() {
        String barcode = barcodeField.getText();
        System.out.println("Add Loan button clicked with barcode: " + barcode);
        
        if (barcode == null || barcode.trim().isEmpty()) {
            System.out.println("Please enter a barcode");
            return;
        }

        // Validate barcode for loan eligibility
        ValidationResult<ItemCopy> result = loanValidationService.validateBarcodeForLoan(barcode);
        
        if (result.isSuccess()) {
            ItemCopy itemCopy = result.getData();
            System.out.println("Adding item to loan list: " + itemCopy.getItem().getTitle());
            
            // Check if item is already in the pending loans list
            if (LoanList.getInstance().getPendingLoans().contains(itemCopy)) {
                System.out.println("Item already in loan list");
                // TODO: Show message to user that item is already added
                return;
            }
            
            // Check if the user can loan more items
            boolean allowed2loan = loanValidationService.canLoanMore(UserSession.getCurrentUser().getBorrowerProfile());
            if (allowed2loan) {
                // Add to LoanList
                LoanList.getInstance().addItemToLoan(itemCopy);
                
                // Add to UI
                addItemToLoanContainer(itemCopy);
                
                // Clear the barcode field for next entry
                barcodeField.clear();
                
                System.out.println("Item successfully added to pending loans");
            } else {
                // User has reached the maximum number of loans allowed
                System.out.println("User has reached maximum loan limit");
                // TODO: Show error message to user
            }
        } else {
            // Show error message
            String errorMessage = result.getMessage();
            System.out.println("Validation failed: " + errorMessage);
            // TODO: Show error message to user (e.g., using Alert dialog or status label)
        }
    }

    @FXML
    private void clickedConfirmLoansButtonBorrower() {
        List<ItemCopy> pendingLoans = LoanList.getInstance().getPendingLoans();
        System.out.println("Confirm Loans button clicked for " + pendingLoans.size() + " items");
        
        if (pendingLoans.isEmpty()) {
            System.out.println("No items to loan");
            // TODO: Show message to user that no items are selected
            return;
        }
        
        // TODO: Process all items in the loan list
        // TODO: Create loan records in the database
        // TODO: Navigate to loan receipt view
        MenuNavigationHelper.buttonClickBorrower(mainPane, "LoanReceipt");
    }

    /**
     * Adds an item to the loan container for display
     * @param itemCopy The item to add to the loan list display
     */
    private void addItemToLoanContainer(ItemCopy itemCopy) {
        // Create a container for the item with remove functionality
        HBox itemContainer = new HBox(10);
        itemContainer.setStyle("-fx-padding: 10px; -fx-border-color: lightgray; -fx-border-width: 1px; -fx-background-color: #f9f9f9; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        
        // Create item details label
        Label itemLabel = new Label(
            itemCopy.getItem().getTitle() + " (Barcode: " + itemCopy.getBarcode() + ")"
        );
        itemLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        itemLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(itemLabel, javafx.scene.layout.Priority.ALWAYS);
        
        // Create remove button
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-size: 10px;");
        removeButton.setOnAction(e -> {
            // Remove from LoanList
            LoanList.getInstance().removeItemFromLoan(itemCopy);
            // Remove from UI
            loanContainer.getChildren().remove(itemContainer);
            System.out.println("Removed item from loan list: " + itemCopy.getItem().getTitle());
        });
        
        itemContainer.getChildren().addAll(itemLabel, removeButton);
        loanContainer.getChildren().add(itemContainer);
    }
}