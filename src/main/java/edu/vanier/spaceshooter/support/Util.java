package edu.vanier.spaceshooter.support;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public final String BACKGROUND_IMAGE_1 = "/background/blue.png";
    public final String BACKGROUND_IMAGE_2 = "/background/purple.png";
    public final String BACKGROUND_IMAGE_3 = "/background/darkPurple.png";
    public final String BACKGROUND_IMAGE_4 = "/background/black.png";

    public final List<String> imageContainer = List.of(
            BACKGROUND_IMAGE_1, BACKGROUND_IMAGE_2, BACKGROUND_IMAGE_3, BACKGROUND_IMAGE_4
    );
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
