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

import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Loan;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.PendingTransactionManager; // Manage pending transactions like loans and returns
import com.javafullstacklibrary.utils.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class ReturnViewBorrowerController {

    private LoanDAO loanDAO;
    private EntityManager entityManager;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField barcodeFieldBorrower;

    @FXML
    private Button addReturnButton;

    @FXML
    private ScrollPane returnScrollPane;

    @FXML
    private VBox returnContainer;

    @FXML
    private Button confirmReturnsButtonBorrower;

    @FXML
    private Label statusLabel;

    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        this.entityManager = emf.createEntityManager();
        this.loanDAO = new LoanDAO(entityManager);
        loadPendingReturns();
    }

    private void loadPendingReturns() {
        List<ItemCopy> pendingReturns = PendingTransactionManager.getInstance().getPending();
        returnContainer.getChildren().clear();
        
        for (ItemCopy itemCopy : pendingReturns) {
            addItemToReturnContainer(itemCopy);
        }
    }

    @FXML
    private void clickedAddReturnButton() {
        String barcode = barcodeFieldBorrower.getText();
        
        clearStatusMessage();
        
        if (barcode == null || barcode.trim().isEmpty()) {
            showErrorMessage("Please enter a barcode");
            return;
        }

        // Find the item copy for this barcode
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

        PendingTransactionManager.getInstance().addItemToPending(itemCopy);
        addItemToReturnContainer(itemCopy);
        barcodeFieldBorrower.clear();
        showSuccessMessage("Item added to return list");
    }

    @FXML
    private void clickedConfirmReturnsButtonBorrower() {
        List<ItemCopy> pendingReturns = PendingTransactionManager.getInstance().getPending();
        
        if (pendingReturns.isEmpty()) {
            showErrorMessage("No items selected for return");
            return;
        }
        
        MenuNavigationHelper.buttonClickBorrower(mainPane, "ReturnReceipt");
    }

    private void addItemToReturnContainer(ItemCopy itemCopy) {
        HBox itemContainer = new HBox();
        itemContainer.getStyleClass().add("return-item-container");
        
        Label itemLabel = new Label(
            itemCopy.getItem().getTitle() + " (Barcode: " + itemCopy.getBarcode() + ")"
        );
        itemLabel.getStyleClass().add("return-item-label");
        itemLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(itemLabel, javafx.scene.layout.Priority.ALWAYS);
        
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> {
            PendingTransactionManager.getInstance().removeItemFromPending(itemCopy);
            returnContainer.getChildren().remove(itemContainer);
            showSuccessMessage("Item removed from return list");
        });
        
        itemContainer.getChildren().addAll(itemLabel, removeButton);
        returnContainer.getChildren().add(itemContainer);
    }

    // Menu navigation methods
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

    // Helper methods for UI feedback
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

    // Helper method to find ItemCopy by barcode
    private ItemCopy findItemCopyByBarcode(String barcode) {
        // TODO: Implement ItemCopy lookup by barcode
        // This should use an ItemCopyDAO to look up the ItemCopy
        return null;
    }
}
