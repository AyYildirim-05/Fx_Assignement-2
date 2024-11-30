package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.Util;
import edu.vanier.spaceshooter.support.LevelController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

public class SpaceShooterAppController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;

    @FXML
    HBox uiContainer;

    @FXML
    Label scoreLabel;

    @FXML
    Label stageLabel;

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
    public Util util;

    public int used_gun = 0;



    public void initialize() {
        levelController = new LevelController();
        util = new Util();
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip(levelController.getPlayer_spaceShip(), 20, 20, 3, "player", 300, 400);
        animationPanel.setPrefSize(600, 1000);
        animationPanel.getChildren().add(spaceShip);
        util.settingBackground(util.getBACKGROUND_IMAGE_1(), animationPanel);
    }

    public void setupGameWorld() {
        sceneActual.getStylesheets().add("/resources/css/MainAppStyle.css");
        initGameLoop();
        setupKeyPressHandlers();
        generateInvaders();
    }

    private void initGameLoop() {
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

    // todo how to implement a level progression
    private void update() {
        elapsedTime += 0.016;
        getSprites().forEach(this::processSprite);
        removeDeadSprites();

        if (input.contains("F")) {
            Stage stage = (Stage) sceneActual.getWindow();
            stage.setFullScreen(!stage.isFullScreen());
            input.remove("F");
        }
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
        if (input.contains("C")) {
            // todo needs to reset to 0
            if (used_gun < levelController.numberOfGuns) {
                used_gun += 1;
                System.out.println("gun: " + used_gun);
            } else {
                used_gun = 0;
            }
            input.remove("C");
        }
        if(input.contains("SPACE")) {
            shoot(spaceShip);
        }
//        if(input.contains("SPACE")){
//            shooting(spaceShip, used_gun);
//        }

        if (elapsedTime > 2) {
            elapsedTime = 0;
        }

    }

    private void generateInvaders() {
        for (int i = 0; i < 5; i++) {
            invader = new Invader(levelController.getSmall_Enemy(), 35, 35, levelController.getHealth_small_Invader(), "enemy",
                    90 + i * 100, 500);
            animationPanel.getChildren().addAll(invader);
        }
    }


    private List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Sprite sprite) {
                spriteList.add(sprite);
            }
        }
        return spriteList;
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
        sprite.moveDown(levelController.getSpeedValue());
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            System.out.println("Collision detected!");
            spaceShip.lose_health();
            System.out.println("SpaceShip health: " + spaceShip.getHealth());
            if (spaceShip.checkHealth()) {
                spaceShip.setDead(true);
                System.out.println("Sprite marked as dead.");

            }
            sprite.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.moveUp(levelController.getSpeedValue());
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.lose_health();
                    if (enemy.checkHealth()) {
                        enemy.setDead(true);
                        levelController.score += 1;
                        System.out.println(levelController.score);
                    }
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
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 500) {
            missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet", (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth()/2),
                    (int) (firingEntity.getTranslateY() -  firingEntity.getFitHeight()/2));
            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    private void shooting(Sprite firingEntity, int weapon) {
        switch (weapon) {
            case 1:
                singleShot(firingEntity);
                System.out.println("weapon 1");
            case 2:
                doubleShot(firingEntity);
                System.out.println("weapon 2");
        }
    }

    public  void singleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 500) {
            missile = new Missile(levelController.blueMissile_1, 20, 20, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet", (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth()/2),
                    (int) (firingEntity.getTranslateY() -  firingEntity.getFitHeight()/2));
            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    public void doubleShot(Sprite firingEntity) {
        missile = new Missile(levelController.blueMissile_1, 20, 20, levelController.getHealth_missile(),
                firingEntity.getType() + "bullet",
                (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 3),
                (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));
        animationPanel.getChildren().add(missile);

        missile = new Missile(levelController.blueMissile_1, 20, 20, levelController.getHealth_missile(),
                firingEntity.getType() + "bullet",
                (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));
        animationPanel.getChildren().add(missile);

        levelController.setLastShot(System.currentTimeMillis());
    }

    public void tripleShot(Sprite firingEntity) {
        singleShot(firingEntity);

        Missile leftMissile = new Missile(levelController.blueMissile_1, 20, 20, levelController.getHealth_missile(),
                firingEntity.getType() + "bullet",
                (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));
        // todo how to angle the sprite


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
