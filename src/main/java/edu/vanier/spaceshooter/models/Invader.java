package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Invader extends FiringSprites {
    public final String small_Enemy = "invaders/enemyBlue2.png";
    public final String medium_Enemy = "invaders/enemyBlue3.png";
    public final String big_Enemy = "invaders/enemyBlue4.png";
    public final String boss_Enemy = "invaders/enemyBlue5.png";

    public Invader(String imagePath, double size, int health, String type, double x, double y) {
        super(imagePath, size, health, type, x, y);
    }
}
