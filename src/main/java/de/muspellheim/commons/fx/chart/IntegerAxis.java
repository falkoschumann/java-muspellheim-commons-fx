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

public class IntegerAxis extends Axis<Integer> {

    private final StringConverter<Integer> defaultFormatter = new IntegerStringConverter();

    private IntegerProperty lowerBound = new SimpleIntegerProperty(this, "lowerBound", 0) {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private IntegerProperty upperBound = new SimpleIntegerProperty(this, "upperBound", 100) {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private BooleanProperty forceZeroInRange = new SimpleBooleanProperty(this, "forceZeroInRange", true) {
        @Override
        protected void invalidated() {
            if (isAutoRanging()) {
                requestAxisLayout();
                invalidateRange();
            }
        }
    };

    private IntegerProperty tickUnit = new SimpleIntegerProperty(this, "tickUnit", 5) {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private ReadOnlyDoubleWrapper scale = new ReadOnlyDoubleWrapper(this, "scale", 0) {
        @Override
        protected void invalidated() {
            requestAxisLayout();
        }
    };

    private final DoubleProperty currentLowerBound = new SimpleDoubleProperty(this, "currentLowerBound");

    private double offset;
    private int dataMinValue;
    private int dataMaxValue;

    public final int getLowerBound() {
        return lowerBound.get();
    }

    public final void setLowerBound(int value) {
        lowerBound.set(value);
    }

    public final IntegerProperty lowerBoundProperty() {
        return lowerBound;
    }

    public final int getUpperBound() {
        return upperBound.get();
    }

    public final void setUpperBound(int value) {
        upperBound.set(value);
    }

    public final IntegerProperty upperBoundProperty() {
        return upperBound;
    }

    public final boolean isForceZeroInRange() {
        return forceZeroInRange.getValue();
    }

    public final void setForceZeroInRange(boolean value) {
        forceZeroInRange.setValue(value);
    }

    public final BooleanProperty forceZeroInRangeProperty() {
        return forceZeroInRange;
    }

    public final int getTickUnit() {
        return tickUnit.get();
    }

    public final void setTickUnit(int value) {
        tickUnit.set(value);
    }

    public final IntegerProperty tickUnitProperty() {
        return tickUnit;
    }

    public final double getScale() {
        return scale.get();
    }

    protected final void setScale(double scale) {
        this.scale.set(scale);
    }

    public final ReadOnlyDoubleProperty scaleProperty() {
        return scale.getReadOnlyProperty();
    }

    @Override
    protected Object autoRange(double length) {
        if (isAutoRanging()) {
            double labelSize = getTickLabelFont().getSize() * 4;
            return autoRange(dataMinValue, dataMaxValue, length, labelSize);
        } else {
            return getRange();
        }
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
        return new Range(getLowerBound(), getUpperBound(), getTickUnit(), getScale());
    }

    @Override
    public double getZeroPosition() {
        if (0 < getLowerBound() || 0 > getUpperBound()) {
            return Double.NaN;
        }

        return getDisplayPosition(0);
    }

    @Override
    public void invalidateRange(List<Integer> data) {
        if (data.isEmpty()) {
            dataMaxValue = getUpperBound();
            dataMinValue = getLowerBound();
        } else {
            dataMinValue = Integer.MAX_VALUE;
            dataMaxValue = Integer.MIN_VALUE;
        }
        for (Integer dataValue : data) {
            dataMinValue = Math.min(dataMinValue, dataValue);
            dataMaxValue = Math.max(dataMaxValue, dataValue);
        }
        super.invalidateRange(data);
    }

    @Override
    public double getDisplayPosition(Integer value) {
        return offset + ((value.doubleValue() - currentLowerBound.get()) * getScale());
    }

    @Override
    public Integer getValueForDisplay(double displayPosition) {
        return toRealValue(((displayPosition - offset) / getScale()) + currentLowerBound.get());
    }

    @Override
    public boolean isValueOnAxis(Integer value) {
        final double num = value.doubleValue();
        return num >= getLowerBound() && num <= getUpperBound();
    }

    @Override
    public double toNumericValue(Integer value) {
        return (value == null) ? Double.NaN : value;
    }

    @Override
    public Integer toRealValue(double value) {
        return (int) value;
    }

    @Override
    protected List<Integer> calculateTickValues(double length, Object range) {
        Range r = (Range) range;
        List<Integer> tickValues = new ArrayList<>();
        for (int tickValue = r.getMin(); tickValue < r.getMax(); tickValue += r.getTickUnit()) {
            tickValues.add(tickValue);
        }
        tickValues.add(r.getMax());
        return tickValues;
    }

    @Override
    protected String getTickMarkLabel(Integer value) {
        return defaultFormatter.toString(value);
    }

    private Range autoRange(int minValue, int maxValue, double length, double labelSize) {
        // TODO tick unit: 1, 5, 10, 50, 100, 500, 1000, ...
        int numOfTickMarks = (int) Math.floor(length / labelSize);
        if (isForceZeroInRange()) {
            if (minValue > 0) {
                minValue = 0;
            }
            if (maxValue < 0) {
                maxValue = 0;
            }
        }
        if (minValue != 0) {
            minValue--;
        }
        if (maxValue != 0) {
            maxValue++;
        }
        int range = maxValue - minValue;
        int tickUnit = Math.max(1, range / numOfTickMarks);
        double scale = calculateNewScale(length, minValue, maxValue);
        return new Range(minValue, maxValue, tickUnit, scale);
    }

    private double calculateNewScale(double length, int lowerBound, int upperBound) {
        double range = upperBound - lowerBound;
        if (getSide().isVertical()) {
            offset = length;
            return (range == 0) ? -length : -(length / range);
        } else {
            offset = 0;
            return (range == 0) ? length : length / range;
        }
    }

    @Value
    private static class Range {

        int min;
        int max;
        int tickUnit;
        double scale;

    }

}
