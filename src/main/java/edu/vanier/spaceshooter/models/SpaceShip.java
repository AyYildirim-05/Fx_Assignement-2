package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class SpaceShip extends FiringSprites {

    public final String player_spaceShip = "player/playerShip1_red.png";

    public int health_player = 3;

    public SpaceShip(String imagePath, double size, int health, String type, double x, double y) {
        super(imagePath, size, health, type, x, y);
    }

    public String getPlayer_spaceShip() {
        return player_spaceShip;
    }

    public int getHealth_player() {
        return health_player;
    }

    public void setHealth_player(int health_player) {
        this.health_player = health_player;
    }

    public void lose_health() {
        health_player--;
    }

    public void gain_health() {
        health_player++;
    }
}
