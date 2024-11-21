package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class SpaceShip extends Sprite {
    protected int health_player = 3;


    /**
     * Constructor of all the characters in the application
     *
     * @param imagePath Gets the object sprite location
     * @param type      Defines the type of the object
     *                  // todo will be useful to avoid collisions between enemy objects
     * @param x         Left-to-right coordinate of the top-left corner of the ImageView
     * @param y         Top-to-bottom coordinate of the top-left corner of the ImageView
     */
    public SpaceShip(String imagePath, String type, int health_player, double x, double y, double height, double width) {
        super(imagePath, type, health_player, x, y, height, width);
    }

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
