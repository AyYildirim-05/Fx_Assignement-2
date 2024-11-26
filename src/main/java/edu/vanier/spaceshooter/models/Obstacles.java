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

    public String getObstacle_1() {
        return obstacle_1;
    }

    public String getObstacle_2() {
        return obstacle_2;
    }

    public String getObstacle_3() {
        return obstacle_3;
    }

    public String getObstacle_4() {
        return obstacle_4;
    }

    public int getObstacle_health() {
        return obstacle_health;
    }

    public void setObstacle_health(int obstacle_health) {
        this.obstacle_health = obstacle_health;
    }

    public void decreaseObstacleHealth() {
        if (this.getHealth() > 0) {
            this.setHealth(this.getHealth() - 1);
        } else {
            this.setDead(true);
        }
    }
}
