package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.LevelController;
import edu.vanier.spaceshooter.support.Util;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceShooterAppController {
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;

    @FXML
    StackPane HUD;

    @FXML
    Label scoreLabel;

    @FXML
    Label stageLabel;

    private Scene sceneActual;

    private double elapsedTime = 0;
    private AnimationTimer gameLoop;
    private List<KeyCode> input = new ArrayList<>();
    public SpaceShip spaceShip;
    public Invader invader;
    public Obstacles obstacles;
    public Missile missile;
    public LevelController levelController;

    public Util util;
    public int usedGun = 1;
    int randomNumber;
    Random random = new Random();
    private long lastEnemyMoveTime = 0;

    public int stageNumber = 0;

    public void initialize() {
        levelController = new LevelController();
        util = new Util();
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip(levelController.getPlayer_spaceShip(),
                30, 30,
                levelController.getHealth_player(),
                "player",
                400, 600,
                0, 0);
        animationPanel.setPrefSize(1000, 800);
        animationPanel.getChildren().addAll(spaceShip);

    }

    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
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
            KeyCode code = e.getCode();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        sceneActual.setOnKeyReleased((KeyEvent e) -> {
            KeyCode code = e.getCode();
            input.remove(code);
            update();
        });

    }

    // todo how to limit the movement of invaders within the animationPanel
    // todo need to implement collision between spacehip and invader -1 health to both
    // todo implement a gif for explosion
    private void update() {
        elapsedTime += 0.016;
        getSprites().forEach(this::processSprite);
        removeDeadSprites();
        moveInvaders();

        if (!areEnemiesRemaining()) {
            generateInvaders();
            stageNumber++;
            levelController.setNumberEnemies(1);


            if (stageNumber % 3 == 0) {
                levelController.setSpeedInvader(1);
                levelController.setSpeedSpaceShip(1);
            }

            stageLabel.setText("Stage: " + stageNumber);
            levelController.setInvaderShootingFrequency();
        }
        util.settingBackground(stageNumber, animationPanel);


        if (input.contains(KeyCode.F)) {
            Stage stage = (Stage) sceneActual.getWindow();
            stage.setFullScreen(!stage.isFullScreen());
            input.remove(KeyCode.F);
        }
        // todo enemy does not get out of the screen no matter the speed, but some inconsistencies
        if (input.contains(KeyCode.A) || input.contains(KeyCode.LEFT)) {
            if (spaceShip.getTranslateX() - levelController.getSpeedSpaceShip() >= -2) {
                spaceShip.moveLeft(levelController.getSpeedSpaceShip());
            }
        }
        if (input.contains(KeyCode.D) || input.contains(KeyCode.RIGHT)) {
            if (spaceShip.getTranslateX() + spaceShip.getFitWidth() + levelController.getSpeedSpaceShip() <= sceneActual.getWidth()) {
                spaceShip.moveRight(levelController.getSpeedSpaceShip());
            }
        }
        if (input.contains(KeyCode.W) || input.contains(KeyCode.UP)) {
            if (spaceShip.getTranslateY() - levelController.getSpeedSpaceShip() >= -2) {
                spaceShip.moveUp(levelController.getSpeedSpaceShip());
            }
        }
        if (input.contains(KeyCode.S) || input.contains(KeyCode.DOWN)) {
            if (spaceShip.getTranslateY() + spaceShip.getFitHeight() + levelController.getSpeedSpaceShip() <= sceneActual.getHeight()) {
                spaceShip.moveDown(levelController.getSpeedSpaceShip());
            }
        }
        // todo the collision is not really working
        if (Sprite.isCollision(spaceShip, invader)) {
            spaceShip.lose_health();
            invader.lose_health();
        }


        if (input.contains(KeyCode.C)) {
            // todo there is an odd delay after the swithchin from the last weapon
            System.out.println("Before: " + usedGun);
            if (usedGun <= levelController.numberOfGuns) {
                usedGun += 1;
                System.out.println("Gun number: " + usedGun);
            } else {
                usedGun = 1;
                System.out.println("Cleaned");
            }
            input.remove(KeyCode.C);
        }
        input.remove(KeyCode.C);

        if(input.contains(KeyCode.SPACE)){
            shooting(spaceShip, usedGun);
        }
        if (spaceShip.getBoundsInParent().intersects(invader.getBoundsInParent())) {
            System.out.println("collision");
            invader.setDead(true);
            spaceShip.lose_health();
        }

        if (elapsedTime > 2) {
            elapsedTime = 0;
        }

    }

    // todo fix the enemy generation problem
    private void generateInvaders() {
        if (stageNumber >= 0) {
            for (int i = 0; i < levelController.getNumberEnemies(); i++) {
                invader = new Small_Invader(levelController.getSmall_Enemy(),
                        35, 35,
                        levelController.getHealth_small_Invader(),
                        "enemy",
                        90 + i * 100, 500,
                        0, 0);
                animationPanel.getChildren().addAll(invader);
            }
        }
        if (stageNumber >= 3) {
            // implement the logic of adding medium size enemies
        }
        if (stageNumber >= 5) {
            // implement logic of adding big enemies
        }
    }

    // todo implement custom movement types, not just shifting around
    private void moveInvaders() {
        long now = System.currentTimeMillis();
        if (now - lastEnemyMoveTime > 200) {
            for (Node n : animationPanel.getChildren()) {
                if (n instanceof Small_Invader smallInvader) {
                    randomNumber = random.nextInt(2);
                    smallInvader.movementThree(levelController.getSpeedInvader());

//                    switch (randomNumber) {
//                        case 0 -> smallInvader.setVelocity(levelController.getSpeedInvader(), 0); // move right
//                        case 1 -> smallInvader.setVelocity(-levelController.getSpeedInvader(), 0); // move left
//                        case 2 -> smallInvader.setVelocity(0, levelController.getSpeedInvader()); // move down
//                        case 3 -> smallInvader.setVelocity(0, -levelController.getSpeedInvader()); // move up
//                        case 4 -> smallInvader.setVelocity(levelController.getSpeedInvader(), levelController.getSpeedInvader()); // right down
//                        case 5 -> smallInvader.setVelocity(-levelController.getSpeedInvader(), levelController.getSpeedInvader()); // left down
//                        case 6 -> smallInvader.setVelocity(levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // right up
//                        case 7 -> smallInvader.setVelocity(-levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // left up
//                    }
                }
            }
            lastEnemyMoveTime = now;
        }

        // Now actually move all invaders based on their velocities
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Small_Invader smallInvader) {
                smallInvader.move();
            }
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


