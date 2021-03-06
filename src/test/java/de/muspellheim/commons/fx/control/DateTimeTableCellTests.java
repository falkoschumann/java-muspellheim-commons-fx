/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.time.LocalDateTime;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class DateTimeTableCellTests {

  @BeforeEach
  void setUp() {
    Locale.setDefault(Locale.GERMAN);
  }

  @Test
  void value() {
    // Given
    DateTimeTableCellFactory<Object> factory = new DateTimeTableCellFactory<>();
    DateTimeTableCell cell = (DateTimeTableCell) factory.call(null);

    // When
    LocalDateTime date = LocalDateTime.of(2019, 11, 10, 0, 20);
    cell.updateItem(date, false);

    // Then
    // OpenJDK format with ",", Oracke JDK without
    if (System.getProperty("java.version").startsWith("1.8")) {
      assertEquals("10.11.2019 00:20:00", cell.getText(), "text");
    } else {
      assertEquals("10.11.2019, 00:20:00", cell.getText(), "text");
    }
  }

  @Test
  void valueIsNull() {
    // Given
    DateTimeTableCellFactory<Object> factory = new DateTimeTableCellFactory<>();
    DateTimeTableCell cell = (DateTimeTableCell) factory.call(null);

    // When
    cell.updateItem(null, false);

    // Then
    assertNull(cell.getText(), "text");
  }

  @Test
  void empty() {
    // Given
    DateTimeTableCellFactory<Object> factory = new DateTimeTableCellFactory<>();
    DateTimeTableCell cell = (DateTimeTableCell) factory.call(null);

    // When
    LocalDateTime date = LocalDateTime.of(2019, 11, 10, 0, 20);
    cell.updateItem(date, true);

    // Then
    assertNull(cell.getText(), "text");
  }
}
