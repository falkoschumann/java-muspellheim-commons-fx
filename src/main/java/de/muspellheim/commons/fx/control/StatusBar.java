/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class StatusBar extends Control {

    private final StringProperty text = new SimpleStringProperty(this, "text");
    private final DoubleProperty progress = new SimpleDoubleProperty(this, "progress");
    private final ObservableList<Node> leftItems = FXCollections.observableArrayList();
    private final ObservableList<Node> rightItems = FXCollections.observableArrayList();

    public StatusBar() {
        getStyleClass().add("status-bar");
    }

    public final StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(String text) {
        this.text.set(text);
    }

    public final DoubleProperty progressProperty() {
        return progress;
    }

    public final double getProgress() {
        return progress.get();
    }

    public final void setProgress(double progress) {
        this.progress.set(progress);
    }

    public final ObservableList<Node> getLeftItems() {
        return leftItems;
    }

    public final ObservableList<Node> getRightItems() {
        return rightItems;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new StatusBarSkin(this);
    }

}
