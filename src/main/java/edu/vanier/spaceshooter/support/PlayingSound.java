package edu.vanier.spaceshooter.support;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;

public class PlayingSound {
    private MediaView hit;
    private String hitSound = "/sound_effects/explosion.mp3";

    public String firingSound = "/sound_effects/15640-laser_gun_shot_3.wav";

    private MediaView shooting;


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
