package com.javafullstacklibrary.utils;

import java.io.IOException;

import com.javafullstacklibrary.frontend.guestControllers.SignInUserController;
import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;
import com.javafullstacklibrary.frontend.librarianControllers.AccountMenuLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ChangePasswordLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.CreateBookLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.CreateCourseLitLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.CreateDvdLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.CreateJournalLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.CreateUserLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.EditUsersLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ManageLibraryLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ManageUsersLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ModifyBookLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ModifyCourseLitLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ModifyDvdLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ModifyJournalLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.OverdueViewLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.SearchMenuLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.SearchViewLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.SignOutLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.StartViewLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.UserInfoLibrarianController;
import com.javafullstacklibrary.frontend.librarianControllers.ManageCopiesLibrarianController;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.frontend.guestControllers.SearchMenuGuestController;
import com.javafullstacklibrary.frontend.guestControllers.SignInStaffController;
import com.javafullstacklibrary.frontend.guestControllers.SearchViewGuestController;
import com.javafullstacklibrary.frontend.borrowerControllers.StartViewBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.SearchMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.SearchViewBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.LoanMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.LoanReceiptBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.ReturnMenuBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.ReturnReceiptBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.ReturnViewBorrowerController;
import com.javafullstacklibrary.frontend.borrowerControllers.AccountMenuBorrowerController;

import com.javafullstacklibrary.frontend.borrowerControllers.LoanViewBorrowerController;


import javafx.scene.layout.Pane;

/**
 * MenuNavigationHelper is a utility class that provides methods for handling menu navigation
 * and button actions in the Java Full Stack Library application.
 * It allows for easy navigation between different views based on user roles (guest, borrower, librarian).
 */
public class MenuNavigationHelper {

    private static String currentControllerName = null;


