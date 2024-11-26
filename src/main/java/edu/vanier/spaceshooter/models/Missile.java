package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Missile extends Sprite{

    public final String redMissile_1 = "missile/laserRed06.png";
    public final String redMissile_2 = "missile/laserRed16.png";
    public final String blueMissile_1 = "missile/laserBlue06.png";
    public final String blueMissile_2 = "missile/laserBlue16.png";
    public final String greenMissile_1 = "missile/laserGreen12.png";
    public final String greenMissile_2 = "missile/laserGreen13.png";

    int health_missile = 1;

    public Missile(String imagePath, double size, int health, String type, double x, double y) {
        super(imagePath, size, health, type, x, y);
    }

    public int getHealth_missile() {
        return health_missile;
    }

    public void decreaseMissileHealth() {
        if (this.getHealth() > 0) {
            this.setHealth(this.getHealth() - 1);
        } else {
            this.setDead(true);
        }
    }

}
