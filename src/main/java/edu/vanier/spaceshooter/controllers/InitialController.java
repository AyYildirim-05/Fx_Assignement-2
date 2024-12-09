package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class InitialController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);
    @FXML
    StackPane startPane;
    @FXML
    VBox vertContainer;
    @FXML
    Button startButton;
    @FXML
    Button howTo;
    private final static String mainApp = "/fxml/MainApp_layout.fxml";
    public Scene scene;
    private SpaceShooterApp app;
    private SpaceShooterAppController controller;
    private Stage stage;

    @FXML
    public void initialize() {

        startButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            try {
                logger.info("Bootstrapping the application...");
                FXMLLoader gameLoader = new FXMLLoader(getClass().getResource(mainApp));
                SpaceShooterAppController controller = new SpaceShooterAppController();
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
        });
    }

}
