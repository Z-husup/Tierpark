package com.prog.tierpark;

import atlantafx.base.theme.NordLight;
import com.prog.tierpark.database.DatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main JavaFX application class for the "Tierpark" system.
 * <p>
 * This class initializes the primary stage, connects to the database,
 * and supports dynamic scene switching with scaling based on window size.
 */
public class Application extends javafx.application.Application {

    /**
     * The primary stage of the application.
     */
    public static Stage mainStage;

    /**
     * Wrapper pane for scaling content.
     */
    private static StackPane rootWrapper;

    /**
     * Group used to scale the application's main content.
     */
    private static Group scaledGroup;

    /**
     * The base width used for calculating scaling factor.
     */
    private static final double BASE_WIDTH = 1045;

    /**
     * The base height used for calculating scaling factor.
     */
    private static final double BASE_HEIGHT = 720;

    /**
     * Main entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the JavaFX application.
     * <p>
     * Attempts to establish a connection to the MSSQL database before
     * initializing and displaying the GUI.
     *
     * @param stage The primary JavaFX stage.
     * @throws Exception If FXML loading fails.
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Test MSSQL database connection
        try (Connection connection = DatabaseManager.getConnection()) {
            System.out.println("✅ MSSQL соединение установлено!");
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения:");
            e.printStackTrace();
        }

        mainStage = stage;

        // Apply custom stylesheet theme
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        // Load initial view
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("start-view.fxml"));
        Parent content = loader.load();

        // Wrap content for scaling
        Scene scene = wrapAndScale(content, stage);

        stage.setTitle("Tierpark");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    /**
     * Wraps and scales the content node inside a responsive group.
     *
     * @param content The root node to be scaled.
     * @param stage   The primary stage to bind resizing to.
     * @return A scene with the scaled content.
     */
    private static Scene wrapAndScale(Parent content, Stage stage) {
        scaledGroup = new Group(content);
        rootWrapper = new StackPane(scaledGroup);

        Scene scene = new Scene(rootWrapper);

        // Resize listeners to maintain aspect ratio
        stage.widthProperty().addListener((obs, oldVal, newVal) ->
                scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT));
        stage.heightProperty().addListener((obs, oldVal, newVal) ->
                scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT));

        // Initial scaling
        scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT);

        return scene;
    }

    /**
     * Scales the content group based on the current window size.
     *
     * @param stage      The primary application window.
     * @param group      The group to scale.
     * @param baseWidth  The reference base width.
     * @param baseHeight The reference base height.
     */
    private static void scaleContent(Stage stage, Group group, double baseWidth, double baseHeight) {
        double scaleX = stage.getWidth() / baseWidth;
        double scaleY = stage.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY);
        group.setScaleX(scale);
        group.setScaleY(scale);
    }

    /**
     * Switches the current scene content to a new FXML file.
     * <p>
     * The new content replaces the current content inside the scaled group.
     *
     * @param fxmlPath The relative path to the new FXML view.
     */
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent newContent = loader.load();
            scaledGroup.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

