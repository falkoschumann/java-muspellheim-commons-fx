/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.util.*;

import javafx.beans.property.*;
import javafx.scene.chart.*;
import javafx.util.*;
import javafx.util.converter.*;
import lombok.*;

public class LongAxis extends ValueAxis<Long> {

    private final StringConverter<Long> defaultFormatter = new LongStringConverter();

    private BooleanProperty forceZeroInRange = new SimpleBooleanProperty(this, "forceZeroInRange", true) {
        @Override
        protected void invalidated() {
            if (isAutoRanging()) {
                requestAxisLayout();
                invalidateRange();
            }
        }
    };

    private LongProperty tickUnit = new SimpleLongProperty(this, "tickUnit", 5) {
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
        // TODO tick unit: 1, 5, 10, 50, 100, 500, 1000, ...
        long min = (long) minValue;
        long max = (long) maxValue;
        long maxDigists = Math.max(String.valueOf(min).length(), String.valueOf(max).length());
        labelSize = labelSize * (maxDigists - 1);
        int numOfTickMarks = (int) Math.floor(length / labelSize);
        if (isForceZeroInRange()) {
            if (min > 0) {
                min = 0;
            }
            if (max < 0) {
                max = 0;
            }
        }
        if (min != 0) {
            min--;
        }
        if (max != 0) {
            max++;
        }
        long range = max - min;
        long tickUnit = Math.max(1, range / numOfTickMarks);
        double scale = calculateNewScale(length, min, max);
        return new Range(min, max, tickUnit, scale);
    }

    @Override
    protected List<Long> calculateMinorTickMarks() {
        // TODO implement method
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
        tickValues.add(r.getMax());
        return tickValues;
    }

    @Override
    protected String getTickMarkLabel(Long value) {
        return defaultFormatter.toString(value);
    }

    @Value
    private static class Range {

        long min;
        long max;
        long tickUnit;
        double scale;

    }

}
