/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
        () -> assertTrue(fixture.isAutoRanging(), "autoRanging"));
  }

  @Test
  void autoRangedTickValues() {
    // When
    fixture.invalidateRange(Arrays.asList(LocalDate.of(2019, 10, 20), LocalDate.of(2019, 11, 17)));
    Object range = fixture.autoRange(750);
    fixture.setRange(range, false);
    List<LocalDate> tickValues = fixture.calculateTickValues(750, range);

    // Then
    assertEquals(
        Arrays.asList(
            LocalDate.of(2019, 10, 19),
            LocalDate.of(2019, 10, 22),
            LocalDate.of(2019, 10, 25),
            LocalDate.of(2019, 10, 28),
            LocalDate.of(2019, 10, 31),
            LocalDate.of(2019, 11, 3),
            LocalDate.of(2019, 11, 6),
            LocalDate.of(2019, 11, 9),
            LocalDate.of(2019, 11, 12),
            LocalDate.of(2019, 11, 15),
            LocalDate.of(2019, 11, 18)),
        tickValues,
        "tickValues");
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
    assertEquals(
        Arrays.asList(
            LocalDate.of(2019, 10, 20),
            LocalDate.of(2019, 10, 25),
            LocalDate.of(2019, 10, 30),
            LocalDate.of(2019, 11, 4),
            LocalDate.of(2019, 11, 9),
            LocalDate.of(2019, 11, 14),
            LocalDate.of(2019, 11, 17)),
        tickValues,
        "tickValues");
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
        () -> assertEquals(LocalDate.of(2019, 11, 17), valueForDisplay, "valueForDisplay"));
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
        () -> assertFalse(after, "after value is not on axis"));
  }
}
