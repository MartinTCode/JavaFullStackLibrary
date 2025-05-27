package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class OverdueViewLibrarianController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private VBox overdueContainer;

    @FXML
    private Button loadMoreButton;

    private int totalResults = 15; // Simulated total overdue loans
    private int loadedResults = 0;
    private final int PAGE_SIZE = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInitialResults();
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

        private void loadInitialResults() {
        overdueContainer.getChildren().clear();
        loadedResults = 0;
        loadMoreResults();
    }

    private void loadMoreResults() {
        int nextLimit = Math.min(loadedResults + PAGE_SIZE, totalResults);
        for (int i = loadedResults + 1; i <= nextLimit; i++) {
            Label result = new Label("Overdue Loan #" + i + ": Example borrower, Book Title, Due Date, etc.");
            overdueContainer.getChildren().add(result);
        }
        loadedResults = nextLimit;
        setResultsCountLabel(totalResults);
        loadMoreButton.setVisible(loadedResults < totalResults);
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
}
