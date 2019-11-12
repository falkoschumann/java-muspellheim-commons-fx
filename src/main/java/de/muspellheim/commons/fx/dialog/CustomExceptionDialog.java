/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import javafx.scene.control.*;
import org.controlsfx.dialog.*;

/**
 * Show a dialog for an exception.
 * <p>
 * Uses exception name for header text and fixes unreleased merge request
 * <a href="https://github.com/controlsfx/controlsfx/pull/1155">ControlsFX #1155</a>.
 */
public class CustomExceptionDialog extends ExceptionDialog {

    /**
     * Create a dialog with specified exception.
     *
     * @param exception the exception to display.
     */
    public CustomExceptionDialog(Throwable exception) {
        super(exception);

        setHeaderText(getExceptionName(exception));
        setContentText(exception.getLocalizedMessage());

        // Workaround: Unreleased merge request https://github.com/controlsfx/controlsfx/pull/1155
        Label content = (Label) getDialogPane().getContent();
        content.setWrapText(true);
        content.setPrefWidth(600);
    }

    private static String getExceptionName(Throwable exception) {
        String name = exception.getClass().getSimpleName();
        name = name.replaceAll("([A-Z])", " $1");
        name = name.trim();
        return name;
    }

}
