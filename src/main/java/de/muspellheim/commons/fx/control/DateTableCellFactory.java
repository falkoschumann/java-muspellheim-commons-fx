/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.LocalDate;
import java.time.format.FormatStyle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Table cell factory for a {@link LocalDate}.
 *
 * @param <S> type of table items
 */
public class DateTableCellFactory<S>
    implements Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> {

  /**
   * The date style for formatting the date in cell.
   *
   * @return the date style
   */
  @Getter @Setter @NonNull private FormatStyle dateStyle = FormatStyle.MEDIUM;

  @Override
  public TableCell<S, LocalDate> call(TableColumn<S, LocalDate> param) {
    return new DateTableCell<>(dateStyle);
  }
}
