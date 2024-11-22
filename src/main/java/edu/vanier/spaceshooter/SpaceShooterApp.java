package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.SpaceShooterAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SpaceShooterApp extends Application {

    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);
    private final static String mainApp = "MainApp_layout";

    public static Scene scene;


    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("Bootstrapping the application...");
        SpaceShooterAppController controller = new SpaceShooterAppController();
        Pane root = (Pane) loadFXML(mainApp, controller);
        scene = new Scene(root, 600, 1000);
        primaryStage.setScene(scene);

        primaryStage.sizeToScene();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
        primaryStage.setAlwaysOnTop(false);

    }

    @Override
    public void stop() throws Exception {
        // Stop the animation timer upon closing the main stage.
//        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Parent loadFXML(String fxmlFile, Object fxmlController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceShooterApp.class.getResource("/fxml/" + fxmlFile + ".fxml"));
        fxmlLoader.setController(fxmlController);
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
