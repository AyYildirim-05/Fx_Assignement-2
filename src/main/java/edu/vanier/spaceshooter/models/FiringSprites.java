package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.LevelController;
import javafx.scene.layout.Pane;

public abstract class FiringSprites extends Sprite {

    public Missile missile;
    public LevelController levelController;

    public FiringSprites(String imagePath, double width, double height, int health, String type, double x, double y) {
        super(imagePath, width, height, health, type, x, y);
    }

    public void tripleShot() {

    }
    public  void circleShot() {

    }
    public void machineGun() {

    }

    public void homing() {

    }
}
