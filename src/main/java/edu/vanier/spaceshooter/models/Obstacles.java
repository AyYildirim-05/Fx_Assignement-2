package edu.vanier.spaceshooter.models;

public class Obstacles extends Sprite {
    public final String obstacle_1 = "obstacles/meteorGrey_big1.png";
    public final String obstacle_2 = "obstacles/meteorGrey_big2.png";
    public final String obstacle_3 = "obstacles/meteorGrey_big3.png";
    public final String obstacle_4 = "obstacles/meteorGrey_big4.png";

    public int obstacle_health = 2;

    public Obstacles(String imagePath, double size, int health, String type, double x, double y) {
        super(imagePath, size, health, type, x, y);
    }
}