// todo make this so that it seperates the firing type cause after setting them to go diagonally, they come back to moveUp
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



    // todo enemy is shooting from the back fix it
    private void handleEnemyBullet(Sprite missile) {
        missile.move();
        if (missile.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            System.out.println("Collision detected!");
            spaceShip.lose_health();
            System.out.println("SpaceShip health: " + spaceShip.getHealth());
            if (spaceShip.checkHealth()) {
                spaceShip.setDead(true);
                System.out.println("Sprite marked as dead.");
            }
            missile.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.move();
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.lose_health();
                    if (enemy.checkHealth()) {
                        enemy.setDead(true);
                        if (enemy instanceof Small_Invader) {
                            levelController.score += 1;
                        } else if (enemy instanceof Medium_Invader) {
                            levelController.score += 3;
                        } else if (enemy instanceof Big_Invader) {
                            levelController.score += 5;
                        }
                        scoreLabel.setText("Score: " + levelController.getScore());
                    }
                }
            }
        }
    }


    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            if (Math.random() < levelController.getInvaderShootingFrequency()) {
                singleShot(sprite);
            }
        }
    }

    // todo update this so that any sprite (espacially missiles) will stop rendering if they go out of bounds
    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            if (n instanceof Sprite sprite) {
                // Check if the sprite is dead or out of bounds
                boolean isOutOfBounds = sprite.getTranslateX() < -2 ||
                        sprite.getTranslateX() > animationPanel.getWidth() ||
                        sprite.getTranslateY() < -2 ||
                        sprite.getTranslateY() > animationPanel.getHeight();
                return sprite.isDead() || isOutOfBounds;
            }
            return false;
        });
    }



    // todo fix the shooting. after pressing on the last one, user has to press one more time to allow to come back to 0
    private void shooting(Sprite firingEntity, int weapon) {
        switch (weapon) {
            case 1:
                laser(firingEntity);
                System.out.println("weapon 1");
                break;
            case 2:
                doubleShot(firingEntity);
                System.out.println("weapon 2");
                break;
            case 3:
                circleShot(firingEntity);
                System.out.println("weapon 3");
                break;
            default:
                break;
        }
    }

    public void singleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            double dx = 0; // Straight up
            double dy = -levelController.getSpeedSpaceShip();

            Missile missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dx, dy);

            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }
    public void laser(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 500) {
            double dx = 0;
            double dy = -25;

            Missile missile = new Missile(levelController.blueMissile_1, 5, 65, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2 - 2.5,
                    (int) firingEntity.getTranslateY() - firingEntity.getFitHeight() * 2,
                    dx, dy);

            animationPanel.getChildren().add(missile);
            levelController.setLastShot(now);
        }
    }

    public void doubleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            double dxLeft = 0;
            double dyLeft = -levelController.getSpeedSpaceShip();

            double dxRight = 0;
            double dyRight = -levelController.getSpeedSpaceShip();

            Missile leftMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() -10 + firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxLeft, dyLeft);

            Missile rightMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxRight, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);

            levelController.setLastShot(now);
        }
    }

    public void doubleShotAngle(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            double dxLeft = -0.5; // Slightly left
            double dyLeft = -levelController.getSpeedSpaceShip(); // Upward

            double dxRight = 0.5; // Slightly right
            double dyRight = -levelController.getSpeedSpaceShip(); // Upward

            Missile leftMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxLeft, dyLeft);

            Missile rightMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxRight, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);

            levelController.setLastShot(now);
        }
    }

    public void customTripleShoot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            double dxLeft = -0.5; // Slightly left
            double dyLeft = -levelController.getSpeedSpaceShip(); // Upward

            double dxRight = 0.5; // Slightly right
            double dyRight = -levelController.getSpeedSpaceShip(); // Upward
            singleShot(firingEntity);

            Missile leftMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxLeft, dyLeft);

            Missile rightMissile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxRight, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);
            levelController.setLastShot(now);
        }
    }

    public void circleShot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 5000) {
            int numberOfBullets = 12; // Total bullets in the circle
            double centerX = firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2;
            double centerY = firingEntity.getTranslateY() + firingEntity.getFitHeight() / 2;

            for (int i = 0; i < numberOfBullets; i++) {
                double angle = 2 * Math.PI * i / numberOfBullets;

                double dx = levelController.getSpeedSpaceShip() * Math.cos(angle);
                double dy = levelController.getSpeedSpaceShip() * Math.sin(angle);

                Missile missile = new Missile(levelController.blueMissile_1, 10, 10, levelController.getHealth_missile(),
                        firingEntity.getType() + "bullet",
                        (int) centerX,
                        (int) centerY,
                        dx, dy);
                animationPanel.getChildren().add(missile);
            }

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

    private boolean ifCharacterWithinScene() {
        // todo method to check if the sprites are within the scene
        return false;
    }
}
