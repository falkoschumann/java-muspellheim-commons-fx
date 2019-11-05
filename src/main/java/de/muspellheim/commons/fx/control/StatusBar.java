/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * A status bar with a text and progress indicator.
 * <p>
 * Additional controls can be added left and right optionally.
 */
public class StatusBar extends Control {

    private final StringProperty text = new SimpleStringProperty(this, "text");
    private final DoubleProperty progress = new SimpleDoubleProperty(this, "progress");
    private final ObservableList<Node> leftItems = FXCollections.observableArrayList();
    private final ObservableList<Node> rightItems = FXCollections.observableArrayList();

    /**
     * Create a status bar.
     */
    public StatusBar() {
        getStyleClass().add("status-bar");
    }

    /**
     * The current set status text.
     *
     * @return the status text
     */
    public final StringProperty textProperty() {
        return text;
    }

    /**
     * Get the status text.
     *
     * @return the status text
     */
    public final String getText() {
        return text.get();
    }

    /**
     * Set the status text.
     *
     * @param text a status text
     */
    public final void setText(String text) {
        this.text.set(text);
    }

    /**
     * The current progress between 0.0 and 1.0.
     * <p>
     * A progress of 0.0 hide the progress indicator.
     *
     * @return the progress
     */
    public final DoubleProperty progressProperty() {
        return progress;
    }

    /**
     * Get the progress.
     *
     * @return the progress
     */
    public final double getProgress() {
        return progress.get();
    }

    /**
     * Set the progress.
     *
     * @param progress a progress
     */
    public final void setProgress(double progress) {
        this.progress.set(progress);
    }

    /**
     * The list of controls on the left side of this status bar.
     *
     * @return the left items
     */
    public final ObservableList<Node> getLeftItems() {
        return leftItems;
    }

    /**
     * The list of controls on the right side of this status bar.
     *
     * @return the right items
     */
    public final ObservableList<Node> getRightItems() {
        return rightItems;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new StatusBarSkin(this);
    }

}
