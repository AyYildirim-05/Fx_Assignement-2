package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class SpaceShip extends Sprite {

    /**
     * Constructor of all the characters in the application
     *
     * @param imagePath Gets the object sprite location
     * @param type      Defines the type of the object
     *                  // todo will be useful to avoid collisions between enemy objects
     * @param x         Left-to-right coordinate of the top-left corner of the ImageView
     * @param y         Top-to-bottom coordinate of the top-left corner of the ImageView
     */
    public SpaceShip(String imagePath, double height, double width, int health, String type, double x, double y) {
        super(imagePath, height, width, health, type, x, y);

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
}
