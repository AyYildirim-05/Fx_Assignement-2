package edu.vanier.spaceshooter.support;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Background {
    public final String BACKGROUND_IMAGE_1 = "/background/blue.png";
    public final String BACKGROUND_IMAGE_2 = "/background/purple.png";
    public final String BACKGROUND_IMAGE_3 = "/background/darkPurple.png";
    public final String BACKGROUND_IMAGE_4 = "/background/black.png";

    public void settingBackground(String backgroundColor, Pane pane) {
        Image backgroundImage = new Image(getClass().getResource(backgroundColor).toExternalForm());

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
