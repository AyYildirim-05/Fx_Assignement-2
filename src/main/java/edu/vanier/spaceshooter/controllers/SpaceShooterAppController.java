package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.LevelController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SpaceShooterAppController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;
    private Scene sceneActual;

    private long lastNanoTime = System.nanoTime();
    private double elapsedTime = 0;
    private AnimationTimer gameLoop;
    private List<String> input = new ArrayList<>();
    public SpaceShip spaceShip;
    public Invader invader;
    public Obstacles obstacles;
    public Missile missile;
    public LevelController levelController;


    public void initialize() {
        levelController = new LevelController();
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip(levelController.getPlayer_spaceShip(), 20, 20, 3, "player", 300, 400);
        animationPanel.setPrefSize(600, 1000);
        animationPanel.getChildren().add(spaceShip);
        System.out.println("looool");
    }

    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
        generateInvaders();
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

    private void setupKeyPressHandlers() {
        sceneActual.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        sceneActual.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
            update();
        });



    }

    private void generateInvaders() {
        for (int i = 0; i < 5; i++) {
            Invader invader = new Invader(levelController.getSmall_Enemy(), 35, 35, levelController.getHealth_small_Invader(), "enemy",
                    90 + i * 100, 500);
            animationPanel.getChildren().add(invader);
        }
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


    private void update() {
        elapsedTime += 0.016;
        // Actions to be performed during each frame of the animation.
        getSprites().forEach(this::processSprite);
        removeDeadSprites();

        // game logic
        if (input.contains("LEFT") || input.contains("A")) {
            spaceShip.moveLeft(levelController.getSpeedValue());
        }
        if (input.contains("RIGHT") || input.contains("D")) {
            spaceShip.moveRight(levelController.getSpeedValue());
        }
        if (input.contains("UP") || input.contains("W")) {
            spaceShip.moveUp(levelController.getSpeedValue());
        }
        if (input.contains("DOWN") || input.contains("S")) {
            spaceShip.moveDown(levelController.getSpeedValue());
        }
        if(input.contains("SPACE")){
            shoot(spaceShip);

//            long currentTime = System.currentTimeMillis();
//            if (currentTime - levelController.lastShot >= levelController.getCOOLDOWN()) {
//                if(!spaceShip.isDead()){
//                    shoot(spaceShip);
//                }
//                levelController.lastShot = currentTime;
//            }
        }

        if (elapsedTime > 2) {
            elapsedTime = 0;
        }

    }

    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" ->
                    handleEnemyBullet(sprite);
            case "playerbullet" ->
                    handlePlayerBullet(sprite);
            case "enemy" ->
                    handleEnemyFiring(sprite);
        }
    }

    private void handleEnemyBullet(Sprite sprite) {
        sprite.moveDown();
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
                shoot(sprite);
            }
        }
    }

    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            Sprite sprite = (Sprite) n;
            return sprite.isDead();
        });
    }


    private void shoot(Sprite firingEntity) {
        missile = new Missile(levelController.blueMissile_1, 20, 20, levelController.getHealth_missile(),
                firingEntity.getType() + "bullet",(int) (firingEntity.getTranslateX() + firingEntity.getFitWidth()/2),
                (int) (firingEntity.getTranslateY() - 10));
        animationPanel.getChildren().add(missile);
    }

    public void setScene(Scene scene) {
        sceneActual = scene;
    }

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

}
