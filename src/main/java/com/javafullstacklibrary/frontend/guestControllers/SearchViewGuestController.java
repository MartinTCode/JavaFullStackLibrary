package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.DataSingleton;
import com.javafullstacklibrary.services.ItemQueryService;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.frontend.components.SearchResultItemComponent;

public class SearchViewGuestController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private VBox resultsContainer;

    @FXML
    private Button loadMoreButton;
    
    // Pagination parameters
    private static final int RESULTS_PER_PAGE = 5;
    private int currentPage = 0;
    private String currentQuery = "";
    private long totalResults = 0;

    /**
     * Initializes the controller after the root element has been completely processed.
     * Retrieves the search query from the DataSingleton and sets it in the search field.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String searchQuery = DataSingleton.getInstance().getSearchQuery();
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            System.out.println("Retrieved search query: " + searchQuery);
            setSearchQuery(searchQuery);
        } else {
            resultsCountLabel.setText("Please enter a search query");
            System.out.println("No search query found in DataSingleton.");
            loadMoreButton.setVisible(false);
        }
        
        // Initially hide the load more button
        loadMoreButton.setVisible(false);
    }

    /**
     * Handles the action when the "Home" menu button is clicked.
     * Navigates the user to the home view.
     */
    @FXML
    private void clickedHomeMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    /**
     * Handles the action when the "Sign In" menu button is clicked.
     * Navigates the user to the sign-in view.
     */
    @FXML
    private void clickedSignInMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "SignIn");
    }

    /**
     * Handles the action when the search button is clicked.
     * Retrieves the search query from the search field and performs a database search.
     */
    @FXML
    private void clickedSearchButton() {
        String query = searchField.getText();
        if (query == null || query.trim().isEmpty()) {
            resultsCountLabel.setText("Please enter a search query");
            resultsContainer.getChildren().clear();
            loadMoreButton.setVisible(false);
            return;
        }
        
        System.out.println("Searching for: " + query);
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
            
            // Determine if we need a "load more" button
            loadMoreButton.setVisible(totalResults > RESULTS_PER_PAGE);
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            resultsCountLabel.setText("Search error occurred");
        }
    }

    /**
     * Loads search results from the database and displays them.
     */
    private void loadSearchResults(ItemQueryService queryService, String query, int offset, int limit) {
        // Get search results from the service
        List<Item> items = queryService.searchItems(query, offset, limit);
        
        // Display results
        for (Item item : items) {
            SearchResultItemComponent resultComponent = new SearchResultItemComponent(item);
            resultsContainer.getChildren().add(resultComponent);
        }
        
        // If no results were found and this is the first page
        if (items.isEmpty() && offset == 0) {
            Label noResultsLabel = new Label("No results found for query: " + query);
            resultsContainer.getChildren().add(noResultsLabel);
        }
    }

    /**
     * Updates the results count label with the number of found items
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
     * Handles the action when the "Load More" button is clicked.
     * Loads the next page of search results.
     */
    @FXML
    private void handleLoadMoreResults() {
        System.out.println("Loading more results...");
        currentPage++;
        
        try (ItemQueryService queryService = new ItemQueryService()) {
            int offset = currentPage * RESULTS_PER_PAGE;
            loadSearchResults(queryService, currentQuery, offset, RESULTS_PER_PAGE);
            
            // Hide the button if we've loaded all results
            if (offset + RESULTS_PER_PAGE >= totalResults) {
                loadMoreButton.setVisible(false);
            }
        } catch (Exception e) {
            System.err.println("Error loading more results: " + e.getMessage());
        }
    }

    /**
     * Sets the search query in the search field and triggers the search logic.
     *
     * @param query The search query to set in the search field.
     */
    public void setSearchQuery(String query) {
        searchField.setText(query);
        clickedSearchButton();
    }
}