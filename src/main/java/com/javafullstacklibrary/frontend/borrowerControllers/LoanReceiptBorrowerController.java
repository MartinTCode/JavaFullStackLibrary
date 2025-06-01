package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager;
import com.javafullstacklibrary.utils.UserSession;

import java.time.LocalDate;
import java.util.List;

public class LoanReceiptBorrowerController {
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private ScrollPane loanScrollPane;
    
    @FXML
    private VBox loanContainer;
    
    @FXML
    private Button printLoanRecieptBorrower;

    

    public void initialize() {
        displayLoanReceipt();
    }

    private void displayLoanReceipt() {
        List<ItemCopy> loanedItems = PendingTransactionManager.getInstance().getPending();
        loanContainer.getChildren().clear();

        // Add receipt header
        Label dateLabel = new Label("Date: " + LocalDate.now().toString());
        dateLabel.getStyleClass().add("loan-item-label");
        loanContainer.getChildren().add(dateLabel);

        // Add borrower information
        Label borrowerLabel = new Label("Borrower: " + 
        UserSession.getCurrentUser().getBorrowerProfile().getFirstName() + " " +
        UserSession.getCurrentUser().getBorrowerProfile().getLastName());
        
        borrowerLabel.getStyleClass().add("loan-item-label");
        loanContainer.getChildren().add(borrowerLabel);

        // Add items section header
        Label itemsHeader = new Label("\nLoaned Items:");
        itemsHeader.getStyleClass().add("label-title");
        loanContainer.getChildren().add(itemsHeader);

        // Add each loaned item to the receipt
        for (ItemCopy itemCopy : loanedItems) {
            Label itemLabel = new Label(
                String.format("- %s\n  Barcode: %s\n  Due Date: %s", 
                    itemCopy.getItem().getTitle(), 
                    itemCopy.getBarcode(),
                    getReturnDateByItemCopy(itemCopy).toString()
                )
            );
            itemLabel.getStyleClass().add("loan-item-label");
            loanContainer.getChildren().add(itemLabel);
        }

        // Process the loans in the database
        PendingTransactionManager.getInstance().clearPending();
    }


    private LocalDate getReturnDateByItemCopy(ItemCopy itemCopy) {
        // First extract the item from the ItemCopy
        Item item = itemCopy.getItem();
        int days2add2now = item.getMaxLoanTimeDays();
        return LocalDate.now().plusDays(days2add2now);
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
    private void clickedPrintLoanReceipt() {
        // TODO: Implement print functionality
        System.out.println("Print functionality to be implemented");
    }
}
