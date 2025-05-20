package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.DataSingleton;

public class SearchViewBorrowerController implements Initializable {

    // Singleton instance to store and fetch search query
    private DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private Button searchButtonBorrower;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private VBox resultsContainer;

    @FXML
    private Button loadMoreButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String searchQuery = DataSingleton.getInstance().getSearchQuery();
        if (searchQuery != null) {
            System.out.println("Retrieved search query: " + searchQuery);
            setSearchQuery(searchQuery);
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
    private void clickedSearchButtonBorrower() {
        String query = searchField.getText();
        System.out.println("Search query: " + query);

        setResultsCountLabel(10); // Simulate setting the results count
        resultsContainer.getChildren().clear(); // Clear previous results

        // Add mock results (replace with actual search logic)
        for (int i = 1; i <= 5; i++) {
            Label result = new Label("Result " + i + " for query: " + query);
            resultsContainer.getChildren().add(result);
        }
        loadMoreButton.setVisible(true); // Show the load more button
    }

    private void setResultsCountLabel(int count) {
        if (resultsCountLabel != null) {
            resultsCountLabel.setText("Search query resulted in " + count + " matches");
        }
    }

    @FXML
    private void handleLoadMoreResults() {
        System.out.println("Loading more results...");

        // Add mock additional results (replace with actual logic)
        for (int i = 6; i <= 10; i++) {
            Label result = new Label("Additional Result " + i + " for query: " + searchField.getText()
            + ".\nShowing how it can hold more data in same box");
            resultsContainer.getChildren().add(result);
        }
        loadMoreButton.setVisible(false); // Hide the button after loading more results
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        clickedSearchButtonBorrower();
    }
}
