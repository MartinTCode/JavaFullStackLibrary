package com.javafullstacklibrary.frontend.guestControllers;

// Importing necessary classes for JavaFX testing
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Importing necessary classes for junit testing
import org.testfx.framework.junit5.ApplicationTest;

// Importing necessary classes for parameterized tests
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
// Importing JUnit Test annotation
import org.junit.jupiter.api.Test;
// Importing JUnit Test Method Orderer
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

// Importing necessary classes for assertions
import static org.testfx.util.NodeQueryUtils.*;

// Importing necessary TestFX classes for assertions
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.util.List;
import java.util.logging.Logger;

/*
 * This class is a test suite for the StartViewGuestController class.
 * It uses TestFX for JavaFX UI testing and JUnit 5 for test organization.
 * The tests are designed to verify the functionality of the menu buttons in the guest view.
 * NOTE: this test class extends ApplicationTest, which is a TestFX class.
 * Be very careful with migrating code from here to utils since many are dependent on the ApplicationTest class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StartViewGuestControllerTest extends ApplicationTest {

    private static final Logger logger = LoggerUtil.getFileLogger(StartViewGuestControllerTest.class, "StartViewGuestControllerTest.log");

    // List of menu entries for the test
    // This list contains the button IDs and their corresponding expected field IDs to show when being in their view.
    // represents menus: start > search > sign_in
    private final List<MenuEntry> MENU_ENTRIES_GUESTVIEWS = List.of(
        new MenuEntry("#homeMenuGuest", "#welcomeTextGuest"),
        new MenuEntry("#searchMenuGuest", "#searchField"),
        new MenuEntry("#signInMenuGuest", "#ssnField")
    );

    private final MenuEntry BORROWER_TRANSITION_GW = new MenuEntry(
        //1. transition to sign in view:
        "#signInMenuGuest", "#ssnField", true,
        // List<MenuEntry>. 
        List.of(
        // 2. Fake login as user BREAKS! (expect username field, should alrdy be there).
        //new MenuEntry("#userButton", "#usernameField", true),
        // 3. press sign in.
        new MenuEntry("#signInButton", "#welcomeMsgUser", true)
        )
    );

    // represents menus: start > search > loan > return > account > sign_out
    private final List<MenuEntry> MENU_ENTRIES_BORROWERVIEWS = List.of(
        BORROWER_TRANSITION_GW,
        new MenuEntry("#homeMenuBorrower", "#welcomeMsgUser"),
        new MenuEntry("#searchMenuBorrower", "#searchButtonBorrower"),
        new MenuEntry("#loanMenuBorrower", "#loanButtonBorrower"),
        new MenuEntry("#returnMenuBorrower", "#returnButtonBorrower"),
        new MenuEntry("#accountMenuBorrower", "#userInfoButtonBorrower"),
        new MenuEntry("#signOutMenuBorrower", "#confirmSignOutButtonBorrower")
    );

    private final MenuEntry LIBRARIAN_TRANSITION_GW = new MenuEntry(
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
    private final List<MenuEntry> MENU_ENTRIES_LIBRARIANVIEWS = List.of(
        LIBRARIAN_TRANSITION_GW,
        new MenuEntry("#homeMenuLibrarian", "#welcomeMsgLibrarian")
    );

    @Test
    @Order(1)
    void testGuestViewMenuButtons() {
        testMenuButtonClick_All (
            MENU_ENTRIES_GUESTVIEWS, 
            List.of(new MenuEntry("#homeMenuGuest", "#welcomeTextGuest"))
        );
    }

    @Test
    @Order(2)
    // FIXME: there be dragons here, transition works fine, but then it finds no node for:
    // lookup by selector: "#searchButtonBorrower"
    void testBorrowerViewMenuButtons() {
        testMenuButtonClick_All(MENU_ENTRIES_BORROWERVIEWS, null);
    }


    // TODO: add transition logic to go to next menu view 
    // (staff / user via #signInMenuGuest), and add field to MenuEntry to if entry is transition steps.
    // then change logic in testMenuButtonClick_All to check if the entry is a transition step, and if so,
    // navigate to the next menu entry after testing all buttons from the current one.

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Create an instance of FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafullstacklibrary/frontend/guestViews/Start_View_Guest.fxml"));
            
            // Set the controller programmatically
            loader.setController(new StartViewGuestController());
            
            // Load the FXML file
            Parent root = loader.load();
            
            // Set up the stage
            primaryStage.setScene(new Scene(root));
            // Set minimum width and height for the stage (window)
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            // set title of window.
            primaryStage.setTitle("JavaFX Library App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper to navigate to a view by clicking the menu button
    private void navigateTo(String buttonId) {
        clickOn(buttonId);
        sleep(5);
    }

    // Helper to click a menu button and verify the expected node is visible
    private void clickMenuAndVerify(String buttonId, String expectedFieldId, String testName) {
        logger.info("[" + testName + "] : " + "Clicking + " + buttonId + ", expecting " + expectedFieldId);
        clickOn(buttonId);
        sleep(5);
        verifyThat(expectedFieldId, isVisible());
    }

    @BeforeEach
    void setUp() {
        // navigate to the initial view
        // This is the starting view for the test
        navigateTo("#homeMenuGuest");
    }



    private void testMenuButtonClick_All(List<MenuEntry> menuEntries, List<MenuEntry> back2homeTraversal) {
        for (int i = 0; i < menuEntries.size(); i++) {
            MenuEntry entry_from = menuEntries.get(i);
            String buttonIdFrom = entry_from.getButtonId();
            //TODO: add transition logic here to do transition steps and then continue to the next menu entry:
            if (entry_from.isTransition()) {
                // If the entry is a transition step, 
                traverseThroughViews(List.of(entry_from), true);
                traverseThroughViews(entry_from.getTransitionSteps(), true);
                continue;
            }
            // ... and try to click all other buttons from this view:
            for (int y = 0; y < menuEntries.size(); y++) {
                MenuEntry entry_to = menuEntries.get(y);
                String buttonIdTo = entry_to.getButtonId();
                // Skip if the button is the same it's coming from (can't click itself)
                if (buttonIdFrom.equals(buttonIdTo) || entry_to.isTransition()) {
                    // If the entry is a transition step, skip it.
                    continue; 
                }

                // Click the button and verify the expected field is visible
                String expectedFieldId = entry_to.getFieldId();
                logger.info("[testMenuButtonClick_All] Clicking " + buttonIdTo + ", from " + buttonIdFrom + ", expecting " + expectedFieldId);
                clickMenuAndVerify(buttonIdTo, expectedFieldId, "testMenuButtonClick_All");
                // go back to from again:
                navigateTo(buttonIdFrom);
            }
            // navigate to the next menu entry after testing all buttons from the current one
            if (i < menuEntries.size()-1)
            {
                navigateTo(menuEntries.get(i+1).getButtonId());
            }
        }
        // once all tests are done, go back home so to not pollute next test:
        if (back2homeTraversal != null) {
            traverseThroughViews(back2homeTraversal, false);
        }
        // TODO: add logic here for doing several steps at a time.
        //navigateTo(buttonForStartView);
    }

    private void traverseThroughViews(List<MenuEntry> menuEntries, boolean verifyClick) {
        for (MenuEntry entry : menuEntries) {
            String button2press = entry.getButtonId();
            if (verifyClick) {
                // Click the button and verify the expected field is visible
                String expectedFieldId = entry.getFieldId();
                clickMenuAndVerify(button2press, expectedFieldId, "traverseThroughViews");
            } else {
                String expectedFieldId = entry.getFieldId();
                logger.info("Clicking " + button2press + ", expecting " + expectedFieldId);
                navigateTo(button2press);
            }
        }
    }


    @Disabled
    @Order(2)
    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeTextGuest", 
        "#signInMenuGuest, #ssnField"
    })
    void testInner_SearchMenuButtons(String buttonId, String expectedFieldId) {
        navigateTo("#searchMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId, "testInner_SearchMenuButtons");
    }

    @Disabled
    @Order(3)
    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeTextGuest",
        "#searchMenuGuest, #searchField",
        "#staffButton, #usernameField"
    })
    void testInner_SignInUser(String buttonId, String expectedFieldId) {
        navigateTo("#signInMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId, "testInner_SignInUser");
    }

}