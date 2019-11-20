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
class DateTimeAxisTests {

    private DateTimeAxis fixture;

    @BeforeEach
    void setUp() {
        Instant fixed = LocalDateTime.of(2019, 11, 20, 13, 18).toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fixed, ZoneId.systemDefault());
        fixture = new DateTimeAxis(clock);
        fixture.setSide(Side.BOTTOM);
    }

    @Test
    void created() {
        // Then
        assertAll(
            () -> assertEquals(LocalDateTime.of(2019, 11, 20, 0, 0), fixture.getLowerBound(), "lowerBound"),
            () -> assertEquals(LocalDateTime.of(2019, 11, 21, 0, 0), fixture.getUpperBound(), "upperBound"),
            () -> assertEquals(Duration.ofHours(1), fixture.getTickUnit(), "tickUnit"),
            () -> assertTrue(fixture.isAutoRanging(), "autoRanging")
        );
    }

    @Test
    void autoRangedTickValues() {
        // When
        fixture.invalidateRange(Arrays.asList(LocalDateTime.of(2019, 11, 16, 21, 34), LocalDateTime.of(2019, 11, 17, 10, 1)));
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);
        List<LocalDateTime> tickValues = fixture.calculateTickValues(750, range);

        // Then
        // TODO normalised number of ticks: 6
        /*
        assertEquals(Arrays.asList(
            LocalDateTime.of(2019, 11, 16, 21, 0),
            LocalDateTime.of(2019, 11, 17, 0, 0),
            LocalDateTime.of(2019, 11, 17, 3, 0),
            LocalDateTime.of(2019, 11, 17, 6, 0),
            LocalDateTime.of(2019, 11, 17, 9, 0),
            LocalDateTime.of(2019, 11, 17, 12, 0)), tickValues, "tickValues");
        */
        assertEquals(Arrays.asList(
            LocalDateTime.of(2019, 11, 16, 21, 34),
            LocalDateTime.of(2019, 11, 16, 23, 38, 30),
            LocalDateTime.of(2019, 11, 17, 1, 43),
            LocalDateTime.of(2019, 11, 17, 3, 47, 30),
            LocalDateTime.of(2019, 11, 17, 5, 52),
            LocalDateTime.of(2019, 11, 17, 7, 56, 30),
            LocalDateTime.of(2019, 11, 17, 10, 1)), tickValues, "tickValues");
    }

    @Test
    void manualTickValues() {
        // When
        fixture.setAutoRanging(false);
        fixture.setLowerBound(LocalDateTime.of(2019, 11, 16, 21, 34));
        fixture.setUpperBound(LocalDateTime.of(2019, 11, 17, 10, 1));
        fixture.setTickUnit(Duration.ofHours(5));
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);
        List<LocalDateTime> tickValues = fixture.calculateTickValues(750, range);

        // Then
        assertEquals(Arrays.asList(
            LocalDateTime.of(2019, 11, 16, 21, 34),
            LocalDateTime.of(2019, 11, 17, 2, 34),
            LocalDateTime.of(2019, 11, 17, 7, 34),
            LocalDateTime.of(2019, 11, 17, 10, 1)), tickValues, "tickValues");
    }

    @Test
    void displayValue() {
        // Given
        fixture.setAutoRanging(false);
        Object range = fixture.autoRange(750);
        fixture.setRange(range, false);

        // When
        double displayPosition = fixture.getDisplayPosition(LocalDateTime.of(2019, 11, 20, 14, 47));
        LocalDateTime valueForDisplay = fixture.getValueForDisplay(500);

        // Then
        assertAll(
            () -> assertEquals(461.979, displayPosition, 0.001, "displayPosition"),
            () -> assertEquals(LocalDateTime.of(2019, 11, 20, 16, 0), valueForDisplay, "valueForDisplay")
        );
    }

    @Test
    void axisBounds() {
        // When
        boolean before = fixture.isValueOnAxis(LocalDateTime.of(2019, 11, 19, 23, 59));
        boolean lowerBound = fixture.isValueOnAxis(LocalDateTime.of(2019, 11, 20, 0, 0));
        boolean contains = fixture.isValueOnAxis(LocalDateTime.of(2019, 11, 20, 12, 0));
        boolean upperBound = fixture.isValueOnAxis(LocalDateTime.of(2019, 11, 21, 0, 0));
        boolean after = fixture.isValueOnAxis(LocalDateTime.of(2019, 11, 21, 0, 1));

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
