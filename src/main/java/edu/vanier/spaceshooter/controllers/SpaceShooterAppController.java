package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.SpaceShip;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class SpaceShooterAppController { // go to previous submission: "in class modification done in case missing code"
    @FXML
    Pane animationPanel;

    private Scene scene;
    private long lastNanoTime = System.nanoTime();
    private AnimationTimer animation;

    public static final String BACKGROUND_IMAGE_1 = "/images/background/blue.png";

    public void initialize() {
        settingBackground(BACKGROUND_IMAGE_1);
        SpaceShip spaceShip = new SpaceShip("/images/playerShip1_red.png", 20, 20, "player", 300, 400);

        animationPanel.getChildren().add(spaceShip.getImageView());


        animation = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                List<String> input = new ArrayList<>();
                spaceShip.setVelocity(0, 0);

                if (input.contains("LEFT")) {
                    spaceShip.addVelocity(-250, 0);
                }
                if (input.contains("RIGHT")) {
                    spaceShip.addVelocity(250, 0);
                }
                if (input.contains("UP")) {
                    spaceShip.addVelocity(0, -250);
                }
                if (input.contains("DOWN")) {
                    spaceShip.addVelocity(0, 250);
                }

                spaceShip.update(elapsedTime);
            }
        };
        animation.start();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        initializeKeyListeners();
    }

    private void initializeKeyListeners() {
        List<String> input = new ArrayList<>();

        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }

    public void settingBackground(String backgroundColor) {
        Image backgroundImage = new Image(getClass().getResource(backgroundColor).toExternalForm());

        BackgroundSize backgroundSize = new BackgroundSize(
                animationPanel.getWidth(),
                animationPanel.getHeight(),
                false,
                false,
                true,
                true);

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        animationPanel.setBackground(new Background(bgImage));
    }
}
