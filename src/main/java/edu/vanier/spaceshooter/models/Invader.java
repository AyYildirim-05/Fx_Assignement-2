package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Invader extends Sprite {

    public Invader(String imagePath, double height, double width, int health, String type, double x, double y) {
        super(imagePath, height, width, health, type, x, y);
    }


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
}
