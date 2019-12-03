/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
        () -> assertEquals(5, fixture.getMinorTickCount(), "minorTickCount"));
  }

  @Test
  void autoRanged() {
    // When
    Object range = fixture.autoRange(23, 78, 250, 22);
    fixture.setRange(range, false);
    List<Long> tickValues = fixture.calculateTickValues(250, range);

    // Then
    assertEquals(
        Arrays.asList(0L, 10L, 20L, 30L, 40L, 50L, 60L, 70L, 80L), tickValues, "tickValues");
  }

  @Test
  void doNotForceZeroInRange() {
    // When
    fixture.setForceZeroInRange(false);
    Object range = fixture.autoRange(23, 78, 250, 22);
    fixture.setRange(range, false);
    List<Long> tickValues = fixture.calculateTickValues(250, range);

    // Then
    assertEquals(Arrays.asList(20L, 30L, 40L, 50L, 60L, 70L, 80L), tickValues, "tickValues");
  }

  @Test
  void numberOfTicksMustBeReduced() {
    // When
    fixture.setForceZeroInRange(false);
    Object range = fixture.autoRange(12, 109, 2048, 22);
    fixture.setRange(range, false);
    List<Long> tickValues = fixture.calculateTickValues(250, range);

    // Then
    assertEquals(
        Arrays.asList(10L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L, 100L, 110L),
        tickValues,
        "tickValues");
  }
}
