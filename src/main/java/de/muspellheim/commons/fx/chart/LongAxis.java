/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.chart.ValueAxis;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;
import lombok.Value;

public class LongAxis extends ValueAxis<Long> {

  private final StringConverter<Long> defaultFormatter = new LongStringConverter();

  private BooleanProperty forceZeroInRange =
      new SimpleBooleanProperty(this, "forceZeroInRange", true) {
        @Override
        protected void invalidated() {
          if (isAutoRanging()) {
            requestAxisLayout();
            invalidateRange();
          }
        }
      };

  private LongProperty tickUnit =
      new SimpleLongProperty(this, "tickUnit", 5) {
        @Override
        protected void invalidated() {
          if (!isAutoRanging()) {
            invalidateRange();
            requestAxisLayout();
          }
        }
      };

  public final boolean isForceZeroInRange() {
    return forceZeroInRange.getValue();
  }

  public final void setForceZeroInRange(boolean value) {
    forceZeroInRange.setValue(value);
  }

  public final BooleanProperty forceZeroInRangeProperty() {
    return forceZeroInRange;
  }

  public final long getTickUnit() {
    return tickUnit.get();
  }

  public final void setTickUnit(long value) {
    tickUnit.set(value);
  }

  public final LongProperty tickUnitProperty() {
    return tickUnit;
  }

  @Override
  protected Object autoRange(double minValue, double maxValue, double length, double labelSize) {
    long min = (long) Math.floor(minValue * 0.98);
    long max = (long) Math.ceil(maxValue * 1.02);
    if (isForceZeroInRange()) {
      if (min > 0) {
        min = 0;
      }
      if (max < 0) {
        max = 0;
      }
    }

    long range = max - min;
    int numOfTickMarks = (int) Math.max(2, Math.floor(length / labelSize));
    long newTickUnit = Math.max(1, range / numOfTickMarks);

    // TODO normalize tick unit: 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, ...
    int exp = (int) Math.ceil(Math.log10(newTickUnit));
    do {
      newTickUnit = (long) Math.pow(10, exp);
      exp++;
      numOfTickMarks = (int) (range / newTickUnit);
    } while (numOfTickMarks > 20);
    min = min / newTickUnit * newTickUnit;
    max = max / newTickUnit * newTickUnit;
    if (max < maxValue) {
      max += newTickUnit;
    }

    double scale = calculateNewScale(length, min, max);
    return new Range(min, max, newTickUnit, scale);
  }

  @Override
  protected List<Long> calculateMinorTickMarks() {
    return Collections.emptyList();
  }

  @Override
  protected void setRange(Object range, boolean animate) {
    Range r = (Range) range;
    setLowerBound(r.getMin());
    setUpperBound(r.getMax());
    setTickUnit(r.getTickUnit());
    setScale(r.getScale());
    currentLowerBound.set(r.getMin());
  }

  @Override
  protected Object getRange() {
    return new Range((long) getLowerBound(), (long) getUpperBound(), getTickUnit(), getScale());
  }

  @Override
  protected List<Long> calculateTickValues(double length, Object range) {
    Range r = (Range) range;
    List<Long> tickValues = new ArrayList<>();
    for (long tickValue = r.getMin(); tickValue < r.getMax(); tickValue += r.getTickUnit()) {
      tickValues.add(tickValue);
    }
    if (!tickValues.contains(r.getMax())) {
      tickValues.add(r.getMax());
    }
    return tickValues;
  }

  @Override
  protected String getTickMarkLabel(Long value) {
    return defaultFormatter.toString(value);
  }

  @Value
  @SuppressWarnings("checkstyle:VisibilityModifier")
  private static class Range {

    long min;
    long max;
    long tickUnit;
    double scale;
  }
}
