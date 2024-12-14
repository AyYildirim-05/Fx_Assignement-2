package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.PlayingSound;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;

public abstract class Sprite extends ImageView {
    public int health;
    public double height;

    public double width;
    public String type;
    public double dx;
    public double dy;
    public double x;
    public double y;
    public boolean dead = false;
    private MediaView hit;
    public String imagePath;
    final PlayingSound soundClass = new PlayingSound();

    public long lastFiredTime = 0;

    /**
     * Method that creates a sprite instance.
     * @param imagePath the string of the sprite the instance uses.
     * @param width the width of the sprite.
     * @param height the height of the sprite
     * @param health the health points of the sprite instance,
     * @param type the type of the sprite instance.
     * @param x the x position of the sprite on the screen.
     * @param y the y position of the sprite on the screen.
     * @param dx the initial x velocity.
     * @param dy the initial y velocity.
     */
    public Sprite(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        this.type = type;
        this.health = health;
        this.width = width;
        this.height = height;
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        setImage(image);
        setTranslateX(x);
        setTranslateY(y);
        setFitWidth(width);
        setFitHeight(height);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Method that moves a sprite to the left.
     * @param speed how fast the sprite is going to move.
     */
    public void moveLeft(double speed) { setTranslateX(getTranslateX() - speed); }

    /**
     * Method that moves a sprite to the right.
     * @param speed how fast the sprite is going to move.
     */
    public void moveRight(double speed) {
        setTranslateX(getTranslateX() + speed);
    }

    /**
     * Method that moves a sprite above.
     * @param speed how fast the sprite is going to move.
     */
    public void moveUp(double speed) {
        setTranslateY(getTranslateY() - speed);
    }

    /**
     * Method that moves a sprite below.
     * @param speed how fast the sprite is going to move.
     */
    public void moveDown(double speed) {
        setTranslateY(getTranslateY() + speed);
    }

    /**
     * Method that changes the position of the sprite in the game loop.
     */
    public void move() {
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
    }

    /**
     * Method that moves the invaders by getting the height and width of the panel to set borders.
     */
    public void moveInvaders() {
        double nextX = this.getTranslateX() + dx;
        double nextY = this.getTranslateY() + dy;

        double panelWidth = getParent().getLayoutBounds().getWidth();
        double panelHeight = getParent().getLayoutBounds().getHeight();

        double leftLimit = 25;
        double rightLimit = panelWidth - leftLimit;
        double topLimit = 50;
        double bottomLimit = panelHeight - topLimit;

        if (nextX < leftLimit) {
            nextX = leftLimit;
            dx = -dx;
        } else if (nextX + this.getFitWidth() > rightLimit) {
            nextX = rightLimit - this.getFitWidth();
            dx = -dx;
        }

        if (nextY < topLimit) {
            nextY = topLimit;
            dy = -dy;
        } else if (nextY + this.getFitHeight() > bottomLimit) {
            nextY = bottomLimit - this.getFitHeight();
            dy = -dy;
        }

        this.setTranslateX(nextX);
        this.setTranslateY(nextY);
    }


    /**
     * Method that sets x and y velocity.
     * @param dx x velocity.
     * @param dy y velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Method that decreases the health point of a sprite.
     */
    public void lose_health() {
        if (this.health > 0) {
            this.setHealth(this.getHealth() - 1);
        }
        soundClass.playSound(soundClass.getHit(), soundClass.getHitSound(), 0.3);
     }

    /**
     * Method that increases the health point by one.
     */
    public void gain_health() {
        this.health++;
    }

    /**
     * Method that gets the type of the sprite.
     * @return the String value of the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Method that checks if the sprite health point is lower or equal to one.
     * @return boolean value
     */
    public boolean checkHealth() {
        return health <= 0;
    }

    /**
     * Method that gets the current value of the sprite health points.
     * @return the health point number.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method that sets the health of the sprite.
     * @param health entered health points.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Method that gets two sprite instances and checks for collision between them.
     * @param sprite1 the entered sprite one (spaceship).
     * @param sprite2 the entered sprite two (invader).
     * @return boolean value
     */
    public static boolean isCollision(Node sprite1, Node sprite2) {
        return sprite1.getBoundsInParent().intersects(sprite2.getBoundsInParent());
    }

    /**
     * Method that looks if a sprite is dead.
     * @return boolean value.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Method that sets the death of the sprite.
     * @param dead true.
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public long getLastFiredTime() {
        return lastFiredTime;
    }

    public void setLastFiredTime(long lastFiredTime) {
        this.lastFiredTime = lastFiredTime;
    }

}
