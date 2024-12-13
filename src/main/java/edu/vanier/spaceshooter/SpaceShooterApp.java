package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.InitialController;
import edu.vanier.spaceshooter.controllers.SpaceShooterAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Launch location of the application
 */
public class SpaceShooterApp extends Application {
    private final static String startApp = "/fxml/startPage_layout.fxml";
    private SpaceShooterAppController controller;

    /**
     * Method that creates the main page of the application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader startLoader = new FXMLLoader(getClass().getResource(startApp));
        InitialController initialController = new InitialController();
        startLoader.setController(initialController);
        Parent root = startLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(getClass().getResource("/css/MainAppStyle.css"));
    }

    /**
     * Stops the application
     */
    @Override
    public void stop() {
        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
