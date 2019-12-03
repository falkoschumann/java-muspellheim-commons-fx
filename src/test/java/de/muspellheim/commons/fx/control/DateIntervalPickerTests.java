/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import de.muspellheim.commons.time.LocalDateInterval;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class DateIntervalPickerTests {

  private DateIntervalPicker fixture;
  private DatePicker startPicker;
  private DatePicker endPicker;

  private Clock clock;

  @BeforeEach
  void setUp() {
    fixture = new DateIntervalPicker();
    fixture.createDefaultSkin();
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
        () -> assertNull(endPicker.getValue(), "end"));
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
        () -> assertEquals(end, endPicker.getValue(), "end"));
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
        () -> assertNull(endPicker.getValue(), "end"));
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
        () -> assertEquals(end, endPicker.getValue(), "end"));
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
        () -> assertEquals(end, endPicker.getValue(), "end"));
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
        () -> assertEquals(end, endPicker.getValue(), "end"));
  }
}
