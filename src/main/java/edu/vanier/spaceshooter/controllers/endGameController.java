package edu.vanier.spaceshooter.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;


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

    public void getScore(int score) {
        this.lastScore = score;
    }
}
