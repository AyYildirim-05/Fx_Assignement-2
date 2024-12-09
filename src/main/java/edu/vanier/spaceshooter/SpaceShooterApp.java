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
    private final static String startApp = "/fxml/startPage_layout.fxml";
    private final static String endApp = "/fxml/endGame_layout.fxml";
    private SpaceShooterAppController controller;
    public Scene scene;

    private Stage stage;


    // todo create a stage before lunching the game
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            logger.info("Bootstrapping the application...");
            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource(mainApp));
            controller = new SpaceShooterAppController();
            gameLoader.setController(controller);
            Pane root = gameLoader.load();

            scene = new Scene(root, 1000, 800);

            primaryStage.widthProperty().addListener((obs, oldX, newX) -> {
                if (!Objects.equals(oldX, newX)) {
                    controller.bindSceneWidth(primaryStage);
                }
            });

            primaryStage.heightProperty().addListener((obs, oldY, newY) -> {
                if (!Objects.equals(oldY, newY)) {
                    controller.bindSceneHeight(primaryStage);
                }
            });

            controller.setScene(scene);
            controller.setStage(primaryStage);
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
        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startScene() {
        FXMLLoader startLoader = new FXMLLoader(getClass().getResource(startApp));


    }

    public static void gameEndScene() {

    }
}
