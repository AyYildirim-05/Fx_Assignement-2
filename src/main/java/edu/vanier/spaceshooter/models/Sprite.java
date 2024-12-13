package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.PlayingSound;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;

public abstract class Sprite extends ImageView {
    public int health;
    public ImageView imageView;
    public double positionX;
    public double positionY;
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

    public void moveLeft(double speed) { setTranslateX(getTranslateX() - speed); }
    public void moveRight(double speed) {
        setTranslateX(getTranslateX() + speed);
    }
    public void moveUp(double speed) {
        setTranslateY(getTranslateY() - speed);
    }
    public void moveDown(double speed) {
        setTranslateY(getTranslateY() + speed);
    }

    public void move() {
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }


    public void lose_health() {
        if (this.health > 0) {
            this.setHealth(this.getHealth() - 1);
        }
        soundClass.playSound(soundClass.getHit(), soundClass.getHitSound(), 0.3);
     }

    public void gain_health() {
        this.health++;
    }



    public String getType() {
        return type;
    }



    public boolean checkHealth() {
        return health >= 0;
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }


}

