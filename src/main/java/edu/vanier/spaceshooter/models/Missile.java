package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Missile extends Sprite{


    public Missile(String imagePath, double height, double width, int health, String type, double x, double y) {
        super(imagePath, height, width, health, type, x, y);
    }

    //todo create at lest 3 different missile types
    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveUp() {

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
