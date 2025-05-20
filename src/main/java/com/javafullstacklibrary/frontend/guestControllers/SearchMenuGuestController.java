package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.DataSingleton;

public class SearchMenuGuestController {

    // Singleton instance to store search query
    private DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

    @FXML
    private void clickedHomeMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    @FXML
    private void clickedSignInMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "SignIn");
    }

    @FXML
    private void clickedFilterButton() {
        // Functionality not implemented in current version. 
    }

    @FXML
    private void clickedSearchButton() {
        // Print the search query
        String query = searchField.getText();
        System.out.println("Search query: " + query);
        // Set the search query in the singleton instance
        dataSingleton.setSearchQuery(query);
        // Load the search results view
        MenuNavigationHelper.menuClickGuest(mainPane, "SearchResults");
    }
}