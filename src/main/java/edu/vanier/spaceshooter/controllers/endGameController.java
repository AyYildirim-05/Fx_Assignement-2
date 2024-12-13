package edu.vanier.spaceshooter.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

/**
 * Class that handles the end game screen.
 */

public class endGameController {
    @FXML
    StackPane endGamePane;

    @FXML
    VBox vContainer;

    @FXML
    Label losingMessage;

    @FXML
    Label finalScore;

    int lastScore = 0;

    @FXML
    public void initialize() {
        finalScore.setText("Final Score:" + lastScore);
    }

    /**
     * Method that gets the user's final score at the end of the game.
     * @param score the final user score
     */
    public void getScore(int score) {
        this.lastScore = score;
    }

}
