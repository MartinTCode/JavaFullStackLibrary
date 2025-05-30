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
import java.util.List;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.DataSingleton;
import com.javafullstacklibrary.services.ItemQueryService;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.frontend.components.SearchResultItemComponent;

public class SearchViewBorrowerController implements Initializable {

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

    // --- Real search state ---
    private static final int RESULTS_PER_PAGE = 5;
    private int currentPage = 0;
    private String currentQuery = "";
    private long totalResults = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String searchQuery = DataSingleton.getInstance().getSearchQuery();
        resultsContainer.getChildren().clear();
        loadMoreButton.setVisible(false);

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
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

    /**
     * Handles the search button click event for the borrower view.
     * Retrieves the search query from the text field,
     * performs a search using the ItemQueryService,
     * and updates the results container with the search results.
     */
    @FXML
    private void clickedSearchButtonBorrower() {
        String query = searchField.getText();
        if (query == null || query.trim().isEmpty()) {
            resultsCountLabel.setText("Please enter a search query");
            resultsContainer.getChildren().clear();
            loadMoreButton.setVisible(false);
            return;
        }

        System.out.println("Borrower searching for: " + query);
        currentQuery = query;
        currentPage = 0;

        try (ItemQueryService queryService = new ItemQueryService()) {
            // Query total result count
            totalResults = queryService.countSearchResults(query);
            setResultsCountLabel((int) totalResults);

            // Clear previous results
            resultsContainer.getChildren().clear();

            // Perform search with pagination
            loadSearchResults(queryService, query, currentPage * RESULTS_PER_PAGE, RESULTS_PER_PAGE);

            // Show/hide load more button
            loadMoreButton.setVisible(totalResults > RESULTS_PER_PAGE);
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            resultsCountLabel.setText("Search error occurred");
        }
    }

    /**
     * Loads search results from the database and displays them in the results container.
     * This method handles pagination by accepting an offset and limit for the results.
     */
    private void loadSearchResults(ItemQueryService queryService, String query, int offset, int limit) {
        List<Item> items = queryService.searchItems(query, offset, limit);

        for (Item item : items) {
            SearchResultItemComponent resultComponent = new SearchResultItemComponent(item);
            resultsContainer.getChildren().add(resultComponent);
        }

        if (items.isEmpty() && offset == 0) {
            Label noResultsLabel = new Label("No results found for query: " + query);
            resultsContainer.getChildren().add(noResultsLabel);
        }
    }

    /**
     * Sets the results count label based on the number of search results found.
     */
    private void setResultsCountLabel(int count) {
        String resultText;
        if (count == 0) {
            resultText = "No matches found";
        } else if (count == 1) {
            resultText = "Found 1 match";
        } else {
            resultText = "Found " + count + " matches";
        }
        resultsCountLabel.setText(resultText);
    }

    /**
     * Handles the "Load More Results" button click event.
     * Increments the current page and loads the next set of results.
     */
    @FXML
    private void handleLoadMoreResults() {
        System.out.println("Loading more results...");
        currentPage++;

        try (ItemQueryService queryService = new ItemQueryService()) {
            int offset = currentPage * RESULTS_PER_PAGE;
            loadSearchResults(queryService, currentQuery, offset, RESULTS_PER_PAGE);

            if (offset + RESULTS_PER_PAGE >= totalResults) {
                loadMoreButton.setVisible(false);
            }
        } catch (Exception e) {
            System.err.println("Error loading more results: " + e.getMessage());
        }
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        clickedSearchButtonBorrower();
    }
}
