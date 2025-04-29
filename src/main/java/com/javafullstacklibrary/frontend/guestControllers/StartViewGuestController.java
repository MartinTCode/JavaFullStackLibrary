package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.javafullstacklibrary.utils.ViewLoader;


public class StartViewGuestController {

    @FXML
    private Pane mainPane;

    @FXML
    private void clickedSearchMenuGuest() {
       try {
            // Load fxml to stage
            SearchMenuGuestController controller = new SearchMenuGuestController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Search_Menu_Guest.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedSignInMenuGuest() {
        try {
            // Load fxml to stage
            SignInUserController controller = new SignInUserController();
            ViewLoader.loadToStage(mainPane, "guestViews", "Sign_In_User.fxml", controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}