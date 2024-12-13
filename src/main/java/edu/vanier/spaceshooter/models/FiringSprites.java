package edu.vanier.spaceshooter.models;


/**
 * Sprite instance of all firing classes.
 */

public abstract class FiringSprites extends Sprite {

    public FiringSprites(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }
}
