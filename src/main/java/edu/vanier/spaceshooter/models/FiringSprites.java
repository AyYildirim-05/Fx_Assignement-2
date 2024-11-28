package edu.vanier.spaceshooter.models;

public abstract class FiringSprites extends Sprite {

    public double missileSpeed;

    public FiringSprites(String imagePath, double width, double height, int health, String type, double x, double y) {
        super(imagePath, width, height, health, type, x, y);
    }


    public  void singleShot() {

    }

    public  void doubleShot() {

    }
    public void tripleShot() {

    }
    public  void circleShot() {

    }
    public void machineGun() {

    }

    public void homing() {

    }
}
