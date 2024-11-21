package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Invader extends Sprite {
    public int small_invader = 1;
    public int medium_invader = 2;
    public int big_invader = 3;

    public Invader(String imagePath, String type, int health, double x, double y, double height, double width) {
        super(imagePath, type, health, x, y, height, width);
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
