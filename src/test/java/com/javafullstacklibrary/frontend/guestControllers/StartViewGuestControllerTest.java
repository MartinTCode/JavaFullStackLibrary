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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StartViewGuestControllerTest extends ApplicationTest {

    private static final Logger logger = LoggerUtil.getFileLogger(StartViewGuestControllerTest.class, "StartViewGuestControllerTest.log");

    // List of menu entries for the test
    // This list contains the button IDs and their corresponding expected field IDs to show when being in their view.
    private static final List<MenuEntry> MENU_ENTRIES_LIST = List.of(
        new MenuEntry("#homeMenuGuest", "#welcomeTextGuest"),
        new MenuEntry("#searchMenuGuest", "#searchField"),
        new MenuEntry("#signInMenuGuest", "#ssnField")
    );

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

    @Order(1)
    @Test
    void testMenuButtonClick_All() {
        List<MenuEntry> menuEntries = MENU_ENTRIES_LIST;
        for (int i = 0; i < menuEntries.size(); i++) {
            String buttonIdFrom = menuEntries.get(i).getButtonId();
            // ... and try to click all other buttons from this view:
            for (MenuEntry entry_to : MENU_ENTRIES_LIST) {
                // Skip if the button is the same it's coming from (can't click itself)
                if (buttonIdFrom.equals(entry_to.getButtonId())) {
                    continue; 
                }
                // Click the button and verify the expected field is visible
                String buttonId = entry_to.getButtonId();
                String expectedFieldId = entry_to.getFieldId();
                logger.info("[testMenuButtonClick_All] Clicking " + buttonId + ", from " + buttonIdFrom + ", expecting " + expectedFieldId);
                clickMenuAndVerify(buttonId, expectedFieldId, "testMenuButtonClick_All");
                // go back to from again:
                navigateTo(buttonIdFrom);
            }
            // navigate to the next menu entry after testing all buttons from the current one
            if (i < menuEntries.size()-1)
            {
                navigateTo(menuEntries.get(i+1).getButtonId());
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