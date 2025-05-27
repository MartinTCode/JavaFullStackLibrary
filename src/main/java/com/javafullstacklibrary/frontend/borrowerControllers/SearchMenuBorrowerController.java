package com.javafullstacklibrary.frontend.borrowerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.DataSingleton;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

public class SearchMenuBorrowerController {

    // Singleton instance to store search query
    private DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    private Pane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton filterButton;

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
        // Print the search query
        String query = searchField.getText();
        System.out.println("Search query: " + query);
        // Set the search query in the singleton instance
        dataSingleton.setSearchQuery(query);
        // Load the search results view
        MenuNavigationHelper.buttonClickBorrower(mainPane, "SearchResults");
    }

}