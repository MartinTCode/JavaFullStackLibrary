package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.services.OverDueLoanInfoService;

public class OverdueViewLibrarianController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private VBox overdueContainer;

    @FXML
    private Button loadMoreButton;

    private List<Map<String, String>> allOverdueLoans;
    private int loadedResults = 0;
    private final int PAGE_SIZE = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadOverdueLoans();
        loadInitialResults();
    }

    private void loadOverdueLoans() {
        try (OverDueLoanInfoService service = new OverDueLoanInfoService()) {
            allOverdueLoans = service.getOverDueLoansDict();
        } catch (Exception e) {
            e.printStackTrace();
            allOverdueLoans = List.of(); // Empty list on error
        }
    }

    private void loadInitialResults() {
        overdueContainer.getChildren().clear();
        loadedResults = 0;
        loadMoreResults();
    }

    private void loadMoreResults() {
        int totalResults = allOverdueLoans.size();
        int nextLimit = Math.min(loadedResults + PAGE_SIZE, totalResults);
        
        for (int i = loadedResults; i < nextLimit; i++) {
            Map<String, String> loan = allOverdueLoans.get(i);
            VBox loanItem = createLoanItemDisplay(loan);
            overdueContainer.getChildren().add(loanItem);
        }
        
        loadedResults = nextLimit;
        setResultsCountLabel(totalResults);
        loadMoreButton.setVisible(loadedResults < totalResults);
    }

    private VBox createLoanItemDisplay(Map<String, String> loan) {
        VBox container = new VBox(5);
        container.getStyleClass().add("loan-item-container");
        container.setPadding(new Insets(10));

        // Title and Item Type
        Label titleLabel = new Label(loan.get("title") + " (" + loan.get("itemType") + ")");
        titleLabel.getStyleClass().add("loan-item-label");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Borrower Info
        HBox borrowerBox = new HBox(10);
        Label borrowerLabel = new Label("Borrower:");
        borrowerLabel.getStyleClass().add("loan-item-label");
        Label borrowerValue = new Label(loan.get("firstName") + " " + loan.get("lastName"));
        borrowerBox.getChildren().addAll(borrowerLabel, borrowerValue);

        // Email
        HBox emailBox = new HBox(10);
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("loan-item-label");
        Label emailValue = new Label(loan.get("email"));
        emailBox.getChildren().addAll(emailLabel, emailValue);

        // SSN
        HBox ssnBox = new HBox(10);
        Label ssnLabel = new Label("SSN:");
        ssnLabel.getStyleClass().add("loan-item-label");
        Label ssnValue = new Label(loan.get("ssn"));
        ssnBox.getChildren().addAll(ssnLabel, ssnValue);

        // Barcode
        HBox barcodeBox = new HBox(10);
        Label barcodeLabel = new Label("Barcode:");
        barcodeLabel.getStyleClass().add("loan-item-label");
        Label barcodeValue = new Label(loan.get("barcode"));
        barcodeBox.getChildren().addAll(barcodeLabel, barcodeValue);

        // Date Borrowed
        HBox borrowedBox = new HBox(10);
        Label borrowedLabel = new Label("Date Borrowed:");
        borrowedLabel.getStyleClass().add("loan-item-label");
        Label borrowedValue = new Label(loan.get("dateBorrowed"));
        borrowedBox.getChildren().addAll(borrowedLabel, borrowedValue);

        // Due Date
        HBox dueBox = new HBox(10);
        Label dueLabel = new Label("Due Date:");
        dueLabel.getStyleClass().add("loan-item-label");
        Label dueValue = new Label(loan.get("dueDate"));
        dueBox.getChildren().addAll(dueLabel, dueValue);

        // Days Overdue (highlighted)
        HBox overdueBox = new HBox(10);
        Label overdueLabel = new Label("Days Overdue:");
        overdueLabel.getStyleClass().add("loan-item-label");
        Label overdueValue = new Label(loan.get("daysOverdue"));
        overdueValue.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        overdueBox.getChildren().addAll(overdueLabel, overdueValue);

        container.getChildren().addAll(
            titleLabel, borrowerBox, emailBox, ssnBox, 
            barcodeBox, borrowedBox, dueBox, overdueBox
        );

        return container;
    }

    private void setResultsCountLabel(int count) {
        if (resultsCountLabel != null) {
            resultsCountLabel.setText("Overdue loans: " + count);
        }
    }

    @FXML
    private void handleLoadMoreResults() {
        loadMoreResults();
    }

    // Menu navigation handlers
    @FXML
    private void clickedHomeMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenyLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedLibraryMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageLibrary");
    }

    @FXML
    private void clickedUsersMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedAccountMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }
}