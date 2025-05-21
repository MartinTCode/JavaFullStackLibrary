package com.javafullstacklibrary.frontend.librarianControllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class StartViewLibrarianController {

    @FXML
    private void clickedSearchMenuLibrarian(MouseEvent event) {
        //Implement navigation to Search view
        System.out.println("Search menu clicked");
    }

    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        //Implement navigation to Overdue Loans view
        System.out.println("Overdue menu clicked");
    }

    @FXML
    private void clickedLibraryMenuLibrarian(MouseEvent event) {
        //Implement navigation to Manage Library view
        System.out.println("Library menu clicked");
    }

    @FXML
    private void clickedUsersMenuLibrarian(MouseEvent event) {
        //Implement navigation to Manage Users view
        System.out.println("Users menu clicked");
    }

    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        //Implement navigation to Account view
        System.out.println("Account menu clicked");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        //Implement navigation to Sign Out view
        System.out.println("Sign out menu clicked");
    }
}
