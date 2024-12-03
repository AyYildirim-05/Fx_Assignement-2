package edu.vanier.spaceshooter.models;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Invader extends FiringSprites {


    public Invader(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }

    public void shiftingAround() {

    }
}
