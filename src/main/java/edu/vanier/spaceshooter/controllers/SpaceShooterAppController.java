package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SpaceShooterAppController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;
    private Scene sceneActual;

    private long lastNanoTime = System.nanoTime();
    private double elapsedTime = 0;
    private AnimationTimer gameLoop;

    private List<KeyCode> input = new ArrayList<>();
    private int score = 0;

    public SpaceShip spaceShip;
    public Invader invader;
    public Obstacles obstacles;
    public Missile missile;
    public LevelController levelController;

    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip("player/playerShip1_red.png", 20, 3, "player", 300, 400);
        animationPanel.setPrefSize(600, 1000);
        animationPanel.getChildren().add(spaceShip);
    }
    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
//        generateInvaders();
    }


    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                spaceShip.setVelocity(0, 0);

                setupKeyPressHandlers();

                spaceShip.update(elapsedTime);
            }
        };
        gameLoop.start();
    }

    private void setupKeyPressHandlers() {
        // e the key event containing information about the key pressed.
        sceneActual.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> spaceShip.addVelocity(-250, 0);
                case A -> spaceShip.addVelocity(250, 0);
                case S -> spaceShip.addVelocity(0, -250);
                case D -> spaceShip.addVelocity(0, 250);
            }
        });

    }

    public void setScene(Scene scene) {
        this.sceneActual = scene;
        initializeKeyListeners();
    }

    private void initializeKeyListeners() {
        input = new ArrayList<>();

        sceneActual.setOnKeyPressed((KeyEvent e) -> {
            KeyCode code = e.getCode();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        sceneActual.setOnKeyReleased((KeyEvent e) -> {
            KeyCode code = e.getCode();
            input.remove(code);
        });
    }



    private List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Sprite sprite) {
                // We should add to the list any node that is a Sprite object.
                spriteList.add(sprite);
            }
        }
        return spriteList;
    }

    private void initGameLoop() {
        // Create the game loop.
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void update() {
        elapsedTime = 0.016;
        // Actions to be performed during each frame of the animation.
        getSprites().forEach(this::processSprite);
        removeDeadSprites();
        // Reset the elapsed time.
        if (elapsedTime > 2) {
            elapsedTime = 0;
        }
    }

    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            Sprite sprite = (Sprite) n;
            return sprite.isDead();
        });
    }

    private void handleEnemyBullet(Sprite sprite) {
        sprite.moveDown();
        // Check for collision with the spaceship
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            spaceShip.lose_health();
            sprite.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.moveUp();
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                // Check for collision with an enemy
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.setDead(true);
                    sprite.setDead(true);
                }
            }
        }
    }

//    private void handleEnemyFiring(Sprite sprite) {
//        if (elapsedTime > 2) {
//            if (Math.random() < 0.3) {
//                sprite.shoot();
//            }
//        }
//    }

    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" -> handleEnemyBullet(sprite);
            case "playerbullet" -> handlePlayerBullet(sprite);
//            case "enemy" -> handleEnemyFiring(sprite);
        }
    }


    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
