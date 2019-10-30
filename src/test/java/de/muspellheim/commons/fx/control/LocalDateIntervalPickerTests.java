/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;

import de.muspellheim.commons.fx.test.*;
import de.muspellheim.commons.time.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class LocalDateIntervalPickerTests {

    // Default constructor
    // Non default constructor
    // set value non null
    // set value null
    // change start
    // change start wrong
    // change end
    // change end wrong

    @Test
    void created() {
        // When
        LocalDateIntervalPicker picker = new LocalDateIntervalPicker();

        // Then
        assertNull(picker.getValue(), "value");
        assertNull(picker.getStart().getValue(), "start");
        assertNull(picker.getEnd().getValue(), "end");
    }

    @Test
    void valueSet() {
        // Given
        LocalDateIntervalPicker picker = new LocalDateIntervalPicker();
        Instant fixed = LocalDateTime.of(2019, 10, 25, 18, 2).toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fixed, ZoneId.systemDefault());

        // When
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        picker.setValue(interval);

        // Then
        assertEquals(interval, picker.getValue(), "value");
        assertEquals(start, picker.getStart().getValue(), "start");
        assertEquals(end, picker.getEnd().getValue(), "end");
    }

    @Test
    void startAndEndSet() {
        // Given
        LocalDateIntervalPicker picker = new LocalDateIntervalPicker();
        Instant fixed = LocalDateTime.of(2019, 10, 25, 18, 2).toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fixed, ZoneId.systemDefault());

        // When
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        picker.getStart().setValue(start);
        picker.getEnd().setValue(end);

        // Then
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        assertEquals(interval, picker.getValue(), "value");
        assertEquals(start, picker.getStart().getValue(), "start");
        assertEquals(end, picker.getEnd().getValue(), "end");
    }

}
