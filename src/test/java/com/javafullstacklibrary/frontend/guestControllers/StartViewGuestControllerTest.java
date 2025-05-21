package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import static org.testfx.util.NodeQueryUtils.*;

import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;


import java.util.logging.Logger;

public class StartViewGuestControllerTest extends ApplicationTest {

    private static final Logger logger = Logger.getLogger(StartViewGuestController.class.getName());

    // Static block to configure logging to a file
    static {
        try {
            FileHandler fh = new FileHandler("StartViewGuestControllerTest.log", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(java.util.logging.Level.ALL);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false); // Prevents logging to console
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Test
    void testClickedSearchMenuGuest() {
        logger.info("About to click #searchMenuGuest");
        clickOn("#searchMenuGuest");
        logger.info("Clicked #searchMenuGuest, checking for #searchField");
        sleep(500);
        verifyThat("#searchField", isVisible());
    }

    @Test
    void testClickedSignInMenuGuest() {
        // Simulate clicking the Sign In button
        clickOn("#signInMenuGuest");
        // Add assertions here to verify the result of the click
        sleep(500); // Wait 500ms to allow the new node to load
        verifyThat("#ssnField", isVisible());
    }
}