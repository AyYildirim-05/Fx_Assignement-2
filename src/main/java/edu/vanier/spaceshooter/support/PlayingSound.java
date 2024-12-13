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

/**
 * Class that handles the sounds played within the game.
 */
public class PlayingSound {
    private MediaView hit;
    private String hitSound = "/sound_effects/explosion.mp3";

    public String firingSound = "/sound_effects/15640-laser_gun_shot_3.wav";

    private MediaView shooting;

    private static Image explosionGif;


    /**
     * Class that plays an audio file.
     * @param mediaView the entered media view instance.
     * @param sound the path of the played sound.
     * @param volume the volume of the sound played.
     */
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

    /**
     * Method that plays a gif animation.
     * @param sp the sprite used to set the location of the gif.
     * @param animationPanel the pane in which the animation is played.
     */
    public void explosionGif(Sprite sp, Pane animationPanel) {
        try {
            if (explosionGif == null) {
                explosionGif = new Image(getClass().getResource("/visual_effects/explosion.gif").toExternalForm());
            }

            ImageView explosionView = new ImageView(explosionGif);
            explosionView.setFitWidth(50);
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

    /**
     * Returns the MediaView object associated with the "hit" action.
     * @return a MediaView object representing the "hit" media.
     */
    public MediaView getHit() {
        return hit;
    }

    /**
     * Returns the sound file name or path for the "hit" action.
     * @return a String representing the "hit" sound file.
     */
    public String getHitSound() {
        return hitSound;
    }

    /**
     * Returns the sound file name or path for the "firing" action.
     * @return a String representing the "firing" sound file.
     */
    public String getFiringSound() {
        return firingSound;
    }

    /**
     * Returns the MediaView object associated with the "shooting" action.
     * @return a MediaView object representing the "shooting" media.
     */
    public MediaView getShooting() {
        return shooting;
    }

}
