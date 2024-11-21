package edu.vanier.spaceshooter.models;

public class Obstacles extends Sprite {
    public int small_obstacle = 1;
    public int medium_obstacle = 2;
    public int big_obstacle = 3;

    public Obstacles(String imagePath, String type, int health, double x, double y, double height, double width) {
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
}
