package edu.vanier.spaceshooter.models;

import java.util.Random;

public class Small_Invader extends Invader {
    Random someNumber;
    int directionX; // left or right
    int directionY; // left or right
    int distance; // how much left or right

    public Small_Invader(String imagePath, double width, double height, int health, String type, double x, double y) {
        super(imagePath, width, height, health, type, x, y);
        someNumber = new Random();
    }

    // only horizontal movement
    public void movement_1() {
        if (someNumber.nextBoolean()) {
            directionX = 1;
        } else {
            directionX = -1;
        }
        distance = someNumber.nextInt(100) + 1;
        setTranslateX(getTranslateX() + (directionX * distance));
    }

    // small changes in 4 directions
    public void movement_2() {
        if (someNumber.nextBoolean()) {
            directionX = 1;
        } else {
            directionX = -1;
        }
        distance = someNumber.nextInt(25) + 1;
        setTranslateX(getTranslateX() + (directionX * distance));


        if (someNumber.nextBoolean()) {
            directionY = 1;
        } else {
            directionY = -1;
        }
        distance = someNumber.nextInt(25) + 1;
        setTranslateY(getTranslateY() + (directionY * distance));
    }
}
