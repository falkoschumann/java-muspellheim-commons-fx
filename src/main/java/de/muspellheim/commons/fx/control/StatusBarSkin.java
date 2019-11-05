/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import javafx.beans.*;
import javafx.beans.binding.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

class StatusBarSkin extends SkinBase<StatusBar> {

    StatusBarSkin(StatusBar statusBar) {
        super(statusBar);

        //
        // Create
        //

        GridPane root = new GridPane();
        root.setPadding(new Insets(4));
        getChildren().add(root);

        HBox leftItems = new HBox();
        leftItems.setSpacing(5);
        leftItems.getStyleClass().add("left-items");
        leftItems.getChildren().setAll(getSkinnable().getLeftItems());
        GridPane.setMargin(leftItems, new Insets(0, 10, 0, 0));
        GridPane.setFillHeight(leftItems, true);
        GridPane.setVgrow(leftItems, Priority.ALWAYS);
        root.add(leftItems, 0, 0);

        Label text = new Label();
        text.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        text.getStyleClass().add("status-text");
        GridPane.setHgrow(text, Priority.ALWAYS);
        root.add(text, 1, 0);

        ProgressBar progress = new ProgressBar();
        progress.getStyleClass().add("status-progress");
        root.add(progress, 2, 0);

        HBox rightItems = new HBox();
        rightItems.setSpacing(5);
        rightItems.getStyleClass().add("right-items");
        rightItems.getChildren().setAll(getSkinnable().getRightItems());
        GridPane.setMargin(rightItems, new Insets(0, 0, 0, 10));
        GridPane.setFillHeight(rightItems, true);
        GridPane.setVgrow(rightItems, Priority.ALWAYS);
        root.add(rightItems, 3, 0);

        //
        // Bind
        //

        leftItems.managedProperty().bind(Bindings.isNotEmpty(getSkinnable().getLeftItems()));
        getSkinnable().getLeftItems().addListener((Observable o) -> leftItems.getChildren().setAll(getSkinnable().getLeftItems()));

        text.textProperty().bind(getSkinnable().textProperty());
        text.styleProperty().bind(getSkinnable().styleProperty());

        BooleanBinding notZeroProgress = Bindings.notEqual(0, getSkinnable().progressProperty());
        progress.progressProperty().bind(getSkinnable().progressProperty());
        progress.visibleProperty().bind(notZeroProgress);
        progress.managedProperty().bind(notZeroProgress);

        rightItems.managedProperty().bind(Bindings.isNotEmpty(getSkinnable().getRightItems()));
        getSkinnable().getRightItems().addListener((Observable o) -> rightItems.getChildren().setAll(getSkinnable().getRightItems()));
    }

}
