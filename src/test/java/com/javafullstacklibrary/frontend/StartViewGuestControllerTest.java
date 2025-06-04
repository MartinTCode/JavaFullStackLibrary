package com.javafullstacklibrary.frontend;

// Importing necessary classes for JavaFX testing
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

// Importing testdata class instances
import static com.javafullstacklibrary.frontend.MenuEntryTestData.MENU_ENTRIES_GUESTVIEWS;
import static com.javafullstacklibrary.frontend.MenuEntryTestData.MENU_ENTRIES_BORROWERVIEWS;

import com.javafullstacklibrary.MainApp;
// import start controller class
//import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;

// import class responsible for changing the view to monitor current controller.
import com.javafullstacklibrary.utils.MenuNavigationHelper;


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

    private static final int SLEEP_TIME = 10; // milliseconds

    @Test
    @Order(1)
    void testGuestViewMenuButtons() {
        testMenuButtonClick_All (
            MENU_ENTRIES_GUESTVIEWS, 
            null
            );
    }



    @Test
    @Order(2)
    void testBorrowerViewMenuButtons() {
        testMenuButtonClick_All(MENU_ENTRIES_BORROWERVIEWS, 
        null
        );
    }


    // TODO: add transition logic to go to next menu view 
    // (staff / user via #signInMenuGuest), and add field to MenuEntry to if entry is transition steps.
    // then change logic in testMenuButtonClick_All to check if the entry is a transition step, and if so,
    // navigate to the next menu entry after testing all buttons from the current one.

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
        MainApp mainApp = new MainApp();
        mainApp.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper to navigate to a view by clicking the menu button
    private void navigateTo(String buttonId) {
        clickOn(buttonId);
        sleep(SLEEP_TIME);
    }

    // Helper to click a menu button and verify the expected node is visible
    private void clickMenuAndVerify(String buttonId, String expectedFieldId, String testName) {
        logger.info("[" + testName + "] : " + "Clicking + " + buttonId + ", expecting " + expectedFieldId);
        clickOn(buttonId);
        sleep(SLEEP_TIME);
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
                logger.info("\n\n[testMenuButtonClick_All] Clicking " + buttonIdTo + ", from " + buttonIdFrom + ", expecting " + expectedFieldId
                + " | CurrentController: " + MenuNavigationHelper.getCurrentControllerName());
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


    /*@Disabled
    @Order(3)
    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeTextGuest", 
        "#signInMenuGuest, #ssnField"
    })*/
    void testInner_SearchMenuButtons(String buttonId, String expectedFieldId) {
        navigateTo("#searchMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId, "testInner_SearchMenuButtons");
    }

    /*@Disabled
    @Order(4)
    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeTextGuest",
        "#searchMenuGuest, #searchField",
        "#staffButton, #usernameField"
    }) */
    void testInner_SignInUser(String buttonId, String expectedFieldId) {
        navigateTo("#signInMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId, "testInner_SignInUser");
    }

}