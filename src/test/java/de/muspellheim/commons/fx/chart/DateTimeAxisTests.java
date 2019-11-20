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
    void tickValues() {
        // When
        Object range = fixture.autoRange(LocalDateTime.of(2019, 11, 16, 21, 34), LocalDateTime.of(2019, 11, 17, 10, 1), 750, 165);
        List<LocalDateTime> tickValues = fixture.calculateTickValues(750, range);

        // Then
        List<LocalDateTime> ticks = Arrays.asList(
            LocalDateTime.of(2019, 11, 16, 21, 34),
            LocalDateTime.of(2019, 11, 17, 0, 40, 45),
            LocalDateTime.of(2019, 11, 17, 3, 47, 30),
            LocalDateTime.of(2019, 11, 17, 6, 54, 15),
            LocalDateTime.of(2019, 11, 17, 10, 1)
        );
        assertEquals(ticks, tickValues, "tickValues");
    }

}
