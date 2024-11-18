package edu.vanier.spaceshooter.models;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class Player extends Sprite{

    private AudioClip shoot;
    public Player(int x, int y, int width, int height, String type, Color color) {
        super(x, y, width, height, type, color);
    }

    public void makeNoise() {
        shoot = new AudioClip(getClass().getResource("/sounds/picked-coin.wav").toExternalForm());
    }
}
