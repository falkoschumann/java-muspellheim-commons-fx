/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class HintPopup extends Popup {

    private Label label;

    public HintPopup() {
        Pane pane = new StackPane();
        pane.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 7, 0, 4, 4);"
            + "-fx-padding: 5;"
            + "-fx-border-width: 1;"
            + "-fx-background-color: #ffdddd;"
            + "-fx-border-color: #aa0000;");
        getContent().add(pane);

        label = new Label();
        label.setStyle("-fx-font-weight: bold;"
            + "-fx-text-fill: #aa0000;"
            + "-fx-font-size: 0.875em;");
        pane.getChildren().add(label);
    }

    public void show(String text, Region owner) {
        label.setText(text);
        Point2D location = getLocation(owner);
        show(owner, location.getX(), location.getY());
    }

    private Point2D getLocation(Region owner) {
        Scene scene = owner.getScene();
        Window window = scene.getWindow();
        return owner.localToScene(0.0, 0.0)
            .add(0, owner.getHeight() + 5)
            .add(scene.getX(), scene.getY())
            .add(window.getX(), window.getY());
    }

}
