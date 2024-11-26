package edu.vanier.spaceshooter.models;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Sprite extends ImageView {
    public int speedValue = 5;

    public int health;
    public ImageView imageView;
    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;
    public double height;
    public double width;
    public boolean dead = false;
    public String type;

    public Sprite(String imagePath, double size, int health, String type, double x, double y) {
        this.type = type; // deciding if the sprite is player, enemy. or other
        this.health = health;
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = size;
        this.height = size;
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        setImage(image);
        setX(x); // set positioning
        setY(y);
        setTranslateX(x); // set incremental changes
        setTranslateY(y);
        setFitWidth(size);
        setFitHeight(size);
        setPreserveRatio(true);
    }

    // todo might not use it at all
//    public void moveLeft() { setTranslateX(getTranslateX() - speedValue); }
//    public void moveRight() {
//        setTranslateX(getTranslateX() + speedValue);
//    }
//    public void moveUp() {
//        setTranslateY(getTranslateY() - speedValue);
//    }
//    public void moveDown() {
//        setTranslateY(getTranslateY() + speedValue);
//    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }

    public boolean isDead() {
        return dead;
    }


    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(imageView.getImage(), positionX, positionY);
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void decreaseHealth() {
        if (health > 0) {
            health--;
        }
        if (health <= 0) {
            setDead(true);
        }
    }
}

