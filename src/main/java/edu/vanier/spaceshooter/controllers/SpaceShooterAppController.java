package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;
import edu.vanier.spaceshooter.support.LevelController;
import edu.vanier.spaceshooter.support.Util;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private static MediaView mediaView;

    private double elapsedTime = 0;
    private AnimationTimer gameLoop;
    private List<KeyCode> input = new ArrayList<>();
    public List<Invader> invaders = new ArrayList<>();
    public List<Missile> missiles = new ArrayList<>();
    public SpaceShip spaceShip;
    public Invader invader;

    public Sprite sprite;
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

    public void initialize() {
        levelController = new LevelController();

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

    public void setupGameWorld() {
        animationPanel.prefWidthProperty().bind(stageActual.widthProperty());
        animationPanel.prefHeightProperty().bind(stageActual.heightProperty());
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

    // todo implement a gif for explosion
    // todo transition mechanism
    // todo explosion effect when collision
    // todo player get damage sound effect
    // todo css in the game scene not effective


    // todo not all enemies are shooting & sound is causing problems
    private static Image explosionGif;

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

    private void generateInvaders() {
        switch (stageNumber) {
            case 1 -> generateEnemy(1, 1, 0, 0);
            case 2 -> generateEnemy(0, 1, 1, 0);
            case 3 -> generateEnemy(0, 0, 1, 1);
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

    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            switch (sprite.getClass().getSimpleName()) {
                case "Small_Invader" -> {
                    System.out.println("asura");
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
                    circleShot(sprite, levelController.getNumOfMissile());
                }
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


    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" -> {
                handleEnemyBullet(sprite);
            }
            case "playerbullet" -> handlePlayerBullet(sprite);
            case "enemy" -> handleEnemyFiring(sprite);
        }
    }


    private void handleEnemyBullet(Sprite missile) {
        missile.move();
        if (missile.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            playerLoseHealth();
            missile.lose_health();
            missile.setDead(true);
        }
    }

    private void playerLoseHealth() {
        spaceShip.lose_health();
        util.removeLastHealth(playerHealthRepresentation);
    }

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

    private ImageView explosion;
    private ImageView imageView;

    private void update() {
        elapsedTime += 0.016;

        if (spaceShip.getHealth() == 0) {
            resetScene();
            stopAnimation();
            endGameScene();
            return;
        }



        getSprites().forEach(this::processSprite);

        removeDeadSprites();
        moveInvaders();
        stageLabel.setText("Stage: " + stageNumber);

        if (!areEnemiesRemaining() && spaceShip.checkHealth()) {
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



            levelController.setSpeedInvader(0.2);
            levelController.setSpeedSpaceShip(0.2);
            levelController.setNumOfMissile();
        }

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
        for (Invader invader : invaders) {
            if (Sprite.isCollision(spaceShip, invader)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCollisionTime > 5000) {
                    playerLoseHealth();
                    invader.lose_health();
                    invader.setDead(true);
                    levelController.score++;
                    scoreLabel.setText("Score: " + levelController.getScore());
                    lastCollisionTime = currentTime;
                }
            }
        }

        if (elapsedTime > 2) {
            elapsedTime = 0;
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.move();
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.lose_health();
                    explosionGif(enemy);
                    if (enemy.checkHealth()) {
                        updateScore(enemy);
                        enemy.setDead(true);
                    }
                    sprite.setDead(true);
                }
            }
        }
    }

    public ImageView explosionGif(Sprite sp) {
        try {
            if (explosionGif == null && imageView == null) {
                explosionGif = new Image(getClass().getResource("/visual_effects/explosion.mp4").toExternalForm());
                imageView = new ImageView(explosionGif);
            }
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            imageView.setTranslateX(sp.getTranslateX());
            imageView.setTranslateY(sp.getTranslateY());

            animationPanel.getChildren().add(imageView);

            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1));
            pause.setOnFinished(event -> animationPanel.getChildren().remove(imageView));
            pause.play();
        } catch (Exception e) {
            System.err.println("Failed to load explosion GIF: " + e.getMessage());
        }
        return imageView;
    }



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
        invaders.removeIf(Invader::isDead);
    }

    private void shooting(Sprite firingEntity, int weapon) {
        switch (weapon) {
            case 1:
                singleShot(firingEntity, levelController.getBlueMissile_1());
                break;
            case 2:
                doubleShotAngle(firingEntity);
                break;
            case 3:
                tripleShoot(firingEntity);
                break;
            case 4:
                laser(firingEntity, levelController.getRedMissile_1());
                break;
            case 5:
                circleShot(firingEntity, 8);
                break;
            default:
                break;
        }
    }

    public void singleShot(Sprite firingEntity, String color) {
        double dy = levelController.getSpeedSpaceShip();
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
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
            levelController.setLastShot(now);
        }
    }

    public void laser(Sprite firingEntity, String color) {
        double dy = 25;
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 500) {
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
            levelController.setLastShot(now);
        }
    }

    public void doubleShot(Sprite firingEntity) {
        double dyLeft = levelController.getSpeedSpaceShip();
        double dyRight = levelController.getSpeedSpaceShip();
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
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
            levelController.setLastShot(now);
        }
    }

    public void doubleShotAngle(Sprite firingEntity) {
        double dyLeft = levelController.getSpeedSpaceShip();
        double dyRight = levelController.getSpeedSpaceShip();
        double dxLeft = -0.5;
        double dxRight = 0.5;

        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
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
            levelController.setLastShot(now);
        }
    }

    public void tripleShoot(Sprite firingEntity) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > levelController.getAnimationDuration()) {
            double dxLeft = -0.5;
            double dyLeft = -levelController.getSpeedSpaceShip();

            double dxRight = 0.5;
            double dyRight = -levelController.getSpeedSpaceShip();

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
            levelController.setLastShot(now);
        }

    }

    public void circleShot(Sprite firingEntity, int numMissile) {
        long now = System.currentTimeMillis();
        if (now - levelController.lastShot > 5000) {
            double centerX = firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2;
            double centerY = firingEntity.getTranslateY() + firingEntity.getFitHeight() / 2;

            for (int i = 0; i < numMissile; i++) {
                double angle = 2 * Math.PI * i / numMissile;

                double dx = levelController.getSpeedSpaceShip() * Math.cos(angle);
                double dy = levelController.getSpeedSpaceShip() * Math.sin(angle);

                missile = new Missile(levelController.getRedMissile_2(), 10, 10, levelController.getHealth_missile(),
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

    public void setStage(Stage stage) {
        stageActual = stage;
        stageActual.setOnCloseRequest(event -> {
            if (gameLoop != null) {
                gameLoop.stop();
            }
        });
    }

    public void bindSceneWidth(Stage stage) {
        stage.minWidthProperty().bind(stageActual.widthProperty());

    }

    public void bindSceneHeight(Stage stage) {
        stage.minHeightProperty().bind(stageActual.heightProperty());
    }

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
            invader = new Boss_Invader(levelController.getBoss_Enemy(), 50, 50, levelController.getHealth_boss_Invader(), "enemy", x, bossRowY, 0, 0);
            animationPanel.getChildren().add(invader);
            invaders.add(invader);
        }

        for (int i = 0; i < big; i++) {
            double x = bigSpacing * (i + 1);
            invader = new Big_Invader(levelController.getBig_Enemy(), 45, 45, levelController.getHealth_big_Invader(), "enemy", x, bigRow, 0, 0);
            animationPanel.getChildren().add(invader);
            invaders.add(invader);
        }

        for (int i = 0; i < medium; i++) {
            double x = mediumSpacing * (i + 1);
            invader = new Medium_Invader(levelController.getMedium_Enemy(), 40, 40, levelController.getHealth_medium_Invader(), "enemy", x, mediumRow, 0, 0);
            animationPanel.getChildren().add(invader);
            invaders.add(invader);
        }

        for (int i = 0; i < small; i++) {
            double x = smallSpacing * (i + 1);
            invader = new Small_Invader(levelController.getSmall_Enemy(), 35, 35,
                    levelController.getHealth_small_Invader(), "enemy", x, smallRow, 0, 0);
            animationPanel.getChildren().add(invader);
            invaders.add(invader);
        }
    }


    public void resetScene() {
        animationPanel.getChildren().removeIf(n -> n instanceof Sprite);
        invaders.clear();
    }
}