    // Private constructor to prevent instantiation
    private MenuNavigationHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    } 

    public static String getCurrentControllerName() {
        return currentControllerName;
    }
    //#region Menu Click Handlers for Guest.
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
    //#endregion 

    //#region Menu Click Handlers for Borrower.

    /**
     * Handles navigation for borrower menu bar clicks (top navigation).
     * Navigates the user to the appropriate borrower view based on the selected menu option.
     *
     * @param mainPane   The main pane where the new view will be loaded.
     * @param menuChange The menu option selected by the borrower (e.g., "Home", "Search", "Loan", "Return", "Account", "SignOut").
     * @throws IllegalArgumentException if the menuChange value is invalid.
     */
    public static void menuClickBorrower(Pane mainPane, String menuChange) {
        switch (menuChange) {
            case "Home":
                navigateToView(mainPane, "borrowerViews", "Start_View_Borrower.fxml", new StartViewBorrowerController());
                break;
            case "Search":
                navigateToView(mainPane, "borrowerViews", "Search_Menu_Borrower.fxml", new SearchMenuBorrowerController());
                break;
            case "Loan":
                navigateToView(mainPane, "borrowerViews", "Loan_Menu_Borrower.fxml", new LoanMenuBorrowerController());
                break;
            case "Return":
                navigateToView(mainPane, "borrowerViews", "Return_Menu_Borrower.fxml", new ReturnMenuBorrowerController());
                break;
            case "Account":
                navigateToView(mainPane, "borrowerViews", "Account_Menu_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "SignOut":
                navigateToView(mainPane, "borrowerViews", "Sign_Out_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            default:
                throw new IllegalArgumentException("Invalid menu type: " + menuChange);
        }
    }
    //#endregion

    //#region Submenu Click Handlers for Borrower.

    /**
     * Handles navigation for borrower button clicks (side panel or content buttons).
     * Navigates the user to the appropriate borrower view based on the button action selected.
     *
     * @param mainPane     The main pane where the new view will be loaded.
     * @param buttonAction The action associated with the button clicked by the borrower 
     * @throws IllegalArgumentException if the buttonAction value is invalid.
     */
    public static void buttonClickBorrower(Pane mainPane, String buttonAction) {
        switch (buttonAction) {
            case "SearchResults":
                navigateToView(mainPane, "borrowerViews", "Search_View_Borrower.fxml", new SearchViewBorrowerController());
                break;
            case "UserInfo":
                navigateToView(mainPane, "borrowerViews", "User_Info_View_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ChangeInfo":
                navigateToView(mainPane, "borrowerViews", "User_Info_Edit_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ActiveLoans":
                navigateToView(mainPane, "borrowerViews", "Active_Loans_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "ChangePassword":
                navigateToView(mainPane, "borrowerViews", "Change_Password_Borrower.fxml", new AccountMenuBorrowerController());
                break;
            case "LoanView":
                navigateToView(mainPane, "borrowerViews", "Loan_View_Borrower.fxml", new LoanViewBorrowerController());
                break;
            case "LoanReceipt":
                navigateToView(mainPane, "borrowerViews", "Loan_Receipt_Borrower.fxml", new LoanReceiptBorrowerController());
                break;
            case "ReturnView":
                navigateToView(mainPane, "borrowerViews", "Return_View_Borrower.fxml", new ReturnViewBorrowerController());
                break;
            case "ReturnReceipt":
                navigateToView(mainPane, "borrowerViews", "Return_Receipt_Borrower.fxml", new ReturnReceiptBorrowerController());
                break;
            default:
                throw new IllegalArgumentException("Invalid button action: " + buttonAction);  
        }
    }
    //#endregion

    //#region Menu Click Handlers for Librarian.

    /**
     * Handles navigation for librarian menu bar clicks.
     * Navigates the user to the appropriate librarian view based on the selected menu option.
     *
     * @param mainPane   The main pane where the new view will be loaded.
     * @param menuChange The menu option selected by the librarian (e.g., "Home").
     * @throws IllegalArgumentException if the menuChange value is invalid.
     */
    public static void menuClickLibrarian(Pane mainPane, String menuChange) {
        switch (menuChange) {
            case "Home":
                // Navigate to the Home view for librarians
                navigateToView(mainPane, "librarianViews", "Start_View_Librarian.fxml", new StartViewLibrarianController());
                break;
            case "Search":
                // Navigate to the Search view for librarians
                navigateToView(mainPane, "librarianViews", "Search_Menu_Librarian.fxml", new SearchMenuLibrarianController());
                break;
            case "Overdue":
                // Navigate to the Overdue Loans view for librarians
                navigateToView(mainPane, "librarianViews", "Overdue_View_Librarian.fxml", new OverdueViewLibrarianController());
                break;
            case "ManageLibrary":
                // Navigate to the Manage Library view for librarians
                navigateToView(mainPane, "librarianViews", "Manage_Library_Librarian.fxml", new ManageLibraryLibrarianController());
                break;
            case "ManageUsers":
                // Navigate to the Manage Users view for librarians
                navigateToView(mainPane, "librarianViews", "Manage_Users_Librarian.fxml", new ManageUsersLibrarianController());
                break;
            case "Account":
                // Navigate to the Account view for librarians
                navigateToView(mainPane, "librarianViews", "Account_Menu_Librarian.fxml", new AccountMenuLibrarianController());
                break;
            case "SignOut":
                // Navigate to the Sign-Out view for librarians
                navigateToView(mainPane, "librarianViews", "Sign_Out_Librarian.fxml", new SignOutLibrarianController());
                break;
            default:
                // Throw an exception for invalid menu options
                throw new IllegalArgumentException("Invalid menu type: " + menuChange);
        }
    }

    //#endregion

    //#region Submenu Click Handlers for Librarian.

    /**
     * Handles navigation for librarian button clicks.
     * Navigates the user to the appropriate librarian view based on the button action selected.
     *
     * @param mainPane     The main pane where the new view will be loaded.
     * @param buttonAction The action associated with the button clicked by the librarian (e.g., "UserInfo", "ChangePassword").
     * @throws IllegalArgumentException if the buttonAction value is invalid.
     */
    public static void buttonClickLibrarian(Pane mainPane, String buttonAction) {
        switch (buttonAction) {
            case "UserInfo":
                navigateToView(mainPane, "librarianViews", "User_Info_Librarian.fxml", new UserInfoLibrarianController());
                break;
            case "ChangePassword":
                navigateToView(mainPane, "librarianViews", "Change_Password_Librarian.fxml", new ChangePasswordLibrarianController());
                break;
            case "SignOut":
                navigateToView(mainPane, "librarianViews", "Sign_Out_Librarian.fxml", new SignOutLibrarianController());
                break;
            case "CreateBook":
                navigateToView(mainPane, "librarianViews", "Create_Book_Librarian.fxml", new CreateBookLibrarianController());
                break;	
            case "CreateCourseLit":
                navigateToView(mainPane, "librarianViews", "Create_Course_Lit_Librarian.fxml", new CreateCourseLitLibrarianController());
                break;
            case "CreateJournal":
                navigateToView(mainPane, "librarianViews", "Create_Journal_Librarian.fxml", new CreateJournalLibrarianController());
                break;
            case "CreateDvd":
                navigateToView(mainPane, "librarianViews", "Create_Dvd_Librarian.fxml", new CreateDvdLibrarianController());
                break;
            case "ModifyItem":
                navigateToView(mainPane, "librarianViews", "Search_View_Librarian", new SearchViewBorrowerController());
                break;
            case "EditUser":
                navigateToView(mainPane, "librarianViews", "Edit_Users_Librarian.fxml", new EditUsersLibrarianController());
                break;
            case "CreateUser":
                navigateToView(mainPane, "librarianViews", "Create_User_Librarian.fxml", new CreateUserLibrarianController());
                break;
            case "ModifyBook":
                navigateToView(mainPane, "librarianViews", "Modify_Book_Librarian.fxml", new ModifyBookLibrarianController());
                break;
            case "ModifyJournal":
                navigateToView(mainPane, "librarianViews", "Modify_Journal_Librarian.fxml", new ModifyJournalLibrarianController());
                break;
            case "ModifyDvd":
                navigateToView(mainPane, "librarianViews", "Modify_Dvd_Librarian.fxml", new ModifyDvdLibrarianController());
                break;
            case "ModifyCourseLit":
                navigateToView(mainPane, "librarianViews", "Modify_Course_Lit_Librarian.fxml", new ModifyCourseLitLibrarianController());
                break;
            default:
                throw new IllegalArgumentException("Invalid button action: " + buttonAction);
        }
    }
    //#endregion

    //#region Submenu Click Handlers for Librarian with Search Query.

    /**
     * Handles navigation for librarian button clicks with a search query.
     * Navigates the user to the appropriate librarian view based on the button action and search query.
     *
     * @param mainPane     The main pane where the new view will be loaded.
     * @param buttonAction The action associated with the button clicked by the librarian (e.g., "SearchView").
     * @param query        The search query to be used in the search view.
     * @throws IllegalArgumentException if the buttonAction value is invalid.
     */
public static void buttonClickLibrarian(Pane mainPane, String buttonAction, String query) {
        switch (buttonAction) {
            case "SearchView":
                SearchViewLibrarianController.initialQuery = query; // <-- Set static variable
                navigateToView(mainPane, "librarianViews", "Search_View_Librarian.fxml", new SearchViewLibrarianController());
                break;
            // ...other cases...
            default:
                throw new IllegalArgumentException("Invalid button action: " + buttonAction);
        }
    }

    //#endregion


    //#region Submenu Click Handlers for Librarian with Item.

    /**
     * Handles navigation for librarian button clicks with an item.
     * Navigates the user to the appropriate librarian view based on the button action and item.
     *
     * @param mainPane     The main pane where the new view will be loaded.
     * @param buttonAction The action associated with the button clicked by the librarian (e.g., "ModifyBook").
     * @param item         The item to be modified or viewed.
     * @throws IllegalArgumentException if the buttonAction value is invalid.
     */
    public static void buttonClickLibrarian(Pane mainPane, String buttonAction, Item item) {
        switch (buttonAction) {
            case "ModifyBook":
                navigateToView(mainPane, "librarianViews", "Modify_Book_Librarian.fxml", 
                    new ModifyBookLibrarianController(item));
                break;
            case "ModifyCourseLit":
                navigateToView(mainPane, "librarianViews", "Modify_Course_Lit_Librarian.fxml", 
                    new ModifyCourseLitLibrarianController(item));
                break;
            case "ModifyJournal":
                navigateToView(mainPane, "librarianViews", "Modify_Journal_Librarian.fxml", 
                    new ModifyJournalLibrarianController(item));
                break;
            case "ModifyDvd":
                navigateToView(mainPane, "librarianViews", "Modify_Dvd_Librarian.fxml", 
                    new ModifyDvdLibrarianController(item));
                break;
            case "ManageCopies":
                navigateToView(mainPane, "librarianViews", "Manage_Copies_Librarian.fxml", 
                    new ManageCopiesLibrarianController(item));
                break;
            default:
                throw new IllegalArgumentException("Invalid button action: " + buttonAction);
        }
    }

    //#endregion

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
            // Update the current controller name
            if (controller != null) {
                currentControllerName = controller.getClass().getSimpleName();
            } else {
                currentControllerName = null;
            }
            // Load the FXML file and set the controller
            ViewLoader.loadToStage(mainPane, viewFolder, fxmlFile, controller);
        } catch (IOException e) {
            // Print the stack trace if an error occurs during view loading
            e.printStackTrace();
        }
    }
}
