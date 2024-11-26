package edu.vanier.spaceshooter.models;

import java.util.ArrayList;

public class LevelController {
    public ArrayList<Integer> numberEnemies = new ArrayList<>(10);

    public void increaseNumberEnemies() {
        numberEnemies.add(5);
    }

    public ArrayList<Integer> getNumberEnemies() {
        return numberEnemies;
    }

    public void setNumberEnemies(ArrayList<Integer> numberEnemies) {
        this.numberEnemies = numberEnemies;
    }

    @Override
    public String toString() {
        return "LevelController{" +
                "numberEnemies=" + numberEnemies +
                '}';
    }
}




