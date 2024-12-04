package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.support.LevelController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Missile extends Sprite {

    public LevelController levelController = new LevelController();


    public Missile(String imagePath, double width, double height, int health, String type, double x, double y, double dx, double dy) {
        super(imagePath, width, height, health, type, x, y, dx, dy);
    }
}

