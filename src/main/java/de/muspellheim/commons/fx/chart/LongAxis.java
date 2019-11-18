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
        long min = (long) minValue;
        long max = (long) maxValue;
        long maxDigists = Math.max(String.valueOf(min).length(), String.valueOf(max).length());
        labelSize = labelSize * maxDigists;
        if (isForceZeroInRange()) {
            if (min > 0) {
                min = 0;
            }
            if (max < 0) {
                max = 0;
            }
        }
        long range = max - min;
        int numOfTickMarks = (int) Math.floor(length / labelSize);
        // TODO normalize tick unit: 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, ...
        long newTickUnit = Math.max(1, range / numOfTickMarks);
        double scale = calculateNewScale(length, min, max);
        return new Range(min, max, newTickUnit, scale);
    }

    @Override
    protected List<Long> calculateMinorTickMarks() {
        List<Long> minorTickMarks = new ArrayList<>();
        long min = (long) getLowerBound();
        long max = (long) getUpperBound();
        long majorTickUnit = getTickUnit();
        long minorTickUnit = majorTickUnit / Math.max(1, getMinorTickCount());
        if (majorTickUnit > 0 && minorTickUnit > 0) {
            if (((max - min) / minorTickUnit) > 10000) {
                System.err.println("Warning we tried to create more than 10000 minor tick marks on a NumberAxis. "
                    + "Lower Bound=" + getLowerBound() + ", Upper Bound=" + getUpperBound() + ", Tick Unit=" + majorTickUnit);
                return minorTickMarks;
            }
            long major = min;
            long count = (max - major) / majorTickUnit;
            for (int i = 0; major < max && i < count; major += majorTickUnit, i++) {
                long next = Math.min(major + majorTickUnit, max);
                long minor = major + minorTickUnit;
                long minorCount = (next - minor) / minorTickUnit;
                for (int j = 0; minor < next && j < minorCount; minor += minorTickUnit, j++) {
                    minorTickMarks.add(minor);
                }
            }
        }
        return minorTickMarks;
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
    @SuppressWarnings("checkstyle:VisibilityModifier")
    private static class Range {

        long min;
        long max;
        long tickUnit;
        double scale;

    }

}
