/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.control.TableCell;

/**
 * Table cell for a {@link LocalDate}.
 *
 * @param <S> type of table items
 */
public class DateTableCell<S> extends TableCell<S, LocalDate> {

  private final DateTimeFormatter formatter;

  /**
   * Create a cell with given date style.
   *
   * @param dateStyle the format style to formatting date in cell
   */
  public DateTableCell(FormatStyle dateStyle) {
    this(DateTimeFormatter.ofLocalizedDate(dateStyle));
  }

  /**
   * Create a cell with given formatter.
   *
   * @param formatter the formatter to formatting date in cell
   */
  public DateTableCell(DateTimeFormatter formatter) {
    this.formatter = formatter;
  }

  protected void updateItem(LocalDate item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setText(item.format(formatter));
    }
  }
}
