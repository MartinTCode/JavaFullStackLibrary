package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager;
import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.utils.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class ReturnReceiptBorrowerController {
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private ScrollPane returnScrollPane;
    
    @FXML
    private VBox returnContainer;
    
    @FXML
    private Button printReturnRecieptBorrower;

    private LoanDAO loanDAO;
    private EntityManager entityManager;

    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        this.entityManager = emf.createEntityManager();
        this.loanDAO = new LoanDAO(entityManager);
        displayReturnReceipt();
    }

    private void displayReturnReceipt() {
        List<ItemCopy> returnedItems = PendingTransactionManager.getInstance().getPending();
        returnContainer.getChildren().clear();

        // Add receipt header
        Label dateLabel = new Label("Date: " + LocalDate.now().toString());
        dateLabel.getStyleClass().add("return-item-label");
        returnContainer.getChildren().add(dateLabel);

        // Add returner information
        Label returnerLabel = new Label("Returner: " + 
            UserSession.getCurrentUser().getBorrowerProfile().getFirstName() + " " +
            UserSession.getCurrentUser().getBorrowerProfile().getLastName());
        
        returnerLabel.getStyleClass().add("return-item-label");
        returnContainer.getChildren().add(returnerLabel);

        // Add items section header
        Label itemsHeader = new Label("\nReturned Items:");
        itemsHeader.getStyleClass().add("label-title");
        returnContainer.getChildren().add(itemsHeader);

        // Add each returned item to the receipt
        for (ItemCopy itemCopy : returnedItems) {
            Label itemLabel = new Label(
                String.format("- %s\n  Barcode: %s\n  Return Date: %s", 
                    itemCopy.getItem().getTitle(), 
                    itemCopy.getBarcode(),
                    LocalDate.now().toString()
                )
            );
            itemLabel.getStyleClass().add("return-item-label");
            returnContainer.getChildren().add(itemLabel);
        }

        // Process the returns in the database
        processReturns(returnedItems);
    }

    private void processReturns(List<ItemCopy> itemCopies) {
        for (ItemCopy itemCopy : itemCopies) {
            loanDAO.processReturn(itemCopy);
        }
        // Clear the pending returns list after successful processing
        PendingTransactionManager.getInstance().clearPending();
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
    private void clickedPrintReturnReceipt() {
        System.out.println("Print functionality not currently implemented");
    }
}
