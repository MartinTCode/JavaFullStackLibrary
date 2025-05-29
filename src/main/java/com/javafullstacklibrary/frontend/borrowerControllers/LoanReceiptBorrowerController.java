package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.LibraryUser;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.LoanList;
import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.utils.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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

    private LoanDAO loanDAO;
    private EntityManager entityManager;

    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        this.entityManager = emf.createEntityManager();
        this.loanDAO = new LoanDAO(entityManager);
        displayLoanReceipt();
    }

    private void displayLoanReceipt() {
        List<ItemCopy> loanedItems = LoanList.getInstance().getPendingLoans();
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
        processLoans(loanedItems);
    }

    private void processLoans(List<ItemCopy> itemCopies) {
        // do this with the DOA:
        LibraryUser loaner = UserSession.getCurrentUser();
        for (ItemCopy itemCopy : itemCopies) {
            loanDAO.save(itemCopy, loaner, getReturnDateByItemCopy(itemCopy));
        }
        // Clear the pending loans list after successful processing
        LoanList.getInstance().clearPendingLoans();
    }

    private LocalDate getReturnDateByItemCopy(ItemCopy itemCopy) {
        // First extract the item from the ItemCopy
        Item item = itemCopy.getItem();
        int days2add2now = item.getMaxLoanTimeDays();
        return LocalDate.now().plusDays(days2add2now);
    }

    @FXML
    private void clickedHomeMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Search");
    }

    @FXML
    private void clickedLoanMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Loan");
    }

    @FXML
    private void clickedReturnMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Return");
    }

    @FXML
    private void clickedAccountMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuBorrower() {
        LoanList.getInstance().clearPendingLoans();
        MenuNavigationHelper.menuClickBorrower(mainPane, "SignOut");
    }

    @FXML
    private void clickedPrintLoanReceipt() {
        // TODO: Implement print functionality
        System.out.println("Print functionality to be implemented");
    }
}
