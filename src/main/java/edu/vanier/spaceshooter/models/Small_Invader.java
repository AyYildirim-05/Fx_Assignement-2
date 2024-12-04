package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.LevelController;

import java.util.Random;

public class Small_Invader extends Invader {
    Random random = new Random();
    int randomNumber;

    public Small_Invader(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }

    public void movementOne(int speed) {
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
    public void movementThree(int speed) {
        randomNumber = random.nextInt(4);
        switch (randomNumber) {
            case 0:
                this.setVelocity(0, speed);
                this.setVelocity(speed, 0);
                break;
            case 1:
                this.setVelocity(0, speed);
                this.setVelocity(-speed, 0);
                break;
            case 2:
                this.setVelocity(0, -speed);
                this.setVelocity(speed, 0);
                break;
            case 3:
                this.setVelocity(0, -speed);
                this.setVelocity(-speed, 0);
                break;
        }
    }

    public void movementFour(int speed) {
        randomNumber = random.nextInt(4);
        switch (randomNumber) {
            case 0:
                this.setVelocity(speed, speed);
                this.setVelocity(-speed, speed);
                break;
            case 1:
                this.setVelocity(-speed, speed);
                this.setVelocity(speed, speed);
                break;
            case 2:
                this.setVelocity(speed, -speed);
                this.setVelocity(-speed, -speed);
                break;
            case 3:
                this.setVelocity(-speed, -speed);
                this.setVelocity(speed, -speed);
                break;
        }
    }

    public void movementFive(int speed) {
        randomNumber = random.nextInt(1001);
    }
}

