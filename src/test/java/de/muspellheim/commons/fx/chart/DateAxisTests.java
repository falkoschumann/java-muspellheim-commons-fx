/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.time.*;
import java.util.*;

import de.muspellheim.commons.fx.test.*;
import javafx.geometry.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class DateAxisTests {

    private DateAxis fixture;

    @BeforeEach
    void setUp() {
        Instant fixed = LocalDateTime.of(2019, 11, 20, 12, 43).toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fixed, ZoneId.systemDefault());
        fixture = new DateAxis(clock);
        fixture.setSide(Side.BOTTOM);
    }

    @Test
    void created() {
        // Then
        assertAll(
            () -> assertEquals(LocalDate.of(2019, 11, 13), fixture.getLowerBound(), "lowerBound"),
            () -> assertEquals(LocalDate.of(2019, 11, 20), fixture.getUpperBound(), "upperBound"),
            () -> assertEquals(Period.ofDays(1), fixture.getTickUnit(), "tickUnit"),
            () -> assertTrue(fixture.isAutoRanging(), "autoRanging")
        );
    }

    @Test
    void autoRangedTickValues() {
        // When
        fixture.invalidateRange(Arrays.asList(LocalDate.of(2019, 10, 20), LocalDate.of(2019, 11, 17)));
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);
        List<LocalDate> tickValues = fixture.calculateTickValues(750, range);

        // Then
        assertEquals(Arrays.asList(
            LocalDate.of(2019, 10, 20),
            LocalDate.of(2019, 10, 23),
            LocalDate.of(2019, 10, 26),
            LocalDate.of(2019, 10, 29),
            LocalDate.of(2019, 11, 1),
            LocalDate.of(2019, 11, 4),
            LocalDate.of(2019, 11, 7),
            LocalDate.of(2019, 11, 10),
            LocalDate.of(2019, 11, 13),
            LocalDate.of(2019, 11, 16),
            LocalDate.of(2019, 11, 17)), tickValues, "tickValues");
    }

    @Test
    void manualTickValues() {
        // When
        fixture.setAutoRanging(false);
        fixture.setLowerBound(LocalDate.of(2019, 10, 20));
        fixture.setUpperBound(LocalDate.of(2019, 11, 17));
        fixture.setTickUnit(Period.ofDays(5));
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);
        List<LocalDate> tickValues = fixture.calculateTickValues(750, range);

        // Then
        assertEquals(Arrays.asList(
            LocalDate.of(2019, 10, 20),
            LocalDate.of(2019, 10, 25),
            LocalDate.of(2019, 10, 30),
            LocalDate.of(2019, 11, 4),
            LocalDate.of(2019, 11, 9),
            LocalDate.of(2019, 11, 14),
            LocalDate.of(2019, 11, 17)), tickValues, "tickValues");
    }

    @Test
    void displayValue() {
        // Given
        fixture.setAutoRanging(false);
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);

        // When
        double displayPosition = fixture.getDisplayPosition(LocalDate.of(2019, 11, 15));
        LocalDate valueForDisplay = fixture.getValueForDisplay(500);

        // Then
        assertAll(
            () -> assertEquals(214.285, displayPosition, 0.001, "displayPosition"),
            () -> assertEquals(LocalDate.of(2019, 11, 17), valueForDisplay, "valueForDisplay")
        );
    }

    @Test
    void axisBounds() {
        // When
        boolean before = fixture.isValueOnAxis(LocalDate.of(2019, 11, 12));
        boolean lowerBound = fixture.isValueOnAxis(LocalDate.of(2019, 11, 13));
        boolean contains = fixture.isValueOnAxis(LocalDate.of(2019, 11, 15));
        boolean upperBound = fixture.isValueOnAxis(LocalDate.of(2019, 11, 20));
        boolean after = fixture.isValueOnAxis(LocalDate.of(2019, 11, 21));

        // Then
        assertAll(
            () -> assertFalse(before, "before value is not on axis"),
            () -> assertTrue(lowerBound, "lowerBound value is on axis"),
            () -> assertTrue(contains, "contains value is on axis"),
            () -> assertTrue(upperBound, "upperBound value is on axis"),
            () -> assertFalse(after, "after value is not on axis")
        );
    }

}
