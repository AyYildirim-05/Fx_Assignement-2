package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.LevelController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Missile extends Sprite {

    public LevelController levelController = new LevelController();


    public Missile(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }

    public void singleShot(Sprite firingEntity, Pane animationPanel) {
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
}

