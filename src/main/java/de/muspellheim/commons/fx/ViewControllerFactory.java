/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import java.io.*;
import java.net.*;

import javafx.fxml.*;
import javafx.scene.*;
import lombok.*;

/**
 * Factory for view controller.
 *
 * @param <V> the type of the root view
 * @param <C> the type of the controller
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("checkstyle:VisibilityModifier")
public class ViewControllerFactory<V extends Node, C> {

    /**
     * The view controller.
     *
     * @return the controller
     */
    @NonNull C controller;

    /**
     * The root view of the view controller.
     *
     * @return the root view
     */
    @NonNull V view;

    /**
     * Load a controller by class and return the factory for this controller.
     * <p>
     * Load controller and view with {@link FXMLLoader}. If the controller class name contains "Controller" this string
     * is removed.
     *
     * @param controllerType the class of controller to load
     * @param <V>            the type of loaded view
     * @param <C>            the type of loaded controller
     * @return the factory for loaded controller and view
     */
    @SuppressWarnings("unchecked")
    public static <V extends Node, C> ViewControllerFactory<V, C> load(@NonNull Class<C> controllerType) {
        try {
            String viewName = controllerType.getSimpleName().replace("Controller", "") + ".fxml";
            URL url = controllerType.getResource(viewName);
            FXMLLoader loader = new FXMLLoader(url);
            Node view = loader.load();
            Object controller = loader.getController();
            return (ViewControllerFactory<V, C>) new ViewControllerFactory(controller, view);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not load view", e);
        }
    }

}
