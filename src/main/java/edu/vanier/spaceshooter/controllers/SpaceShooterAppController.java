package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.LevelController;
import edu.vanier.spaceshooter.support.Util;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceShooterAppController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;

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
    int randomNumber;
    Random random = new Random();
    private long lastEnemyMoveTime = 0;


    // todo how to setup game animation
    public void initialize() {
        // todo how to set up the score
//        scoreLabel = new Label("Score: " + levelController.getScore());
        levelController = new LevelController();
        util = new Util();
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip(levelController.getPlayer_spaceShip(), 30, 30, levelController.getHealth_player(), "player", 400, 600);
        animationPanel.setPrefSize(1000, 800);
//        scoreLabel.setLayoutX(50);
//        scoreLabel.setLayoutY(50);
        animationPanel.getChildren().addAll(spaceShip);
        util.settingBackground(util.getBACKGROUND_IMAGE_1(), animationPanel);

    }

    public void setupGameWorld() {
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

    // todo how to limit the movement of invaders within the animationPanel
    private void update() {
        elapsedTime += 0.016;
        getSprites().forEach(this::processSprite);
        removeDeadSprites();
        moveInvaders();

//        updateUI();

        if (!areEnemiesRemaining()) {
            // increase player speed, enemy speed. the type of missile player can fire depending on the stage resetting the player
            System.out.println("No enemies remaining. Level cleared!");
            // todo how to make so that this doesnt run infinitely
        }

        if (input.contains("F")) {
            Stage stage = (Stage) sceneActual.getWindow();
            stage.setFullScreen(!stage.isFullScreen());
            input.remove("F");
        }
        // 2 allows the fins to show without going out
        if (input.contains("LEFT") || input.contains("A")) {
            if (spaceShip.getTranslateX() >= 2 && spaceShip.getTranslateX() < sceneActual.getWidth()) {
                spaceShip.moveLeft(levelController.getSpeedSpaceShip());
            }
        }
        // -2 i don't know why & -30 allows the wing to show
        if (input.contains("RIGHT") || input.contains("D")) {
            if (spaceShip.getTranslateX() >= -2 && spaceShip.getTranslateX() < sceneActual.getWidth() - 30) {
                spaceShip.moveRight(levelController.getSpeedSpaceShip());
            }
        }
        if (input.contains("UP") || input.contains("W")) {
            if (spaceShip.getTranslateY() >= 2 && spaceShip.getTranslateY() < sceneActual.getHeight()) {
                spaceShip.moveUp(levelController.getSpeedSpaceShip());
            }
        }
        if (input.contains("DOWN") || input.contains("S")) {
            if (spaceShip.getTranslateY() >= 0 && spaceShip.getTranslateY() < sceneActual.getHeight() - 30) {
                spaceShip.moveDown(levelController.getSpeedSpaceShip());
            }
        }
        // todo ask the teacher about the gun logic
        if (input.contains("C")) {
            if (used_gun < levelController.numberOfGuns) {
                used_gun += 1;
                System.out.println("gun: " + used_gun);
            } else {
                used_gun = 0;
            }
            input.remove("C");
        }
        if(input.contains("SPACE")){
            shooting(spaceShip, used_gun);
        }

        if (elapsedTime > 2) {
            elapsedTime = 0;
        }

    }

    private void generateInvaders() {
        for (int i = 0; i < 5; i++) {
            invader = new Small_Invader(levelController.getSmall_Enemy(), 35, 35, levelController.getHealth_small_Invader(), "enemy",
                    90 + i * 100, 500);
            animationPanel.getChildren().addAll(invader);
        }
    }



    private void moveInvaders() {
        long now = System.currentTimeMillis();
        if (now - lastEnemyMoveTime > 200) {
            for (Node n : animationPanel.getChildren()) {
                if (n instanceof Small_Invader smallInvader) {
                    randomNumber = random.nextInt(8);
                    switch (randomNumber) {
                        case 0 -> smallInvader.moveSprite(levelController.getSpeedInvader(), 0); // move right
                        case 1 -> smallInvader.moveSprite(-levelController.getSpeedInvader(), 0); // move left
                        case 2 -> smallInvader.moveSprite(0, levelController.getSpeedInvader()); // move down
                        case 3 -> smallInvader.moveSprite(0, -levelController.getSpeedInvader()); // move up
                        case 4 ->
                                smallInvader.moveSprite(levelController.getSpeedInvader(), levelController.getSpeedInvader()); // right down
                        case 5 ->
                                smallInvader.moveSprite(-levelController.getSpeedInvader(), levelController.getSpeedInvader()); // left down
                        case 6 ->
                                smallInvader.moveSprite(levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // right up
                        case 7 ->
                                smallInvader.moveSprite(-levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // left up
                    }
                }
            }
            lastEnemyMoveTime = now;
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
        sprite.moveDown(levelController.getSpeedSpaceShip());
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
        sprite.moveUp(levelController.getSpeedSpaceShip());
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.lose_health();
                    if (enemy.checkHealth()) {
                        enemy.setDead(true);
                        if (enemy instanceof Small_Invader) {
                            levelController.score += 1;
                            System.out.println(levelController.score);
                        } else if (enemy instanceof Medium_Invader) {
                            levelController.score += 3;
                            System.out.println(levelController.score);
                        } else if (enemy instanceof Big_Invader) {
                            levelController.score += 5;
                            System.out.println(levelController.score);
                        }
                    }
                }
            }
        }
    }


    private void handleEnemyFiring(Sprite sprite) {
        // if i decrease the value of 2, they fire more frequently
        if (elapsedTime > 2) {
            if (Math.random() < 0.9) {
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
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet", (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth()/2),
                    (int) (firingEntity.getTranslateY() -  firingEntity.getFitHeight()/2));
            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    private void shooting(Sprite firingEntity, int weapon) {
        switch (weapon) {
            case 0:
                singleShot(firingEntity);
                System.out.println("weapon 1");
            case 1:
                doubleShot(firingEntity);
                System.out.println("weapon 2");
            case 2:
                customTripleShoot(spaceShip, 50, -50, -30,-30);
                System.out.println("weapon 3");
        }
    }

    public  void singleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet", (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth()/2),
                    (int) (firingEntity.getTranslateY() -  firingEntity.getFitHeight()/2));
            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    public void doubleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));
            animationPanel.getChildren().add(missile);

            missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));
            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    public void customTripleShoot(Sprite firingEntity, int leftX, int LefY, int RightX, int RightY) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            singleShot(firingEntity);

            Missile leftMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));

            leftMissile.moveSprite(-leftX, LefY);

            Missile rightMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2));

            rightMissile.moveSprite(RightX, RightY);
            animationPanel.getChildren().addAll(leftMissile, rightMissile);
            levelController.setLastShot(now);
        }
    }

    public void tripleSpread(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            singleShot(firingEntity);



            animationPanel.getChildren().addAll();
            levelController.setLastShot(now);
        }
    }

    public void setScene(Scene scene) {
        sceneActual = scene;
    }

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    private boolean areEnemiesRemaining() {
        for (Sprite sprite : getSprites()) {
            if (sprite.getType().equals("enemy")) {
                return true;
            }
        }
        return false;
    }

}
