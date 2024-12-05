package edu.vanier.spaceshooter.support;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class Util extends ImageView {
    public final String BACKGROUND_IMAGE_1 = "/background/blue.png";
    public final String BACKGROUND_IMAGE_2 = "/background/purple.png";
    public final String BACKGROUND_IMAGE_3 = "/background/darkPurple.png";
    public final String BACKGROUND_IMAGE_4 = "/background/black.png";
    public final String HP_REPRESENTATION = "/player/playerLife1_red.png";
    public final List<String> imageContainer = List.of(
            BACKGROUND_IMAGE_1, BACKGROUND_IMAGE_2, BACKGROUND_IMAGE_3, BACKGROUND_IMAGE_4
    );

    public ArrayList<ImageView> container = new ArrayList<>();

    public String temp = "imageView";

    public Image image;

    public HBox enteredBox;


    public Util(HBox hBox) {
        image = new Image(getClass().getResource("/player/playerLife1_red.png").toExternalForm());
        this.enteredBox = hBox;
        for (int i = 1; i <= 3; i++) {
            temp += i;
            ImageView imView = new ImageView(image);
            imView.setId(temp);
            hBox.getChildren().add(imView);
            container.add(imView);
            temp = "imageView";
        }
    }

    public void settingBackground(int imageNumber, Pane pane) {
        Image backgroundImage = new Image(getClass().getResource(imageContainer.get(imageNumber - 1)).toExternalForm());

        BackgroundSize backgroundSize = new BackgroundSize(
                pane.getWidth(),
                pane.getHeight(),
                false,
                false,
                true,
                true);

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        pane.setBackground(new javafx.scene.layout.Background(bgImage));
    }

    public void playerAddHP(HBox hBox) {
        int newId = container.size() + 1;
        if (container.size() < 3) {
            image = new Image(getClass().getResource("/player/playerLife1_red.png").toExternalForm());
            ImageView newHealth = new ImageView(image);
            newHealth.setId(temp + newId);
            hBox.getChildren().add(newHealth);
            container.add(newHealth);
        } else {
            System.out.println("HP is already 3!");
        }
    }

    public void removeLastHealth(HBox hBox) {
        if (!container.isEmpty()) {
            ImageView lastHealth = container.get(container.size() - 1);

            container.remove(container.size() - 1);

            hBox.getChildren().remove(lastHealth);
        } else {
            System.out.println("No health points to remove!");
        }
    }

    public String getBACKGROUND_IMAGE_1() {
        return BACKGROUND_IMAGE_1;
    }

    public String getBACKGROUND_IMAGE_2() {
        return BACKGROUND_IMAGE_2;
    }

    public String getBACKGROUND_IMAGE_3() {
        return BACKGROUND_IMAGE_3;
    }

    public String getBACKGROUND_IMAGE_4() {
        return BACKGROUND_IMAGE_4;
    }
}
