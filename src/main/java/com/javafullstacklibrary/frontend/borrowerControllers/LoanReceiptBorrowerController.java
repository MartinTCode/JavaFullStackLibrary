package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.LoanList;
import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.model.Loan;
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
        Label borrowerLabel = new Label("Borrower: " + UserSession.getCurrentUser().getUsername());
        borrowerLabel.getStyleClass().add("loan-item-label");
        loanContainer.getChildren().add(borrowerLabel);

        // Add items section header
        Label itemsHeader = new Label("\nLoaned Items:");
        itemsHeader.getStyleClass().add("label-title");
        loanContainer.getChildren().add(itemsHeader);

        // Add each loaned item to the receipt
        for (ItemCopy item : loanedItems) {
            Label itemLabel = new Label(
                String.format("- %s\n  Barcode: %s\n  Due Date: %s", 
                    item.getItem().getTitle(), 
                    item.getBarcode(),
                    LocalDate.now().plusWeeks(2).toString()
                )
            );
            itemLabel.getStyleClass().add("loan-item-label");
            loanContainer.getChildren().add(itemLabel);
        }

        // Process the loans in the database
        processLoans(loanedItems);
    }

    private void processLoans(List<ItemCopy> items) {
        try {
            entityManager.getTransaction().begin();
            
            for (ItemCopy item : items) {
                Loan loan = new Loan();
                loan.setItemCopy(item);
                loan.setLibraryUser(UserSession.getCurrentUser());
                loan.setStartDate(LocalDate.now());
                loan.setReturnDate(LocalDate.now().plusWeeks(2));
                loanDAO.save(loan);
            }
            
            entityManager.getTransaction().commit();
            
            // Clear the pending loans list after successful processing
            LoanList.getInstance().clearPendingLoans();
            
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error processing loans: " + e.getMessage());
        }
    }

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
    private void clickedPrintLoanReceipt() {
        // TODO: Implement print functionality
        System.out.println("Print functionality to be implemented");
    }
}
