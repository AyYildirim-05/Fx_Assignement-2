package edu.vanier.spaceshooter.support;

import edu.vanier.spaceshooter.models.Sprite;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;

public class PlayingSound {
    private MediaView hit;
    private String hitSound = "/sound_effects/explosion.mp3";

    public String firingSound = "/sound_effects/15640-laser_gun_shot_3.wav";

    private MediaView shooting;

    private static Image explosionGif;



    public void playSound(MediaView mediaView, String sound, double volume) {
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
        mediaView.getMediaPlayer().setVolume(volume);
        mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getStartTime());
        mediaView.getMediaPlayer().play();
    }

    private ImageView explosionView;

    public void explosionGif(Sprite sp, Pane animationPanel) {
        try {
            if (explosionGif == null) {
                explosionGif = new Image(getClass().getResource("/visual_effects/explosion.gif").toExternalForm());
            }

            // Create a new ImageView for the explosion
            explosionView = new ImageView(explosionGif);
            explosionView.setFitWidth(50); // Set appropriate size
            explosionView.setFitHeight(50);
            explosionView.setTranslateX(sp.getTranslateX());
            explosionView.setTranslateY(sp.getTranslateY());

            animationPanel.getChildren().add(explosionView);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> animationPanel.getChildren().remove(explosionView));
            pause.play();

        } catch (Exception e) {
            System.err.println("Failed to load explosion GIF: " + e.getMessage());
        }
    }

    public MediaView getHit() {
        return hit;
    }

    public String getHitSound() {
        return hitSound;
    }

    public String getFiringSound() {
        return firingSound;
    }

    public MediaView getShooting() {
        return shooting;
    }
}
