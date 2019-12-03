/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import java.util.ResourceBundle;
import org.controlsfx.dialog.ExceptionDialog;

/**
 * Show a dialog for an exception.
 *
 * <p>Uses exception name for header text and fixes unreleased merge request <a
 * href="https://github.com/controlsfx/controlsfx/pull/1155">ControlsFX #1155</a>.
 */
public class CustomExceptionDialog extends ExceptionDialog {

  private final ResourceBundle resources =
      ResourceBundle.getBundle("de.muspellheim.commons.fx.messages");

  /**
   * Create a dialog with specified exception.
   *
   * @param exception the exception to display.
   */
  public CustomExceptionDialog(Throwable exception) {
    super(exception);

    setTitle(resources.getString("ExceptionDialog.title"));
    setHeaderText(getExceptionName(exception));
    setContentText(exception.getLocalizedMessage());

    // Workaround of https://github.com/controlsfx/controlsfx/issues/1211
    getDialogPane().setContent(null);
  }

  private static String getExceptionName(Throwable exception) {
    String name = exception.getClass().getSimpleName();
    name = name.replaceAll("([A-Z])", " $1");
    name = name.trim();
    return name;
  }
}
