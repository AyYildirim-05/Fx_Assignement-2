package edu.vanier.spaceshooter.models;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

    public Image image;
    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;
    public double width;
    public double height;
    public boolean dead = false;
    public String type;
    private AudioClip shoot;

    public Sprite(int x, int y, int width, int height, String type, Color color) {
        super(width, height, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

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

    public void makeNoise() {
        shoot = new AudioClip(getClass().getResource("/sounds/picked-coin.wav").toExternalForm());
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]" + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public void moveLeft() { setTranslateX(getTranslateX() - 5); }
    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }
    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }
    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }
    public boolean isDead() {
        return dead;
    }
    public String getType() {
        return type;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
