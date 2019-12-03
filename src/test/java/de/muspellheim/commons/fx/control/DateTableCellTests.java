/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.time.LocalDate;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class DateTableCellTests {

  @BeforeEach
  void setUp() {
    Locale.setDefault(Locale.GERMAN);
  }

  @Test
  void value() {
    // Given
    DateTableCellFactory<Object> factory = new DateTableCellFactory<>();
    DateTableCell cell = (DateTableCell) factory.call(null);

    // When
    LocalDate date = LocalDate.of(2019, 11, 10);
    cell.updateItem(date, false);

    // Then
    assertEquals("10.11.2019", cell.getText(), "text");
  }

  @Test
  void valueIsNull() {
    // Given
    DateTableCellFactory<Object> factory = new DateTableCellFactory<>();
    DateTableCell cell = (DateTableCell) factory.call(null);

    // When
    cell.updateItem(null, false);

    // Then
    assertNull(cell.getText(), "text");
  }

  @Test
  void empty() {
    // Given
    DateTableCellFactory<Object> factory = new DateTableCellFactory<>();
    DateTableCell cell = (DateTableCell) factory.call(null);

    // When
    LocalDate date = LocalDate.of(2019, 11, 10);
    cell.updateItem(date, true);

    // Then
    assertNull(cell.getText(), "text");
  }
}
