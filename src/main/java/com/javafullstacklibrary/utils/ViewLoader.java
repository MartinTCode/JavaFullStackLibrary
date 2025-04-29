package com.javafullstacklibrary.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewLoader {

    private static final String BASE_PATH = "/com/javafullstacklibrary/frontend/";

    /**
     * Loads a view from a given subfolder and FXML file name.
     *
     * @param viewFolder The folder inside frontend, e.g., "adminViews"
     * @param fxmlFile   The FXML file name, e.g., "Dashboard.fxml"
     * @return Parent node of the loaded FXML
     * @throws IOException if the FXML file is not found or cannot be loaded
     */
    public static Parent load(String viewFolder, String fxmlFile) throws IOException {
        String path = BASE_PATH + viewFolder + "/" + fxmlFile;
        URL fxmlUrl = ViewLoader.class.getResource(path);

        if (fxmlUrl == null) {
            throw new IOException("FXML file not found at path: " + path);
        }

        return FXMLLoader.load(fxmlUrl);
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
    
        // Get the stage from the sourcePane (e.g., a button or anchor pane in current scene)
        Stage stage = (Stage) sourcePane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    

}
