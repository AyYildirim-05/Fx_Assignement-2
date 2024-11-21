package edu.vanier.spaceshooter.models;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Sprite extends Rectangle {
    public ImageView imageView;
    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;
    public boolean dead = false;
    public String type;
    public int health;

    /**
     * Constructor of all the characters in the application
     * @param imagePath Gets the object sprite location
     * @param type Defines the type of the object
     *             // todo will be useful to avoid collisions between enemy objects
     * @param x Left-to-right coordinate of the top-left corner of the ImageView
     * @param y Top-to-bottom coordinate of the top-left corner of the ImageView
     */
    public Sprite(String imagePath, String type, int health, double x, double y, double height, double width) {
        this.type = type; // deciding if the sprite is player, enemy. or other
        this.health = health;
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setX(x); // set positioning
        imageView.setY(y);
        setTranslateX(x); // set incremental changes
        setTranslateY(y);
    }

    // abstract methods to implement in each class
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void moveDown();
    public abstract void moveUp();

    public abstract void shoot();
    public abstract void makeShootingNoise();

    // some getter and setters

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }


    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // other methods
    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean intersects(Sprite sprite) {
        Bounds thisBounds = this.imageView.getBoundsInParent();
        Bounds otherBounds = sprite.imageView.getBoundsInParent();
        return thisBounds.intersects(otherBounds);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(imageView.getImage(), positionX, positionY);
    }
}

