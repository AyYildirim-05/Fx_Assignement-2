package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.LevelController;
import edu.vanier.spaceshooter.support.PlayingSound;
import edu.vanier.spaceshooter.support.Util;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that implements the game logic.
 */

public class SpaceShooterAppController {
    private final static String endApp = "/fxml/endGame_layout.fxml";
    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterAppController.class);
    @FXML
    Pane animationPanel;
    @FXML
    StackPane HUD;
    @FXML
    Label scoreLabel;
    @FXML
    Label stageLabel;
    @FXML
    HBox playerHealthRepresentation;
    private Scene sceneActual;
    private Stage stageActual;
    private double elapsedTime = 0;
    private AnimationTimer gameLoop;
    private List<KeyCode> input = new ArrayList<>();
    public SpaceShip spaceShip;
    public PlayingSound soundClass;
    public Missile missile;
    public LevelController levelController;

    public Util util;
    public int usedGun = 1;
    int randomNumber;
    Random random = new Random();
    private long lastEnemyMoveTime = 0;
    public int stageNumber = -1;
    private long lastCollisionTime = 0;
    int x = 0;
    int y = 0;
    int imageNum = -1;

    /**
     * Method that initializes the game screen.
     */
    public void initialize() {
        levelController = new LevelController();
        soundClass = new PlayingSound();

        util = new Util(playerHealthRepresentation);
        logger.info("Initializing MainAppController...");
        spaceShip = new SpaceShip(levelController.getPlayer_spaceShip1(),
                30, 30,
                levelController.getHealth_player1(),
                "player",
                500,  700,
                0, 0);

        animationPanel.setPrefSize(1000, 800);
        animationPanel.getChildren().addAll(spaceShip);
    }

    /**
     * Method that sets up the game world.
     */
    public void setupGameWorld() {
        animationPanel.prefWidthProperty().bind(stageActual.widthProperty());
        animationPanel.prefHeightProperty().bind(stageActual.heightProperty());
        initGameLoop();
        setupKeyPressHandlers();
    }

    /**
     * Method that starts the game loop.
     */
    private void initGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    /**
     * Method that gets user inputs and puts them all in a list.
     */
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

    /**
     * Method that creates the end game scene.
     */
    private void endGameScene() {
        endGameController endGameController = new endGameController();
        endGameController.getScore(levelController.getScore());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(endApp));

        fxmlLoader.setController(endGameController);
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stageActual.setScene(scene);
        stageActual.show();
    }

    /**
     * Method that generates invaders per level.
     */
    private void generateInvaders() {
        switch (stageNumber) {
            case 1 -> generateEnemy(15, 0, 0, 0);
            case 2 -> generateEnemy(12, 2, 1, 0);
            case 3 -> generateEnemy(10, 4, 3, 1);
            default -> {
                if (stageNumber >= 3) {
                    generateEnemy(
                            random.nextInt(1, levelController.getNumberEnemies()),
                            random.nextInt(1, levelController.getNumberEnemies()),
                            random.nextInt(1, levelController.getNumberEnemies()),
                            random.nextInt(1, levelController.getNumberEnemies())
                    );
                }
            }
        }
    }

    /**
     * Method that moves invaders based on their instance.
     */
    private void moveInvaders() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyMoveTime > 500) {
            for (Node n : animationPanel.getChildren()) {
                randomNumber = random.nextInt(2);
                if (n instanceof Small_Invader smallInvader) {
                    smallInvader.movementThree(levelController.speedInvader);
                }
                else if (n instanceof Medium_Invader mediumInvader) {
                    mediumInvader.movementFive(levelController.speedInvader);
                }  else if (n instanceof Big_Invader bigInvader) {
                    bigInvader.movementSix(levelController.speedInvader);
                } else if (n instanceof Boss_Invader bossInvader) {
                    bossInvader.movementFour(levelController.speedInvader);
                }

            }
            lastEnemyMoveTime = currentTime;
        }

        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Invader invaderInstances) {
                invaderInstances.moveInvaders();
            }
        }
    }

    /**
     * Method that allows an enemy to shoot based on their instance.
     * @param sprite the entered instance of the invader.
     */
    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            switch (sprite.getClass().getSimpleName()) {
                case "Small_Invader" -> {
                    if (Math.random() < 0.99) {
                        singleShot(sprite, levelController.getBlueMissile_1());
                    }
                }
                case "Medium_Invader" -> {
                    if (Math.random() < 0.7) {
                        randomNumber = random.nextInt(2);
                        switch (randomNumber) {
                            case 0 ->  doubleShot(sprite);
                            case 1 ->  doubleShotAngle(sprite);
                        }
                    }
                }
                case "Big_Invader" -> {
                    if (Math.random() < 0.5) {
                        randomNumber = random.nextInt(2);
                        switch (randomNumber) {
                            case 0 ->  tripleShoot(sprite);
                            case 1 ->  laser(sprite, levelController.getRedMissile_2());
                        }
                    }
                }
                case "Boss_Invader" -> {
                    circleShot(sprite, 12);
                }
            }
        }
    }


    /**
     * Method that gets all the sprites int the scene.
     * @return the list of all the sprites in the scene.
     */
    private List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Sprite sprite) {
                spriteList.add(sprite);
            }
        }
        return spriteList;
    }

    /**
     * Method that gets all the invaders int the scene.
     * @return the list of all the invaders in the scene.
     */
    private List<Invader> getInvaders() {
        List<Invader> spriteInvader = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Invader inv) {
                spriteInvader.add(inv);
            }
        }
        return spriteInvader;
    }

    /**
     * Method that processes sprites based on their types.
     * @param sprite the given sprite.
     */
    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" -> {
                handleEnemyBullet(sprite);
            }
            case "playerbullet" -> handlePlayerBullet(sprite);
            case "enemy" -> handleEnemyFiring(sprite);
        }
    }

    /**
     * Method that handles enemy missile behavior.
     * @param missile the given invader missile.
     */
    private void handleEnemyBullet(Sprite missile) {
        missile.move();
        if (missile.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            soundClass.explosionGif(spaceShip, animationPanel);
            playerLoseHealth();
            missile.lose_health();
            missile.setDead(true);
        }
    }

    /**
     * Method that takes care of player health points.
     * It connects player health points and their representation on screen.
     */
    private void playerLoseHealth() {
        spaceShip.lose_health();
        util.removeLastHealth(playerHealthRepresentation);
    }

    /**
     * Method that updates the score based on the invader killed.
     * @param enemy the killed invader.
     */
    private void updateScore(Sprite enemy) {
        int scoreToAdd = switch (enemy.getClass().getSimpleName()) {
            case "Small_Invader" -> 1;
            case "Medium_Invader" -> 2;
            case "Big_Invader" -> 3;
            case "Boss_Invader" -> 5;
            default -> 0;
        };
        levelController.score += scoreToAdd;
        scoreLabel.setText("Score: " + levelController.getScore());
    }

    /**
     * Method that contains the game loop.
     */
    private void update() {
        elapsedTime += 0.016;
        stageLabel.setText("Stage: " + stageNumber);
        getSprites().forEach(this::processSprite);
        removeDeadSprites();
        moveInvaders();
        gameProgress();
        playerInputs();
        collisionDetectorEnemy();
        gameEnd();
        if (elapsedTime > 2) {
            elapsedTime = 0;
        }
    }

    /**
     * Method that contains the logic of ending the game.
     */
    private void gameEnd() {
        if (spaceShip.getHealth() == 0) {
            resetScene();
            stopAnimation();
            endGameScene();
        }
    }

    /**
     * Method that contains game progress.
     */
    private void gameProgress() {
        if (!areEnemiesRemaining()) {
            if (stageNumber > 0) {
                levelController.nextLevel(spaceShip);
            }

            resetScene();
            stageNumber++;
            generateInvaders();
            levelController.setCurrentGun(1);
            levelController.increaseShooting();
            spaceShip.setTranslateX(sceneActual.getWidth() / 2);
            spaceShip.setTranslateY(sceneActual.getHeight() - 100);
            animationPanel.getChildren().remove(spaceShip);
            spaceShip = new SpaceShip(levelController.setPlayerSprite(stageNumber), 30, 30, levelController.getHealth_player1(), "player", 500, 700, 0, 0);
            animationPanel.getChildren().add(spaceShip);

            if (stageNumber != 5) {
                imageNum++;
                util.settingBackground(imageNum, animationPanel);
            }

            if (stageNumber % 2 == 0 && levelController.getNumberEnemies() <= 25) {
                levelController.setNumberEnemies(1);
            }


            levelController.setSpeedInvader(0.5);
            levelController.setSpeedSpaceShip(0.5);
        }
    }

    /**
     * Method that takes care of player inputs.
     */
    private void playerInputs() {
        if (input.contains(KeyCode.F)) {
            Stage stage = (Stage) sceneActual.getWindow();
            stage.setFullScreen(!stage.isFullScreen());
            input.remove(KeyCode.F);
        }
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

        if (input.contains(KeyCode.C)) {
            if (usedGun < levelController.getCurrentGun() && usedGun <= levelController.getNumberOfGuns()) {
                usedGun += 1;
            } else {
                usedGun = 1;
            }
            input.remove(KeyCode.C);
        }
        input.remove(KeyCode.C);

        if (input.contains(KeyCode.SPACE)) {
            shooting(spaceShip, usedGun);
        }
    }

    /**
     * Method that takes care of the collision between the invader and the player.
     */
    private void collisionDetectorEnemy() {
        for (Invader invader : getInvaders()) {
            if (Sprite.isCollision(spaceShip, invader)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCollisionTime > 5000) {
                    playerLoseHealth();
                    invader.lose_health();
                    invader.setDead(true);
                    updateScore(invader);
                    soundClass.explosionGif(invader, animationPanel);
                    lastCollisionTime = currentTime;
                }
            }
        }
    }

    /**
     * Method that deals with the logic of player missiles.
     * @param sprite for a given missile.
     */
    private void handlePlayerBullet(Sprite sprite) {
        sprite.move();
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.lose_health();
                    soundClass.explosionGif(enemy, animationPanel);
                    if (enemy.checkHealth()) {
                        updateScore(enemy);
                        enemy.setDead(true);
                    }
                    sprite.setDead(true);
                }
            }
        }
    }

    /**
     * Method that checks if a sprite should be removed from the scene.
     */
    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            if (n instanceof Sprite sprite && !(n instanceof SpaceShip) ) {
                boolean isOutOfBounds = sprite.getTranslateX() < -2 ||
                        sprite.getTranslateX() > animationPanel.getWidth() ||
                        sprite.getTranslateY() < -2 ||
                        sprite.getTranslateY() > animationPanel.getHeight();
                return isOutOfBounds || sprite.isDead();
            }
            return false;
        });
    }

    /**
     * Method that deals with player switching missile types.
     * @param firingEntity the spaceship
     * @param weapon the entered missile type that the player wishes to use.
     */
    private void shooting(Sprite firingEntity, int weapon) {
        System.out.println("used gun" + weapon);
        switch (weapon) {
            case 1:
                singleShot(firingEntity, levelController.getBlueMissile_1());
                break;
            case 2:
                doubleShot(firingEntity);
                break;
            case 3:
                doubleShotAngle(firingEntity);
                break;
            case 4:
                tripleShoot(firingEntity);
                break;
            case 5:
                laser(firingEntity, levelController.getRedMissile_1());
                break;
            case 6:
                circleShot(firingEntity, 8);
                break;
            default:
                break;
        }
    }

    /**
     * Method that creates a single missile.
     * @param firingEntity the sprite which creates the missile.
     * @param color the color of the missile.
     */
    public void singleShot(Sprite firingEntity, String color) {
        double dy = levelController.getSpeedMissiles();
        long now = System.currentTimeMillis();
        if (now - firingEntity.getLastFiredTime() > 600) {
            if (firingEntity instanceof SpaceShip) {
                dy = -1 * dy;
                x = (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2);
                y = (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2);
            } else if (firingEntity instanceof Invader) {
                dy = 1 * dy;
                x = (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2 - 5);
                y = (int) (firingEntity.getTranslateY() + firingEntity.getFitHeight() / 2 + 5);
            }
            Missile missile = new Missile(color, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    x, y,
                    0, dy);
            animationPanel.getChildren().add(missile);
            firingEntity.setLastFiredTime(now);
        }
    }

    /**
     * Method that creates a laser from a sprite.
     * @param firingEntity the sprite that creates the missile.
     * @param color the color of the laser.
     */
    public void laser(Sprite firingEntity, String color) {
        double dy = 25;
        long now = System.currentTimeMillis();
        if (now - firingEntity.getLastFiredTime() > 1000) {
            if (firingEntity instanceof SpaceShip) {
                dy = -1 * dy;
                x = (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2 - 2.5);
                y = (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() * 2);
            } else if (firingEntity instanceof Invader) {
                x = (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2 - 5);
                y = (int) (firingEntity.getTranslateY() + firingEntity.getFitHeight() + 5);
            }

            Missile missile = new Missile(color, 5, 65, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    x, y,
                    0, dy);

            animationPanel.getChildren().add(missile);
            firingEntity.setLastFiredTime(now);
        }
    }

    /**
     * Method that allows to missiles to be shot next to each other.
     * @param firingEntity the sprite that creates the missile.
     */
    public void doubleShot(Sprite firingEntity) {
        double dyLeft = levelController.getSpeedMissiles();
        double dyRight = levelController.getSpeedMissiles();
        long now = System.currentTimeMillis();
        if (now - firingEntity.getLastFiredTime() > 1200) {
            if (firingEntity instanceof SpaceShip) {
                dyLeft = -1 * dyLeft;
                dyRight = -1 * dyRight;
                y = (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2);
            } else if (firingEntity instanceof Invader) {
                y = (int) (firingEntity.getTranslateY() + firingEntity.getFitHeight());
            }

            Missile leftMissile = new Missile(levelController.greenMissile_2, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() - 10 + firingEntity.getFitWidth() / 3),
                    y, 0, dyLeft);

            Missile rightMissile = new Missile(levelController.greenMissile_2, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                    y, 0, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);
            firingEntity.setLastFiredTime(now);
        }
    }

    /**
     * Method that allows to missiles to be created and shot at an angle.
     * @param firingEntity the sprite that creates the missile.
     */
    public void doubleShotAngle(Sprite firingEntity) {
        double dyLeft = levelController.getSpeedMissiles();
        double dyRight = levelController.getSpeedMissiles();
        double dxLeft = -0.5;
        double dxRight = 0.5;

        long now = System.currentTimeMillis();
        if (now - firingEntity.getLastFiredTime() > 900) {
            if (firingEntity instanceof SpaceShip) {
                dyLeft = -1 * dyLeft;
                dyRight = -1 * dyRight;
                y = (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2);
            } else if (firingEntity instanceof Invader) {
                y = (int) (firingEntity.getTranslateY() + firingEntity.getFitHeight());
            }

            Missile leftMissile = new Missile(levelController.greenMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 3),
                    y,
                    dxLeft, dyLeft);

            Missile rightMissile = new Missile(levelController.greenMissile_1, 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX()  + firingEntity.getFitWidth() / 3),
                    y,
                    dxRight, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);
            firingEntity.setLastFiredTime(now);
        }
    }

    /**
     * Method that creates three missiles with their own pathing (not all straight).
     * @param firingEntity the sprite that creates the missile.
     */
    public void tripleShoot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - firingEntity.getLastFiredTime() > 1200) {
            double dxLeft = -0.5;
            double dyLeft = -levelController.getSpeedMissiles();

            double dxRight = 0.5;
            double dyRight = -levelController.getSpeedMissiles();

            singleShot(firingEntity, levelController.getRedMissile_1());

            Missile leftMissile = new Missile(levelController.getRedMissile_1(), 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxLeft, dyLeft);

            Missile rightMissile = new Missile(levelController.getRedMissile_1(), 10, 10, levelController.getHealth_missile(),
                    firingEntity.getType() + "bullet",
                    (int) (firingEntity.getTranslateX() + 2 * firingEntity.getFitWidth() / 3),
                    (int) (firingEntity.getTranslateY() - firingEntity.getFitHeight() / 2),
                    dxRight, dyRight);

            animationPanel.getChildren().addAll(leftMissile, rightMissile);
            firingEntity.setLastFiredTime(now);
        }

    }

    /**
     * Method that allows a sprite to shoot all around them.
     * @param firingEntity the sprite that creates the missile.
     * @param numMissile the number of missiles the sprite is shooting.
     */
    public void circleShot(Sprite firingEntity, int numMissile) {
        long now = System.currentTimeMillis();
        long down = (firingEntity instanceof SpaceShip) ? 3000 : 1000;
        if (now - firingEntity.getLastFiredTime() > down) {
            double centerX = firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2;
            double centerY = firingEntity.getTranslateY() + firingEntity.getFitHeight() / 2;

            for (int i = 0; i < numMissile; i++) {
                double angle = 2 * Math.PI * i / numMissile;

                double dx = levelController.getSpeedMissiles()
                        * Math.cos(angle);
                double dy = levelController.getSpeedMissiles()
                        * Math.sin(angle);

                missile = new Missile(levelController.getRedMissile_2(), 10, 10, levelController.getHealth_missile(),
                        firingEntity.getType() + "bullet",
                        (int) centerX,
                        (int) centerY,
                        dx, dy);
                animationPanel.getChildren().add(missile);
            }
            firingEntity.setLastFiredTime(now);
        }
    }

    /**
     * Method that sets a scene to the current scene.
     * @param scene the entered scene.
     */
    public void setScene(Scene scene) {
        sceneActual = scene;
    }

    /**
     * Method that stops the game loop.
     */
    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Method that checks if there is any enemy remaining within the screen.
     * @return boolean value.
     */
    private boolean areEnemiesRemaining() {
        for (Sprite sprite : getSprites()) {
            if (sprite.getType().equals("enemy")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that sets a stage to the current stage.
     * @param stage the entered stage.
     */
    public void setStage(Stage stage) {
        stageActual = stage;
        stageActual.setOnCloseRequest(event -> {
            if (gameLoop != null) {
                gameLoop.stop();
            }
        });
    }

    /**
     * Method that binds the scene width to that of the stage.
     * @param stage the entered stage.
     */
    public void bindSceneWidth(Stage stage) {
        stage.minWidthProperty().bind(stageActual.widthProperty());

    }

    /**
     * Method that binds the scene height to that of the stage.
     * @param stage the entered stage.
     */
    public void bindSceneHeight(Stage stage) {
        stage.minHeightProperty().bind(stageActual.heightProperty());
    }

    /**
     * Method that generates enemies.
     * @param small the number of small enemies.
     * @param medium the number of medium enemies.
     * @param big the number of big enemies.
     * @param boss the number of boss enemies.
     */
    public void generateEnemy(int small, int medium, int big, int boss) {
        double animationPanelWidth = animationPanel.getPrefWidth();

        double bossRowY = 50;
        double bigRow = bossRowY + 100;
        double mediumRow = bigRow + 100;
        double smallRow = mediumRow + 100;

        double bossSpacing = animationPanelWidth / (boss + 1);
        double bigSpacing = animationPanelWidth / (big + 1);
        double mediumSpacing = animationPanelWidth / (medium + 1);
        double smallSpacing = animationPanelWidth / (small + 1);

        for (int i = 0; i < boss; i++) {
            double x = bossSpacing * (i + 1);
            Boss_Invader bossInvader = new Boss_Invader(levelController.getBoss_Enemy(), 50, 50, levelController.getHealth_boss_Invader(), "enemy", x, bossRowY, 0, 0);
            animationPanel.getChildren().add(bossInvader);
        }

        for (int i = 0; i < big; i++) {
            double x = bigSpacing * (i + 1);
            Big_Invader bigInvader = new Big_Invader(levelController.getBig_Enemy(), 45, 45, levelController.getHealth_big_Invader(), "enemy", x, bigRow, 0, 0);
            animationPanel.getChildren().add(bigInvader);
        }

        for (int i = 0; i < medium; i++) {
            double x = mediumSpacing * (i + 1);
            Medium_Invader mediumInvader = new Medium_Invader(levelController.getMedium_Enemy(), 40, 40, levelController.getHealth_medium_Invader(), "enemy", x, mediumRow, 0, 0);
            animationPanel.getChildren().add(mediumInvader);
        }

        for (int i = 0; i < small; i++) {
            double x = smallSpacing * (i + 1);
            Small_Invader smallInvader = new Small_Invader(levelController.getSmall_Enemy(), 35, 35,
                    levelController.getHealth_small_Invader(), "enemy", x, smallRow, 0, 0);
            animationPanel.getChildren().add(smallInvader);
        }
    }

    /**
     * Method that resets the scene to prepare it for the next stage.
     * Removes all sprite instances.
     */
    public void resetScene() {
        animationPanel.getChildren().removeIf(n -> n instanceof Sprite);
    }
}