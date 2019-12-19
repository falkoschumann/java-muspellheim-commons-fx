/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Show a dialog for an exception.
 *
 * <p>Uses exception name for header text and localized message for content text. The stack trace is
 * displayed in expandable content.
 */
public class ExceptionDialog extends Alert {

  private final ResourceBundle resources =
      ResourceBundle.getBundle("de.muspellheim.commons.fx.messages");

  /**
   * Create a dialog with specified exception.
   *
   * @param exception the exception to display.
   */
  public ExceptionDialog(Throwable exception) {
    super(AlertType.ERROR);

    setTitle(resources.getString("ExceptionDialog.title"));
    setHeaderText(getExceptionName(exception));
    setContentText(exception.getLocalizedMessage());

    GridPane expandableContent = new GridPane();
    expandableContent.setMaxWidth(Double.MAX_VALUE);

    Label label = new Label(resources.getString("ExceptionDialog.label"));
    expandableContent.add(label, 0, 0);

    String stackTrace = getStackTrace(exception);
    TextArea textArea = new TextArea(stackTrace);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setPrefColumnCount(60);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setHgrow(textArea, Priority.ALWAYS);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    expandableContent.add(textArea, 0, 1);

    getDialogPane().setExpandableContent(expandableContent);
  }

  private static String getExceptionName(Throwable exception) {
    String name = exception.getClass().getSimpleName();
    name = name.replaceAll("([A-Z])", " $1");
    name = name.trim();
    return name;
  }

  private static String getStackTrace(Throwable exception) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
