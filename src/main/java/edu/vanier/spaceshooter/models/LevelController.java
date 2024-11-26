package edu.vanier.spaceshooter.models;

import java.util.ArrayList;

public class LevelController {
    ArrayList<Integer> numberEnemies = new ArrayList<>(10);


    public void increaseNumberEnemies() {
        numberEnemies.add(5);
    }
}




