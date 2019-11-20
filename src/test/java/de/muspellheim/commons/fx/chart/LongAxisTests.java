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

    @Test
    void created() {
        // When
        LongAxis axis = new LongAxis();
        axis.setSide(Side.BOTTOM);

        // Then
        assertAll(
            () -> assertTrue(axis.isForceZeroInRange(), "forceZeroInRange"),
            () -> assertEquals(5, axis.getTickUnit(), "tickUnit"),
            () -> assertEquals(0, axis.getLowerBound(), "lowerBound"),
            () -> assertEquals(100, axis.getUpperBound(), "upperBound"),
            () -> assertTrue(axis.isAutoRanging(), "autoRanging")
        );
    }

    @Test
    void tickValues() {
        // Given
        LongAxis axis = new LongAxis();
        axis.setSide(Side.BOTTOM);

        // When
        Object range = axis.autoRange(23, 78, 250, 22);
        List<Long> tickValues = axis.calculateTickValues(250, range);

        // Then
        // TODO Normalized tick values
        //assertEquals(Arrays.asList(0L, 20, 40, 60, 80), tickValues, "tickValues");
        assertEquals(Arrays.asList(0L, 15L, 30L, 45L, 60L, 75L, 78L), tickValues, "tickValues");
    }

}
