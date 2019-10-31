/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;

import de.muspellheim.commons.fx.test.*;
import de.muspellheim.commons.time.*;
import javafx.scene.control.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class LocalDateIntervalPickerTests {

    private LocalDateIntervalPicker fixture;
    private DatePicker startPicker;
    private DatePicker endPicker;

    private Clock clock;

    @BeforeEach
    void setUp() {
        fixture = new LocalDateIntervalPicker();
        startPicker = (DatePicker) fixture.lookup(".interval-start");
        endPicker = (DatePicker) fixture.lookup(".interval-end");

        LocalDateTime now = LocalDateTime.of(2019, 10, 25, 18, 2);
        Instant fixed = now.toInstant(ZoneOffset.UTC);
        clock = Clock.fixed(fixed, ZoneId.systemDefault());
    }

    @Test
    void created() {
        // Then
        assertAll(
            () -> assertNull(fixture.getValue(), "value"),
            () -> assertNull(startPicker.getValue(), "start"),
            () -> assertNull(endPicker.getValue(), "end")
        );
    }

    @Test
    void initialized() {
        // When
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(3);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        fixture = new LocalDateIntervalPicker(interval);
        startPicker = (DatePicker) fixture.lookup(".interval-start");
        endPicker = (DatePicker) fixture.lookup(".interval-end");

        // Then
        assertAll(
            () -> assertEquals(interval, fixture.getValue(), "value"),
            () -> assertEquals(start, startPicker.getValue(), "start"),
            () -> assertEquals(end, endPicker.getValue(), "end")
        );
    }

    @Test
    void valueSet() {
        // When
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        fixture.setValue(interval);

        // Then
        assertAll(
            () -> assertEquals(interval, fixture.getValue(), "value"),
            () -> assertEquals(start, startPicker.getValue(), "start"),
            () -> assertEquals(end, endPicker.getValue(), "end")
        );
    }

    @Test
    void valueUnset() {
        // Given
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        fixture.setValue(interval);

        // When
        fixture.setValue(null);

        // Then
        assertAll(
            () -> assertNull(fixture.getValue(), "value"),
            () -> assertNull(startPicker.getValue(), "start"),
            () -> assertNull(endPicker.getValue(), "end")
        );
    }

    @Test
    void startAndEndSet() {
        // When
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        startPicker.setValue(start);
        endPicker.setValue(end);

        // Then
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        assertAll(
            () -> assertEquals(interval, fixture.getValue(), "value"),
            () -> assertEquals(start, startPicker.getValue(), "start"),
            () -> assertEquals(end, endPicker.getValue(), "end")
        );
    }

    @Test
    void startAfterEnd_IntervalUnchanged() {
        // Given
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        fixture.setValue(interval);

        // When
        LocalDate date = end.plusDays(2);
        startPicker.setValue(date);

        // Then
        assertAll(
            () -> assertEquals(interval, fixture.getValue(), "value"),
            () -> assertEquals(start, startPicker.getValue(), "start"),
            () -> assertEquals(end, endPicker.getValue(), "end")
        );
    }

    @Test
    void endBeforeStart_IntervalUnchanged() {
        // Given
        LocalDate start = LocalDate.now(clock);
        LocalDate end = start.plusDays(7);
        LocalDateInterval interval = LocalDateInterval.of(start, end);
        fixture.setValue(interval);

        // When
        LocalDate date = start.minusDays(1);
        endPicker.setValue(date);

        // Then
        assertAll(
            () -> assertEquals(interval, fixture.getValue(), "value"),
            () -> assertEquals(start, startPicker.getValue(), "start"),
            () -> assertEquals(end, endPicker.getValue(), "end")
        );
    }

}
