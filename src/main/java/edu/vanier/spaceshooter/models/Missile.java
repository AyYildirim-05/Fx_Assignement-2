package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Missile extends Sprite {

    public Missile(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }
}

