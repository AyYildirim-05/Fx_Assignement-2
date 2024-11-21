package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceShooterApp extends Application {

    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);
    MainAppFXMLController controller;

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Bootstrapping the application...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
            controller = new MainAppFXMLController();
            loader.setController(controller);
            Pane root = loader.load();

            Scene scene = new Scene(root, 600, 1000);
//            scene.getStylesheets().add("style.css");

            controller.setScene(scene);
            controller.setupGameWorld();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Space Invaders!");

            primaryStage.sizeToScene();
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void stop() throws Exception {
        // Stop the animation timer upon closing the main stage.
        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
