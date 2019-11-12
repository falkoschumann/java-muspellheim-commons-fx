/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import javafx.scene.*;
import javafx.stage.*;
import lombok.*;

/**
 * Stage controller for a stage holding a view controller for root of stages scene.
 * <p>
 * The stage controller can be used as is or it can be extended, see {@link #init(Parent, Object)}.
 *
 * @param <V> the type of root view in stages scene
 * @param <C> the type of view controller
 */
public class StageController<V extends Parent, C> {

    private final Class<C> controllerType;
    private final Stage stage;

    private boolean loaded;

    /**
     * Create a stage controller with a new stage.
     *
     * @param controllerType the type of view controller for stages scene
     */
    public StageController(Class<C> controllerType) {
        this(controllerType, new Stage());
    }

    /**
     * Create a stage controller with a existing stage.
     *
     * @param controllerType the type of view controller for stages scene
     * @param stage          a stage
     */
    public StageController(@NonNull Class<C> controllerType, @NonNull Stage stage) {
        this.controllerType = controllerType;
        this.stage = stage;
    }

    /**
     * Get the stages title.
     *
     * @return the stage title
     */
    public String getTitle() {
        return stage.getTitle();
    }

    /**
     * Set the stages title.
     *
     * @param value the new stage title
     */
    public void setTitle(String value) {
        stage.setTitle(value);
    }

    /**
     * Get the stages configuration.
     *
     * @return the stage configuration
     */
    public WindowConfiguration getConfiguration() {
        return WindowConfiguration.of(
            (int) stage.getX(),
            (int) stage.getY(),
            (int) stage.getWidth(),
            (int) stage.getHeight()
        );
    }

    /**
     * Set the stages configuration.
     *
     * @param value the new stage configuration
     */
    public void setConfiguration(WindowConfiguration value) {
        stage.setX(value.getX());
        stage.setY(value.getY());
        stage.setWidth(value.getWidth());
        stage.setHeight(value.getHeight());
    }

    /**
     * The managed stage of this stage controller.
     *
     * @return the managed stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Show the managed stage of this stage controller.
     */
    public void show() {
        load();
        stage.show();
    }

    /**
     * Show and wait the managed stage of this stage controller.
     */
    public void showAndWait() {
        load();
        stage.showAndWait();
    }

    /**
     * Hide the managed stage of this stage controller.
     */
    public void hide() {
        stage.hide();
    }

    private void load() {
        if (loaded) {
            return;
        }

        loaded = true;
        ViewControllerFactory<V, C> factory = ViewControllerFactory.load(controllerType);
        Scene scene = new Scene(factory.getView());
        stage.setScene(scene);
        init(factory.getView(), factory.getController());
    }

    /**
     * Can be used to initialize the stage controller after view controller is loaded.
     *
     * @param view       the loaded view
     * @param controller the loaded controller
     */
    protected void init(V view, C controller) {
        // do nothing
    }

}
