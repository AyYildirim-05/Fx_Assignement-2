package edu.vanier.spaceshooter.models;

import java.util.Random;

public class Invader extends FiringSprites {

    Random random = new Random();
    int randomNumber;

    public Invader(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }

    public void shiftingAround(int speed) {
        randomNumber = random.nextInt(8);
        switch (randomNumber) {
            case 0 -> this.setVelocity(levelController.getSpeedInvader(), 0); // move right
            case 1 -> this.setVelocity(-levelController.getSpeedInvader(), 0); // move left
            case 2 -> this.setVelocity(0, levelController.getSpeedInvader()); // move down
            case 3 -> this.setVelocity(0, -levelController.getSpeedInvader()); // move up
            case 4 ->
                    this.setVelocity(levelController.getSpeedInvader(), levelController.getSpeedInvader()); // right down
            case 5 ->
                    this.setVelocity(-levelController.getSpeedInvader(), levelController.getSpeedInvader()); // left down
            case 6 ->
                    this.setVelocity(levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // right up
            case 7 ->
                    this.setVelocity(-levelController.getSpeedInvader(), -levelController.getSpeedInvader()); // left up
        }
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

    public void movementSix(int speed) {
        // Random jump-like movement
        int xRandom = random.nextInt(speed * 2) - speed;
        int yRandom = random.nextInt(speed * 2) - speed;
        this.setVelocity(xRandom, yRandom);
    }

    public void movementSeven(int speed) {
        // Oscillating movement
        randomNumber = random.nextInt(2);
        if (randomNumber == 0) {
            this.setVelocity(speed, (int) (Math.sin(System.currentTimeMillis() % 360) * speed));
        } else {
            this.setVelocity((int) (Math.cos(System.currentTimeMillis() % 360) * speed), speed);
        }
    }

    public void movementEight(int speed) {
        // Spiral movement
        double angle = Math.toRadians(random.nextInt(360));
        double xVector = Math.cos(angle) * speed;
        double yVector = Math.sin(angle) * speed;
        this.setVelocity(xVector, yVector);
    }
}
