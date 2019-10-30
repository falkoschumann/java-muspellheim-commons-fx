/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import java.io.*;
import java.util.*;

import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Show a dialog for an exception.
 * <p>
 * The exception message is displayed and the stacktrace can be displayed in detail area of the dialog.
 */
public class ExceptionDialog extends Alert {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ExceptionDialog.class.getName());

    /**
     * Create a dialog with specified exception.
     *
     * @param exception the exception to display.
     */
    public ExceptionDialog(Throwable exception) {
        super(AlertType.ERROR);

        setTitle(resourceBundle.getString("exceptionDialog.title"));
        setHeaderText(getExceptionName(exception));
        setContentText(exception.getLocalizedMessage());

        String exceptionText = getExceptionStackTrace(exception);
        GridPane details = createDetails(exceptionText);
        getDialogPane().setExpandableContent(details);
    }

    private static String getExceptionName(Throwable exception) {
        String name = exception.getClass().getSimpleName();
        name = name.replaceAll("([A-Z])", " $1");
        return name;
    }

    private static String getExceptionStackTrace(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

    private GridPane createDetails(String exceptionText) {
        GridPane details = new GridPane();
        details.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(resourceBundle.getString("exceptionDialog.stacktrace"));
        details.add(label, 0, 0);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setPrefColumnCount(70);
        textArea.setWrapText(false);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        details.add(textArea, 0, 1);

        return details;
    }

}
