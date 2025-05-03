package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import com.javafullstacklibrary.utils.ViewLoader;


public class SearchViewGuestController {
    public SearchViewGuestController() {
        // Default constructor
    }

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

    @FXML
    private void clickedHomeMenuGuest() {
        try {
            // Navigate to the home view
            StartViewGuestController controller = new StartViewGuestController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Start_View_Guest.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSignInMenuGuest() {
        try {
            // Navigate to the sign-in view
            SignInUserController controller = new SignInUserController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Sign_In_User.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSearchButton() {
        // Handle the search button click
        String query = searchField.getText();
        System.out.println("Search query: " + query);

        // Simulate search results
        resultsCountLabel.setText("Search query resulted in X matches");
        resultsContainer.getChildren().clear(); // Clear previous results
        // Add mock results (replace with actual search logic)
        for (int i = 1; i <= 5; i++) {
            Label result = new Label("Result " + i + " for query: " + query);
            resultsContainer.getChildren().add(result);
        }
        loadMoreButton.setVisible(true); // Show the load more button
    }

    @FXML
    private void handleLoadMoreResults() {
        // Handle the "Load More" button click
        System.out.println("Loading more results...");
        // Add mock additional results (replace with actual logic)
        for (int i = 6; i <= 10; i++) {
            Label result = new Label("Additional Result " + i);
            resultsContainer.getChildren().add(result);
        }
        loadMoreButton.setVisible(false); // Hide the button after loading more results
    }

    public void setSearchQuery(String query) {
        // Set the query in the search field
        searchField.setText(query);

        // Optionally, trigger the search logic
        clickedSearchButton();
    }
}