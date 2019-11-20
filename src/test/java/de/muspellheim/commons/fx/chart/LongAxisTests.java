/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.util.*;

import de.muspellheim.commons.fx.test.*;
import javafx.geometry.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class LongAxisTests {

    private LongAxis fixture;

    @BeforeEach
    void setUp() {
        fixture = new LongAxis();
        fixture.setSide(Side.LEFT);
    }

    @Test
    void created() {
        // Then
        assertAll(
            () -> assertTrue(fixture.isForceZeroInRange(), "forceZeroInRange"),
            () -> assertEquals(5, fixture.getTickUnit(), "tickUnit"),
            () -> assertEquals(0, fixture.getLowerBound(), "lowerBound"),
            () -> assertEquals(100, fixture.getUpperBound(), "upperBound"),
            () -> assertTrue(fixture.isAutoRanging(), "autoRanging"),
            () -> assertEquals(5, fixture.getMinorTickCount(), "minorTickCount")
        );
    }

    @Test
    void autoRangedTickValues() {
        // When
        Object range = fixture.autoRange(23, 78, 250, 22);
        fixture.setRange(range, false);
        List<Long> tickValues = fixture.calculateTickValues(250, range);
        List<Long> minorTickMarks = fixture.calculateMinorTickMarks();

        // Then
        // TODO Normalized tick values
        //assertEquals(Arrays.asList(0L, 20, 40, 60, 80), tickValues, "tickValues");
        assertEquals(Arrays.asList(0L, 15L, 30L, 45L, 60L, 75L, 78L), tickValues, "tick values");
        assertEquals(Arrays.asList(
            3L, 6L, 9L, 12L,
            18L, 21L, 24L, 27L,
            33L, 36L, 39L, 42L,
            48L, 51L, 54L, 57L,
            63L, 66L, 69L, 72L), minorTickMarks, "minor tick marks");
    }

}
