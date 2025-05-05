package com.javafullstacklibrary.utils;

import java.io.IOException;

import com.javafullstacklibrary.frontend.guestControllers.SignInUserController;
import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;
import com.javafullstacklibrary.frontend.guestControllers.SearchMenuGuestController;
import com.javafullstacklibrary.frontend.guestControllers.SignInStaffController;
import com.javafullstacklibrary.frontend.guestControllers.SearchViewGuestController;

import javafx.scene.layout.Pane;

public class MenuNavigationHelper {

    // Private constructor to prevent instantiation
    private MenuNavigationHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    } 

    /**
     * Handles the action when a guest menu button is clicked.
     * Navigates the user to the specified view based on the menu option selected.
     *
     * @param mainPane   The main pane where the new view will be loaded.
     * @param menuChange The menu option selected by the user (e.g., "Home", "Search", "SignIn").
     * @throws IllegalArgumentException if the menuChange value is invalid.
     */
    public static void menuClickGuest(Pane mainPane, String menuChange) {
        switch (menuChange) {
            case "Home":
                // Navigate to the Home view for guests
                navigateToView(mainPane, "guestViews", "Start_View_Guest.fxml", new StartViewGuestController());
                break;
            case "Search":
                // Navigate to the Search view for guests
                navigateToView(mainPane, "guestViews", "Search_Menu_Guest.fxml", new SearchMenuGuestController());
                break;
            case "SearchResults":
                // Navigate to the Search Results view for guests
                navigateToView(mainPane, "guestViews", "Search_View_Guest.fxml", new SearchViewGuestController());
                break;
            case "SignIn":
                // Navigate to the Sign-In view for guests
                navigateToView(mainPane, "guestViews", "Sign_In_User.fxml", new SignInUserController());
                break;
            case "SignInStaff":
                // Navigate to the Sign-In view for staff
                navigateToView(mainPane, "guestViews", "Sign_In_Staff.fxml", new SignInStaffController());
                break;
            case "SignInUser":
                // Navigate to the Sign-In view for users
                navigateToView(mainPane, "guestViews", "Sign_In_User.fxml", new SignInUserController());
                break;
            default:
                // Throw an exception for invalid menu options
                throw new IllegalArgumentException("Invalid menu type: " + menuChange);
        }
    }

    /**
     * Handles navigation to a specified view.
     * This method loads the specified FXML file into the provided main pane and sets the controller.
     *
     * @param mainPane   The main pane where the new view will be loaded.
     * @param viewFolder The folder containing the FXML file (e.g., "guestViews").
     * @param fxmlFile   The FXML file to load (e.g., "Start_View_Guest.fxml").
     * @param controller The controller instance for the new view.
     * @throws IOException if there is an error loading the FXML file.
     */
    private static void navigateToView(Pane mainPane, String viewFolder, String fxmlFile, Object controller) {
        try {
            // Load the specified view into the main pane
            ViewLoader.loadToStage(mainPane, viewFolder, fxmlFile, controller);
        } catch (IOException e) {
            // Print the stack trace if an error occurs during view loading
            e.printStackTrace();
        }
    }
}
