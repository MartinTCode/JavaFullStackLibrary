package com.javafullstacklibrary;

import com.javafullstacklibrary.exception.ValidationException;
import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;
import com.javafullstacklibrary.services.AuthenticationService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void init() throws Exception {
        // This method is called before start() - perfect for initialization
        super.init();
        initializeApplication();
    }

    @Override
    public void start(Stage primaryStage) {
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
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            // set title of window.
            primaryStage.setTitle("JavaFX Library App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the application by setting up test data passwords
     */
    private void initializeApplication() {
        try {
            System.out.println("Initializing JavaFX Library Application...");
            
            AuthenticationService authService = new AuthenticationService();
            
            // Initialize test data passwords
            boolean initialized = authService.initializeAndVerifyTestData();
            
            if (initialized) {
                System.out.println("✓ Test data passwords have been initialized and hashed");
            } else {
                System.out.println("✓ Passwords were already properly hashed - no initialization needed");
            }
            
            System.out.println("✓ Application initialization completed successfully");
            System.out.println("----------------------------------------");
            
        } catch (ValidationException e) {
            System.err.println("✗ Failed to initialize application:");
            System.err.println("  Error: " + e.getMessage());
            
            if (e.hasFieldErrors()) {
                e.getFieldErrors().forEach((field, error) -> 
                    System.err.println("  " + field + ": " + error));
            }
            
            System.err.println("----------------------------------------");
            System.err.println("Application will continue, but login may not work properly");
            
        } catch (Exception e) {
            System.err.println("✗ Unexpected error during application initialization:");
            e.printStackTrace();
            System.err.println("Application will continue, but there may be issues");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}