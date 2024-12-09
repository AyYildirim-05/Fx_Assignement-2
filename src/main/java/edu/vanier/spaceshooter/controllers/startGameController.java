package edu.vanier.spaceshooter.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class startGameController {
    @FXML
    StackPane startPane;
    @FXML
    VBox vertContainer;
    @FXML
    Button startButton;
    @FXML
    Button howTo;

    @FXML
    public void initialize() {
//        vertContainer.layoutXProperty().bind(startPane.widthProperty().subtract(vertContainer.widthProperty()).divide(2));
//        vertContainer.layoutYProperty().bind(startPane.heightProperty().subtract(vertContainer.heightProperty()).divide(2));




    }
}
