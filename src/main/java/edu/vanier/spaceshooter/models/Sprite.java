package edu.vanier.spaceshooter.models;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Sprite extends ImageView {
    public int health;
    public ImageView imageView;
    public double positionX;
    public double positionY;
    public double height;
    public double width;
    public boolean dead = false;
    public String type;
    public double dx;
    public double dy;
    public double x;
    public double y;

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

    public void moveLeft(int speed) { setTranslateX(getTranslateX() - speed); }
    public void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
    }
    public void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
    }
    public void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
    }

    public void move() {

//        if () here we do the logic of border
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }


    public void lose_health() {
        if (this.health > 0) {
            this.setHealth(this.getHealth() - 1);
        }
    }

    public void gain_health() {
        this.health++;
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

    public boolean checkHealth() {
        return health > 0;
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
    public static boolean isCollision(Node sprite1, Node sprite2) {
        return sprite1.getBoundsInParent().intersects(sprite2.getBoundsInParent());
    }

}

