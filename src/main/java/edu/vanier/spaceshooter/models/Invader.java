package edu.vanier.spaceshooter.models;

import javafx.scene.layout.Pane;

import java.util.Random;

public class Invader extends FiringSprites {

    Random random = new Random();

    int randomNumber;

    public Invader(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }

    public void movementOne(int speed) {
        this.setVelocity(speed, 0);
        randomNumber = random.nextInt(2);
        switch (randomNumber) {
            case 0 -> this.setVelocity(speed, 0);
            case 1 -> this.setVelocity(-speed, 0);
        }
    }

    public void movementTwo(int speed) {
        randomNumber = random.nextInt(2);
        switch (randomNumber) {
            case 0 -> this.setVelocity(0, speed);
            case 1 -> this.setVelocity(0, -speed);
        }
    }
    public void movementThree(double speed) {
        randomNumber = random.nextInt(4);
        switch (randomNumber) {
            case 0:
                this.setVelocity(speed, speed);
                break;
            case 1:
                this.setVelocity(-speed, speed);
                break;
            case 2:
                this.setVelocity(speed, -speed);
                break;
            case 3:
                this.setVelocity(-speed, -speed);
                break;
        }
    }

    public void movementFour(double speed) {
        randomNumber = random.nextInt(8);
        switch (randomNumber) {
            case 0 -> this.setVelocity(speed, 0);
            case 1 -> this.setVelocity(speed, speed);
            case 2 -> this.setVelocity(0, speed);
            case 3 -> this.setVelocity(-speed, speed);
            case 4 -> this.setVelocity(-speed, 0);
            case 5 -> this.setVelocity(-speed, -speed);
            case 6 -> this.setVelocity(0, -speed);
            case 7 -> this.setVelocity(speed, -speed);
        }
    }

    public void movementFive(double speed) {
        double xRandom = (random.nextDouble() * 2 - 1) * speed;
        double yRandom = (random.nextDouble() * 2 - 1) * speed;
        this.setVelocity(xRandom, yRandom);
    }


    public void movementSix(double speed) {
        double angle = Math.toRadians(random.nextInt(360));
        double xVector = Math.cos(angle) * speed;
        double yVector = Math.sin(angle) * speed;
        this.setVelocity(xVector, yVector);
    }
}
