package edu.vanier.spaceshooter.models;

import java.util.Random;

/**
 * Class that defines the invader class.
 */
public class Invader extends FiringSprites {
    Random random = new Random();
    int randomNumber;

    public Invader(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);

    }

    /**
     * Moves the object in a horizontal direction.
     * The direction is randomly chosen between left or right.
     * @param speed the speed at which the object moves horizontally.
     */
    public void movementOne(int speed) {
        this.setVelocity(speed, 0);
        randomNumber = random.nextInt(2);
        switch (randomNumber) {
            case 0 -> this.setVelocity(speed, 0);
            case 1 -> this.setVelocity(-speed, 0);
        }
    }

    /**
     * Moves the object in a vertical direction.
     * The direction is randomly chosen between up or down.
     * @param speed the speed at which the object moves vertically.
     */
    public void movementTwo(int speed) {
        randomNumber = random.nextInt(2);
        switch (randomNumber) {
            case 0 -> this.setVelocity(0, speed);
            case 1 -> this.setVelocity(0, -speed);
        }
    }

    /**
     * Moves the object diagonally.
     * The diagonal direction is randomly chosen between four possible directions:
     * top-right, top-left, bottom-right, or bottom-left.
     * @param speed the speed at which the object moves diagonally.
     */
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

    /**
     * Moves the object in one of eight directions:
     * - Horizontal: right or left
     * - Vertical: up or down
     * - Diagonal: top-right, top-left, bottom-right, or bottom-left
     * The direction is randomly chosen.
     * @param speed the speed at which the object moves.
     */
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

    /**
     * Moves the object in a random direction.
     * Both the horizontal (x-axis) and vertical (y-axis) components of the movement
     * are randomly chosen within the range of `-speed` to `+speed`.
     * @param speed the maximum magnitude of the object's speed in any direction.
     */
    public void movementFive(double speed) {
        double xRandom = (random.nextDouble() * 2 - 1) * speed;
        double yRandom = (random.nextDouble() * 2 - 1) * speed;
        this.setVelocity(xRandom, yRandom);
    }

    /**
     * Moves the object in a random direction determined by a random angle.
     * The angle is randomly chosen between 0 and 360 degrees.
     * The resulting x and y velocity components are calculated using trigonometry.
     * @param speed the magnitude of the object's speed in the chosen direction.
     */
    public void movementSix(double speed) {
        double angle = Math.toRadians(random.nextInt(360));
        double xVector = Math.cos(angle) * speed;
        double yVector = Math.sin(angle) * speed;
        this.setVelocity(xVector, yVector);
    }

}
