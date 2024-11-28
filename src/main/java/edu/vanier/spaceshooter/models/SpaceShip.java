package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class SpaceShip extends FiringSprites {

    public final String player_spaceShip = "/player/playerShip1_red.png";

    public int health_player = 3;

    public SpaceShip(String imagePath, double width, double height, int health, String type, double x, double y) {
        super(imagePath, width, height, health, type, x, y);
    }

    public String getPlayer_spaceShip() {
        return player_spaceShip;
    }

    public int getHealth_player() {
        return health_player;
    }

    public void lose_health() {
        if (this.health > 0) {
            this.setHealth(this.getHealth() - 1);
        } else {
            this.setDead(true);
        }
    }

    public void gain_health() {
        health_player++;
    }
}
