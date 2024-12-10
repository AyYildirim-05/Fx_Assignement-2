package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.LevelController;
import javafx.scene.layout.Pane;

public abstract class FiringSprites extends Sprite {
    public LevelController levelController;

    public FiringSprites(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }
}
