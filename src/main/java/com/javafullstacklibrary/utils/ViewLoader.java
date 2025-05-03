package com.javafullstacklibrary.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class ViewLoader {

    // Debug flag for logging
    private static final boolean DEBUG = true;

    private static final String BASE_PATH = "/com/javafullstacklibrary/frontend/";

    private static void debugPrintln(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }
    /**
     * Loads a view and sets it to the current stage.
     *
     * @param sourcePane  The pane from which the stage is obtained
     * @param viewFolder  The folder inside frontend, e.g., "adminViews"
     * @param fxmlFile    The FXML file name, e.g., "Dashboard.fxml"
     * @param controller  The controller instance to be set for the FXML
     * @throws IOException if the FXML file is not found or cannot be loaded
     */
    public static void loadToStage(Pane sourcePane, String viewFolder, String fxmlFile, Object controller) throws IOException {
        String path = BASE_PATH + viewFolder + "/" + fxmlFile;
        URL fxmlUrl = ViewLoader.class.getResource(path);
    
        if (fxmlUrl == null) {
            throw new IOException("FXML file not found at path: " + path);
        }
    
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setController(controller);
        Parent root = loader.load();
    
        // Get the stage from the sourcePane
        Stage stage = (Stage) sourcePane.getScene().getWindow();

        // Preserve the current window size
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        // Set the new scene
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

        // Restore the previous window size
        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);

        stage.show();
    }
    
    /**
     * Loads a view, sets it to the current stage, and allows passing arguments to the controller.
     *
     * @param sourcePane  The pane from which the stage is obtained
     * @param viewFolder  The folder inside frontend, e.g., "adminViews"
     * @param fxmlFile    The FXML file name, e.g., "Dashboard.fxml"
     * @param controllerInitializer A lambda or consumer to initialize the controller
     * @throws IOException if the FXML file is not found or cannot be loaded
     */
    public static void loadToStageWithInit(
        Pane sourcePane, 
        String viewFolder, 
        String fxmlFile,
        Consumer<Object> controllerInitializer
    ) 
    throws IOException 
    {
        debugPrintln("Entered loadToStageWithInit method");
        String path = BASE_PATH + viewFolder + "/" + fxmlFile;
        URL fxmlUrl = ViewLoader.class.getResource(path);

        if (fxmlUrl == null) {
            throw new IOException("FXML file not found at path: " + path);
        }

        // Create an FXMLLoader instance
        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        // Dynamically set the controller using the controllerInitializer
        loader.setControllerFactory(param -> {
            try {
                // Dynamically create the controller instance
                Object controller = param.getDeclaredConstructor().newInstance();
                debugPrintln("Controller created: " + controller.getClass().getName());
                if (controllerInitializer != null) {
                    controllerInitializer.accept(controller); // Initialize the controller
                    debugPrintln("Controller initialized");
                }
                return controller;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create controller instance", e);
            }
        });
        debugPrintln("Controller factory set");

        // Load the FXML file
        Parent root = loader.load();
        debugPrintln("FXML loaded");

        // Get the stage from the sourcePane
        Stage stage = (Stage) sourcePane.getScene().getWindow();

        // Preserve the current window size
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        // Set the new scene
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

        // Restore the previous window size
        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);

        stage.show();
    }

}
