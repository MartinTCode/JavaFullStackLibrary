package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.services.ItemQueryService;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.frontend.components.SearchResultItemComponentLibrarian;

import java.util.List;

public class SearchViewLibrarianController implements Initializable {

    public static String initialQuery = null;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private Button searchButtonLibrarian;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private VBox resultsContainer;

    @FXML
    private Button loadMoreButton;

    // --- Real search state ---
    private final ItemQueryService queryService = new ItemQueryService();
    private static final int RESULTS_PER_PAGE = 5;
    private int currentPage = 0;
    private String currentQuery = "";
    private long totalResults = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the search view
        resultsContainer.getChildren().clear();
        loadMoreButton.setVisible(false);

        // Set the search field if initialQuery is set
        if (initialQuery != null) {
            setSearchQuery(initialQuery);
            initialQuery = null; // Reset after use
        }
    }

    @FXML
    private void clickedHomeMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedOverdueMenuLibrarian() {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
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

    /**
     * Handles the search button click event for the librarian view.
     * Validates the search query, performs the search, and updates the results.
     */
    @FXML
    private void clickedSearchButtonLibrarian() {
        String query = searchField.getText();
        if (query == null || query.trim().isEmpty()) {
            resultsCountLabel.setText("Please enter a search query");
            resultsContainer.getChildren().clear();
            loadMoreButton.setVisible(false);
            return;
        }

        System.out.println("Librarian searching for: " + query);
        currentQuery = query;
        currentPage = 0;

        // Query total result count
        totalResults = queryService.countSearchResults(query);
        setResultsCountLabel((int) totalResults);

        // Clear previous results
        resultsContainer.getChildren().clear();

        // Perform search with pagination
        loadSearchResults(query, currentPage * RESULTS_PER_PAGE, RESULTS_PER_PAGE);

        // Show/hide load more button
        loadMoreButton.setVisible(totalResults > RESULTS_PER_PAGE);
    }

    /**
     * Loads search results from the database and displays them in the results container.
     * 
     * @param query The search query
     * @param offset The offset for pagination
     * @param limit The maximum number of results to load
     */
    private void loadSearchResults(String query, int offset, int limit) {
        List<Item> items = queryService.searchItems(query, offset, limit);

        for (Item item : items) {
            SearchResultItemComponentLibrarian resultComponent = new SearchResultItemComponentLibrarian(item);
            resultsContainer.getChildren().add(resultComponent);
        }

        if (items.isEmpty() && offset == 0) {
            Label noResultsLabel = new Label("No results found for query: " + query);
            resultsContainer.getChildren().add(noResultsLabel);
        }
    }

    /**
     * Sets the results count label based on the number of matches found.
     * Displays a message indicating how many matches were found.
     * @param count
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
     * Loads the next page of search results and updates the UI accordingly.
     */
    @FXML
    private void handleLoadMoreResults() {
        System.out.println("Loading more results...");
        currentPage++;

        int offset = currentPage * RESULTS_PER_PAGE;
        loadSearchResults(currentQuery, offset, RESULTS_PER_PAGE);

        if (offset + RESULTS_PER_PAGE >= totalResults) {
            loadMoreButton.setVisible(false);
        }
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        clickedSearchButtonLibrarian();
    }

    // Optionally, add a cleanup method to close the service
    public void cleanup() {
        queryService.close();
    }
}
