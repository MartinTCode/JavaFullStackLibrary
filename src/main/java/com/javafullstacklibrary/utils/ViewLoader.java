package com.javafullstacklibrary.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewLoader {

    /**
     * Base path for the FXML files.
     * This should be the path relative to the classpath.
     */
    private static final String BASE_PATH = "/com/javafullstacklibrary/frontend/";

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
        String path = buildFxmlPath(viewFolder, fxmlFile);
        Parent root = loadFxml(path, controller);
        Stage stage = getStageFromPane(sourcePane);
        setSceneToStage(stage, root);
    }

    /**
     * Builds the full path to the FXML file.
     *
     * @param viewFolder The folder inside frontend
     * @param fxmlFile   The FXML file name
     * @return The full path to the FXML file
     */
    private static String buildFxmlPath(String viewFolder, String fxmlFile) {
        return BASE_PATH + viewFolder + "/" + fxmlFile;
    }

    /**
     * Loads the FXML file and sets the controller.
     *
     * @param path       The path to the FXML file
     * @param controller The controller instance
     * @return The loaded Parent node
     * @throws IOException if the FXML file is not found or cannot be loaded
     */
    private static Parent loadFxml(String path, Object controller) throws IOException {
        URL fxmlUrl = ViewLoader.class.getResource(path);
        if (fxmlUrl == null) {
            throw new IOException("FXML file not found at path: " + path);
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setController(controller);
        return loader.load();
    }

    /**
     * Retrieves the Stage from the given Pane.
     *
     * @param sourcePane The Pane from which the Stage is obtained
     * @return The Stage instance
     */
    private static Stage getStageFromPane(Pane sourcePane) {
        return (Stage) sourcePane.getScene().getWindow();
    }

    /**
     * Sets a new scene to the stage while preserving its size.
     *
     * @param stage The Stage to update
     * @param root  The root node for the new scene
     */
    private static void setSceneToStage(Stage stage, Parent root) {
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        Scene newScene = new Scene(root);
        stage.setScene(newScene);

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }
}
