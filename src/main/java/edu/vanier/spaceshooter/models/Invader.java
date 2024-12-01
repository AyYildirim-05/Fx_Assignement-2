package edu.vanier.spaceshooter.models;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Invader extends FiringSprites {
    public Invader(String imagePath, double width, double height, int health, String type, double x, double y) {
        super(imagePath, width, height, health, type, x, y);
    }

    // straight patterns
    public void moveInvader(double xAxis, double yAxis) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), this);
        transition.setByX(xAxis);
        transition.setByY(yAxis);
        transition.setCycleCount(1);
        transition.play();
    }
}
