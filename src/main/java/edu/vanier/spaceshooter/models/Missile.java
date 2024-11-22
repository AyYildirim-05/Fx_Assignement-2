package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Missile extends Sprite{

    public Missile(String imagePath, double height, double width, String type, double x, double y) {
        super(imagePath, height, width, type, x, y);
    }

    @Override
    public void move() {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void makeShootingNoise() {

    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
