/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Table cell factory for a {@link LocalDateTime}.
 *
 * @param <S> type of table items
 */
public class DateTimeTableCellFactory<S>
    implements Callback<TableColumn<S, LocalDateTime>, TableCell<S, LocalDateTime>> {

  /**
   * The date style for formatting the date in cell.
   *
   * @return the date style
   */
  @Getter @Setter @NonNull private FormatStyle dateStyle = FormatStyle.MEDIUM;

  /**
   * The time style for formatting the time in cell.
   *
   * @return the time style
   */
  @Getter @Setter @NonNull private FormatStyle timeStyle = FormatStyle.MEDIUM;

  @Override
  public TableCell<S, LocalDateTime> call(TableColumn<S, LocalDateTime> param) {
    return new DateTimeTableCell<>(dateStyle, timeStyle);
  }
}
