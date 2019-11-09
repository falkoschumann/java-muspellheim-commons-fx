/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.util.*;

import de.muspellheim.commons.fx.test.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

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
