package edu.vanier.spaceshooter.models;

import javafx.scene.media.MediaView;

/**
 * Class that generates missiles.
 * The class plays a sound whenever a new instance is generated.
 */
public class Missile extends Sprite {
    public String firingSound = "/sound_effects/15640-laser_gun_shot_3.wav";

    private MediaView shooting;

    public Missile(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
        soundClass.playSound(soundClass.getShooting(), soundClass.getFiringSound(), 0.3);
    }
}

