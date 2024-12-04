package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.SpaceShooterAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SpaceShooterApp extends Application {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);
    private final static String mainApp = "/fxml/MainApp_layout.fxml";
    private SpaceShooterAppController controller;
    public Scene scene;

    public double newX = 1000;
    public double newY = 800;


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            logger.info("Bootstrapping the application...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(mainApp));
            controller = new SpaceShooterAppController();
            loader.setController(controller);
            Pane root = loader.load();

            //  Attempt #3
//            primaryStage.widthProperty().addListener((obs, oldX, newX) -> {
//                if (!Objects.equals(oldX, newX)) {
//                    controller.bindScene(primaryStage);
//                }
//                System.out.println("New width: " + newX);
//            });
//
//            primaryStage.heightProperty().addListener((obs, oldY, newY) -> {
//                if (!Objects.equals(oldY, newY)) {
//                    controller.bindScene(primaryStage);
//                }
//                System.out.println("New height: " + newY);
//            });

            scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
            controller.setScene(scene);
            controller.setStage(primaryStage);
            controller.setupGameWorld();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Space Invaders!");
            primaryStage.sizeToScene();

//            root.minWidthProperty().bind(primaryStage.minWidthProperty());
//            root.minHeightProperty().bind(primaryStage.minHeightProperty());

            // Attempt #4
//            controller.newSceneDimensions(newX, newY);

            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);


            // Attempt #2
//            primaryStage.minWidthProperty().bind(scene.heightProperty().divide(1.5));
//            primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.5));

            // Attempt #1
//            primaryStage.setResizable(false);
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
