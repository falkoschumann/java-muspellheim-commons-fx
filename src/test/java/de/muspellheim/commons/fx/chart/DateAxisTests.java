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
    void tickValues() {
        // When
        Object range = fixture.autoRange(LocalDate.of(2019, 10, 20), LocalDate.of(2019, 11, 17), 750, 110);
        List<LocalDate> tickValues = fixture.calculateTickValues(750, range);

        // Then
        List<LocalDate> ticks = Arrays.asList(
            LocalDate.of(2019, 10, 20),
            LocalDate.of(2019, 10, 24),
            LocalDate.of(2019, 10, 28),
            LocalDate.of(2019, 11, 1),
            LocalDate.of(2019, 11, 5),
            LocalDate.of(2019, 11, 9),
            LocalDate.of(2019, 11, 13),
            LocalDate.of(2019, 11, 17)
        );
        assertEquals(ticks, tickValues, "tickValues");
    }

}
