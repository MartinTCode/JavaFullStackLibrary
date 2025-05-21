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

// Importing necessary classes for assertions
import static org.testfx.util.NodeQueryUtils.*;

// Importing necessary classes for logging
import com.javafullstacklibrary.utils.LoggerUtil;

// Importing necessary TestFX classes for assertions
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;


import java.util.logging.Logger;

public class StartViewGuestControllerTest extends ApplicationTest {

    private static final Logger logger = LoggerUtil.getFileLogger(StartViewGuestControllerTest.class, "StartViewGuestControllerTest.log");

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
        sleep(500);
    }

    // Helper to click a menu button and verify the expected node is visible
    private void clickMenuAndVerify(String buttonId, String expectedFieldId) {
        logger.info("Clicking " + buttonId + ", expecting " + expectedFieldId);
        clickOn(buttonId);
        sleep(500);
        verifyThat(expectedFieldId, isVisible());
    }

    @ParameterizedTest
    @CsvSource({
        "#searchMenuGuest, #searchField",
        "#signInMenuGuest, #ssnField"
    })
    void testMenuButtonClick_FromStart(String buttonId, String expectedFieldId) {
        clickMenuAndVerify(buttonId, expectedFieldId);
    }

    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeBox", 
        "#signInMenuGuest, #ssnField"
    })
    void testMenuButtonClick_FromSearch(String buttonId, String expectedFieldId) {
        navigateTo("#searchMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId);
    }

    @ParameterizedTest
    @CsvSource({
        "#homeMenuGuest, #welcomeBox",
        "#searchMenuGuest, #searchField",
        "#staffButton, #usernameField"
    })
    void testMenuButtonClick_FromSignInUser(String buttonId, String expectedFieldId) {
        navigateTo("#signInMenuGuest");
        clickMenuAndVerify(buttonId, expectedFieldId);
    }

    // TODO: try and make it one parameterized test for all buttons menu buttons.

    // Optionally, add a test for Sign_In_Staff as well

}