package com.javafullstacklibrary.frontend;

import java.util.List;

public class MenuEntryTestData {

    public static final MenuEntry BORROWER_TRANSITION_GW = new MenuEntry(
        //1. transition to sign in view:
        "#signInMenuGuest", "#ssnField", true,
        // List<MenuEntry>. 
        List.of(
        // 2. Fake login as user (expect username field, should alrdy be there).
        new MenuEntry("#userButton", "#ssnField", true),
        // 3. press sign in.
        new MenuEntry("#signInButton", "#welcomeMsgUser", true)
        )
    );

    // List of menu entries for the test
    // This list contains the button IDs and their corresponding expected field IDs to show when being in their view.
    // represents menus: start > search > sign_in
    public static final List<MenuEntry> MENU_ENTRIES_GUESTVIEWS = List.of(
        new MenuEntry("#homeMenuGuest", "#welcomeTextGuest"),
        new MenuEntry("#searchMenuGuest", "#searchField"),
        new MenuEntry("#signInMenuGuest", "#ssnField")
        //BORROWER_TRANSITION_GW
    );

    

    // represents menus: start > search > loan > return > account > sign_out
    public static final List<MenuEntry> MENU_ENTRIES_BORROWERVIEWS = List.of(
        BORROWER_TRANSITION_GW,
        new MenuEntry("#startMenuBorrower", "#welcomeMsgUser"),
        new MenuEntry("#searchMenuBorrower", "#searchField"),
        new MenuEntry("#loanMenuBorrower", "#loanButtonBorrower"),
        new MenuEntry("#returnMenuBorrower", "#returnButtonBorrower"),
        new MenuEntry("#accountMenuBorrower", "#userInfoButtonBorrower"),
        new MenuEntry("#signOutMenuBorrower", "#confirmSignOutButtonBorrower")
    );

    public static final MenuEntry LIBRARIAN_TRANSITION_GW = new MenuEntry(
        //1. transition to sign in view:
        "#signInMenuGuest", "#ssnField", true,
        // List<MenuEntry>. 
        List.of(
        // 2. Press login as staff.
        new MenuEntry("#staffButton", "#usernameField", true),
        // 3. press sign in.
        new MenuEntry("#signInButtonLibrarian", "#welcomeMsgLibrarian", true)
        )
    );

    // TODO: add entries here as work goes on, making sure to use or add unique ID in fxml.
    @SuppressWarnings("unused")
    public static final List<MenuEntry> MENU_ENTRIES_LIBRARIANVIEWS = List.of(
        LIBRARIAN_TRANSITION_GW,
        new MenuEntry("#homeMenuLibrarian", "#welcomeMsgLibrarian")
    );

    
}
