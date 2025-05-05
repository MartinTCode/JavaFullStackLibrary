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
import java.util.ResourceBundle;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.DataSingleton;

public class SearchViewGuestController implements Initializable {

    // Singleton instance to store and fetch search query
    private DataSingleton dataSingleton = DataSingleton.getInstance();

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

    /**
     * Initializes the controller after the root element has been completely processed.
     * Retrieves the search query from the DataSingleton and sets it in the search field.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if not known.
     * @param resources The resources used to localize the root object, or null if not specified.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String searchQuery = DataSingleton.getInstance().getSearchQuery();
        if (searchQuery != null) {
            System.out.println("Retrieved search query: " + searchQuery);
            setSearchQuery(searchQuery);
        }
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
     * Retrieves the search query from the search field, simulates search results,
     * and displays them in the results container.
     */
    @FXML
    private void clickedSearchButton() {
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
        resultsCountLabel.setText("Search query resulted in " + count + " matches");
    }

    /**
     * Handles the action when the "Load More" button is clicked.
     * Simulates loading additional search results and appends them to the results container.
     */
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