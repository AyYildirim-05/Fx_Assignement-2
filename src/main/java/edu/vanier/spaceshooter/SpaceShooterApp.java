package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.InitialController;
import edu.vanier.spaceshooter.controllers.SpaceShooterAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private final static String startApp = "/fxml/startPage_layout.fxml";
    private SpaceShooterAppController controller;

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


    @Override
    public void stop() throws Exception {
        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
