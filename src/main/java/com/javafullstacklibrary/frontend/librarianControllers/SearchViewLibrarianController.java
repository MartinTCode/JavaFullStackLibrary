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

public class SearchViewLibrarianController implements Initializable {

    public static String initialQuery = null; // <-- Add this line

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

    private int totalResults = 10; // Simulated total results
    private int loadedResults = 0;
    private final int PAGE_SIZE = 5;

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

    @FXML
    private void clickedSearchButtonLibrarian() {
        String query = searchField.getText();
        System.out.println("Librarian search query: " + query);

        setResultsCountLabel(totalResults); // Simulate setting the results count
        resultsContainer.getChildren().clear(); // Clear previous results

        // Add mock results (replace with actual search logic)
        for (int i = 1; i <= PAGE_SIZE && i <= totalResults; i++) {
            Label result = new Label("Result " + i + " for query: " + query);
            resultsContainer.getChildren().add(result);
        }
        loadedResults = PAGE_SIZE;
        loadMoreButton.setVisible(totalResults > PAGE_SIZE);
    }

    private void setResultsCountLabel(int count) {
        if (resultsCountLabel != null) {
            resultsCountLabel.setText("Search query resulted in " + count + " matches");
        }
    }

    @FXML
    private void handleLoadMoreResults() {
        System.out.println("Loading more results...");

        int nextLimit = Math.min(loadedResults + PAGE_SIZE, totalResults);
        for (int i = loadedResults + 1; i <= nextLimit; i++) {
            Label result = new Label("Additional Result " + i + " for query: " + searchField.getText()
                + ".\nShowing how it can hold more data in same box");
            resultsContainer.getChildren().add(result);
        }
        loadedResults = nextLimit;
        loadMoreButton.setVisible(loadedResults < totalResults);
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        clickedSearchButtonLibrarian();
    }
}
