package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.SpaceShooterAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

public class SpaceShooterApp extends Application {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);
    private final static String mainApp = "/fxml/MainApp_layout.fxml";
    private SpaceShooterAppController controller;
    public Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            logger.info("Bootstrapping the application...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(mainApp));
            controller = new SpaceShooterAppController();
            loader.setController(controller);
            Pane root = loader.load();
            scene = new Scene(root, 1000, 1000);
            controller.setScene(scene);
//            controller.setupGameWorld();
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
        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
