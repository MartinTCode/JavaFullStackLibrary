package com.javafullstacklibrary.utils;

import java.io.IOException;

import com.javafullstacklibrary.frontend.guestControllers.SignInUserController;
import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;
import com.javafullstacklibrary.frontend.guestControllers.SearchMenuGuestController;
import com.javafullstacklibrary.frontend.guestControllers.SignInStaffController;
import com.javafullstacklibrary.frontend.guestControllers.SearchViewGuestController;
import com.javafullstacklibrary.frontend.borrowerControllers.StartViewBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.SearchMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.SearchViewBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.LoanMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.ReturnMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.AccountMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.SignOutBorrowerController;


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

    public static void menuClickBorrower(Pane mainPane, String menuChange) {
        switch (menuChange) {
            case "Home":
                // Navigate to the Home view for borrowers
                navigateToView(mainPane, "borrowerViews", "Start_View_Borrower.fxml", new StartViewBorrowerController());
                break;
            case "Search":
                // Navigate to the Search view for borrowers
                navigateToView(mainPane, "borrowerViews", "Search_Menu_Borrower.fxml", new SearchMenuBorrowerController());
                break;
            case "Loan":
                // Navigate to the Loan view for borrowers
                navigateToView(mainPane, "borrowerViews", "Loan_Menu_Borrower.fxml", new LoanMenuBorrowerController());
                break;
            case "Return":
                // Navigate to the Return view for borrowers
                navigateToView(mainPane, "borrowerViews", "Return_Menu_Borrower.fxml", new ReturnMenuBorrowerController());
                break;
            case "Account":
                // Navigate to the Account view for borrowers
                navigateToView(mainPane, "borrowerViews", "Account_Menu_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "SignOut":
                // Navigate to the Sign-Out view for borrowers
                navigateToView(mainPane, "borrowerViews", "Sign_Out_Borrower.fxml", new SignOutBorrowerController());
                break;
            case "SearchResults":
                // Navigate to the Search Results view for borrowers
                navigateToView(mainPane, "borrowerViews", "Search_View_Borrower.fxml", new SearchViewBorrowerController());
                break;
            case "UserInfo":
                // Navigate to the User Info view for borrowers
                navigateToView(mainPane, "borrowerViews", "User_Info_View_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ChangeInfo":
                // Navigate to the Change Info view for borrowers
                navigateToView(mainPane, "borrowerViews", "User_Info_Edit_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ActiveLoans":
                // Navigate to the Active Loans view for borrowers
                navigateToView(mainPane, "borrowerViews", "Active_Loans_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ChangePassword":
                // Navigate to the Change Password view for borrowers
                navigateToView(mainPane, "borrowerViews", "Change_Password_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "LoanView":
                // Navigate to the Loan view for borrowers
                navigateToView(mainPane, "borrowerViews", "Loan_View_Borrower.fxml", new LoanMenuBorrowerController());
                break;
            case "LoanReceipt":
                // Navigate to the Loan Receipt view for borrowers
                navigateToView(mainPane, "borrowerViews", "Loan_Receipt_Borrower.fxml", new LoanMenuBorrowerController());
                break;
            case "ReturnView":
                // Navigate to the Return view for borrowers
                navigateToView(mainPane, "borrowerViews", "Return_View_Borrower.fxml", new ReturnMenuBorrowerController());
                break;
            case "ReturnReceipt":
                // Navigate to the Return Receipt view for borrowers
                navigateToView(mainPane, "borrowerViews", "Return_Receipt_Borrower.fxml", new ReturnMenuBorrowerController());
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
