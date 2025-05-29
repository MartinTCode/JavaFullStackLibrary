package com.javafullstacklibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.javafullstacklibrary.frontend.guestControllers.StartViewGuestController;

/**
 * Test-specific version of MainApp that skips database initialization
 */
public class TestMainApp extends Application {

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
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            primaryStage.setTitle("JavaFX Library App - Test Mode");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}