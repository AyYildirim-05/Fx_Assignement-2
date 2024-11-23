package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.SpaceShip;
import edu.vanier.spaceshooter.models.Sprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

public class SpaceShooterAppController { // go to previous submission: "in class modification done in case missing code"
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;
    private Scene sceneActual;
    private long lastNanoTime = System.nanoTime();
    private AnimationTimer gameLoop;
    public int health_player = 3;
    public int small_obstacle = 1;
    public int medium_obstacle = 2;
    public int big_obstacle = 3;
    public int small_invader = 1;
    public int medium_invader = 2;
    public int big_invader = 3;
    Sprite spaceShip;
    int health_missile = 1;
    private double elapsedTime = 0;
    private List<KeyCode> input = new ArrayList<>();
    private int score = 0;
    public static final String BACKGROUND_IMAGE_1 = "/images/background/blue.png";
    public static final String BACKGROUND_IMAGE_2 = "/images/background/purple.png";
    public static final String BACKGROUND_IMAGE_3 = "/images/background/darkPurple.png";
    public static final String BACKGROUND_IMAGE_4 = "/images/background/black.png";
    public Canvas canvas;
    public GraphicsContext gc;

    public void initialize() {
        System.out.println("is it being done?");
        canvas = new Canvas(600,1000);
        gc = canvas.getGraphicsContext2D();
        logger.info("Initializing MainAppController...");
        settingBackground(BACKGROUND_IMAGE_2);
        spaceShip = new SpaceShip("/images/playerShip1_red.png", 20, 20, 1, "player", 300, 400);

        animationPanel.getChildren().add(spaceShip.getImageView());

        initializeGameLoop();
    }

    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                spaceShip.setVelocity(0, 0);

                // Handle movement based on key inputs
                if (input.contains(KeyCode.A) || input.contains(KeyCode.LEFT)) {
                    spaceShip.addVelocity(-250, 0);
                }
                if (input.contains(KeyCode.D) || input.contains(KeyCode.RIGHT)) {
                    spaceShip.addVelocity(250, 0);
                }
                if (input.contains(KeyCode.W) || input.contains(KeyCode.UP)) {
                    spaceShip.addVelocity(0, -250);
                }
                if (input.contains(KeyCode.S) || input.contains(KeyCode.DOWN)) {
                    spaceShip.addVelocity(0, 250);
                }

                spaceShip.update(elapsedTime);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                spaceShip.render(gc);
            }
        };
        gameLoop.start();
    }

    public void setScene(Scene scene) {
        this.sceneActual = scene;
        initializeKeyListeners();
    }

    private void initializeKeyListeners() {
        List<String> input = new ArrayList<>();

        sceneActual.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        sceneActual.setOnKeyReleased((KeyEvent e) -> {
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

//    private void initGameLoop() {
//        // Create the game loop.
//        gameLoop = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                update();
//            }
//        };
//        gameLoop.start();
//    }

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
            spaceShip.setDead(true);
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

    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            if (Math.random() < 0.3) {
                sprite.shoot();
            }
        }
    }

    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" -> handleEnemyBullet(sprite);
            case "playerbullet" -> handlePlayerBullet(sprite);
            case "enemy" -> handleEnemyFiring(sprite);
        }
    }


//    public void stopAnimation() {
//        if (gameLoop != null) {
//            gameLoop.stop();
//        }
//    }
}
