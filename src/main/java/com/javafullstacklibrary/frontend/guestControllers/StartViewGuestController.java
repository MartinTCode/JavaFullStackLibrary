package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.utils.MenuNavigationHelper;


public class StartViewGuestController {

    @FXML
    private Pane mainPane;

    @FXML
    private void clickedSearchMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"Search");
    }

    @FXML
    private void clickedSignInMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"SignIn");
    }
}