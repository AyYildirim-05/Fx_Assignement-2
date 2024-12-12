package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
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
    private static MediaView mediaView;

    public boolean dead = false;


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
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
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
        soundPlaying("/sound_effects/explosion.mp3");
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

    public void soundPlaying(String sound) {
        if (mediaView == null) {
            mediaView = new MediaView();
        }

        if (mediaView.getMediaPlayer() == null) {
            URL resourceUrl = getClass().getResource(sound);
            if (resourceUrl == null) {
                System.err.println("Sound file not found: " + sound);
                return;
            }
            Media media = new Media(resourceUrl.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }

        mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getStartTime());
        mediaView.getMediaPlayer().play();
    }



    public void explosionGif() {

    }

    public void setMediaView(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    public static void getMediaView(MediaView mW) {
        mediaView = mW;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }


}

