package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.dao.ItemCopyDAO;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Loan;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager; // Manage pending transactions like loans and returns
import com.javafullstacklibrary.utils.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Optional;

public class ReturnMenuBorrowerController {

    private LoanDAO loanDAO;
    private ItemCopyDAO itemCopyDAO;
    private EntityManager entityManager;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeFieldBorrower;

    @FXML
    private Label errorLabelBarcodeSearch;

    public void initialize() {
        // Initialize the EntityManager and DAOs
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        this.entityManager = emf.createEntityManager();
        this.loanDAO = new LoanDAO(entityManager);
        this.itemCopyDAO = new ItemCopyDAO(entityManager);
        
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
        
        // Validate the barcode isn't empty
        if (barcode == null || barcode.trim().isEmpty()) {
            showErrorMessage("Please enter a barcode");
            return;
        }

        // Find the ItemCopy by barcode
        ItemCopy itemCopy = findItemCopyByBarcode(barcode);
        if (itemCopy == null) {
            showErrorMessage("Invalid barcode");
            return;
        }

        // Validate that this item has an active loan
        Optional<Loan> activeLoan = loanDAO.findActiveLoanByItemCopy(itemCopy);
        if (activeLoan.isEmpty()) {
            showErrorMessage("No active loan found for this item");
            return;
        }

        // Validate that the loan belongs to the current user
        if (!(activeLoan.get().getLibraryUser().getId() == UserSession.getCurrentUser().getId())) {
            showErrorMessage("This item was not borrowed by you");
            return;
        }

        // Check if the item is already in the pending returns
        if (PendingTransactionManager.getInstance().getPending().contains(itemCopy)) {
            showErrorMessage("Item is already in your return list");
            return;
        }

        // Add item to return list and navigate to return view
        PendingTransactionManager.getInstance().addItemToPending(itemCopy);
        MenuNavigationHelper.buttonClickBorrower(mainPane, "ReturnView");
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

    // Helper method to find ItemCopy by barcode
    private ItemCopy findItemCopyByBarcode(String barcode) {
        Optional<ItemCopy> itemCopy = itemCopyDAO.findByBarcode(barcode);
        return itemCopy.orElse(null);
    }
}