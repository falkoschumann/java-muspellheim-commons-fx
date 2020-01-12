/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import javafx.scene.control.TableCell;

/**
 * This Table cell shows enum {@code name()} instead of {@code toString()}.
 *
 * @param <S> type of table items
 */
public class EnumTableCell<S> extends TableCell<S, Enum<?>> {

  protected void updateItem(Enum<?> item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setText(item.name());
    }
  }
}
