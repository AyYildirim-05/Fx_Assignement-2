package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;

/**
 * Class that controls the actions defined in the main page of the game.
 * Starts the game loop.
 */

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
    private final static String keyBinds = "/background/HelpGuide.png";
    public Scene scene;
    private SpaceShooterApp app;
    private SpaceShooterAppController controller;
    private Stage stage;

    @FXML
    public void initialize() {

        startButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            try {
                logger.info("Bootstrapping the application...");
                FXMLLoader gameLoader = new FXMLLoader(getClass().getResource(mainApp));
                SpaceShooterAppController controller = new SpaceShooterAppController();
                gameLoader.setController(controller);
                Pane root = gameLoader.load();

                scene = new Scene(root, 1000, 800);
                scene.getStylesheets().add("/css/MainAppStyle.css");


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

        howTo.setOnAction(event -> {
            Stage helpStage = new Stage();
            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.setTitle("How to Play");

            try {
                ImageView helpImageView = new ImageView(getClass().getResource(keyBinds).toExternalForm());
                helpImageView.setPreserveRatio(true);

                helpImageView.setFitWidth(600);

                StackPane imagePane = new StackPane(helpImageView);

                Scene helpScene = new Scene(imagePane, 600, 300);
                helpStage.setScene(helpScene);
                helpStage.show();
            } catch (Exception ex) {
                logger.error("Error loading help guide: " + ex.getMessage(), ex);
            }

        });
    }

}
