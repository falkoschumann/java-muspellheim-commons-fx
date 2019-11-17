/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.time.*;
import java.util.*;

import javafx.beans.property.*;
import javafx.scene.chart.*;
import javafx.util.*;
import javafx.util.converter.*;
import lombok.*;

public class DateAxis extends Axis<LocalDate> {

    private final StringConverter<LocalDate> defaultFormatter = new LocalDateStringConverter();

    private ObjectProperty<LocalDate> lowerBound = new SimpleObjectProperty<LocalDate>(this, "lowerBound") {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private ObjectProperty<LocalDate> upperBound = new SimpleObjectProperty<LocalDate>(this, "upperBound") {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private ObjectProperty<Period> tickUnit = new SimpleObjectProperty<Period>(this, "tickUnit", Period.ofDays(1)) {
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

    private final ObjectProperty<LocalDate> currentLowerBound = new SimpleObjectProperty<>(this, "currentLowerBound");

    private double offset;
    private LocalDate dataMinValue;
    private LocalDate dataMaxValue;

    public final LocalDate getLowerBound() {
        return lowerBound.get();
    }

    public final void setLowerBound(LocalDate value) {
        lowerBound.set(value);
    }

    public final ObjectProperty<LocalDate> lowerBoundProperty() {
        return lowerBound;
    }

    public final LocalDate getUpperBound() {
        return upperBound.get();
    }

    public final void setUpperBound(LocalDate value) {
        upperBound.set(value);
    }

    public final ObjectProperty<LocalDate> upperBoundProperty() {
        return upperBound;
    }

    public final Period getTickUnit() {
        return tickUnit.get();
    }

    public final void setTickUnit(Period value) {
        tickUnit.set(value);
    }

    public final ObjectProperty<Period> tickUnitProperty() {
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
            double labelSize = getTickLabelFont().getSize() * 10;
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
        return Double.NaN;
    }

    @Override
    public void invalidateRange(List<LocalDate> data) {
        if (data.isEmpty()) {
            dataMaxValue = getUpperBound();
            dataMinValue = getLowerBound();
        } else {
            dataMinValue = LocalDate.MAX;
            dataMaxValue = LocalDate.MIN;
        }
        for (LocalDate dataValue : data) {
            dataMinValue = LocalDate.ofEpochDay(Math.min(dataMinValue.toEpochDay(), dataValue.toEpochDay()));
            dataMaxValue = LocalDate.ofEpochDay(Math.max(dataMaxValue.toEpochDay(), dataValue.toEpochDay()));
        }
        super.invalidateRange(data);
    }

    @Override
    public double getDisplayPosition(LocalDate value) {
        return offset + ((value.toEpochDay() - currentLowerBound.get().toEpochDay()) * getScale());
    }

    @Override
    public LocalDate getValueForDisplay(double displayPosition) {
        return toRealValue(((displayPosition - offset) / getScale()) + currentLowerBound.get().toEpochDay());
    }

    @Override
    public boolean isValueOnAxis(LocalDate value) {
        return (value.equals(getLowerBound()) || value.isAfter(getLowerBound()))
            && (value.equals(getUpperBound()) || value.isBefore(getUpperBound()));
    }

    @Override
    public double toNumericValue(LocalDate value) {
        return (value == null) ? Double.NaN : value.toEpochDay();
    }

    @Override
    public LocalDate toRealValue(double value) {
        return LocalDate.ofEpochDay((long) value);
    }

    @Override
    protected List<LocalDate> calculateTickValues(double length, Object range) {
        Range r = (Range) range;
        List<LocalDate> tickValues = new ArrayList<>();
        for (LocalDate tickValue = r.getMin(); tickValue.isBefore(r.getMax()); tickValue = tickValue.plus(r.getTickUnit())) {
            tickValues.add(tickValue);
        }
        tickValues.add(r.getMax());
        return tickValues;
    }

    @Override
    protected String getTickMarkLabel(LocalDate value) {
        return defaultFormatter.toString(value);
    }

    private Range autoRange(LocalDate minValue, LocalDate maxValue, double length, double labelSize) {
        long min = minValue.toEpochDay() - 1;
        long max = maxValue.toEpochDay() + 1;
        int numOfTickMarks = (int) Math.floor(length / labelSize);
        long range = max - min;
        int tickUnit = (int) Math.max(1, range / numOfTickMarks);
        double scale = calculateNewScale(length, min, max);
        return new Range(LocalDate.ofEpochDay(min), LocalDate.ofEpochDay(max), Period.ofDays(tickUnit), scale);
    }

    private double calculateNewScale(double length, long lowerBound, long upperBound) {
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

        LocalDate min;
        LocalDate max;
        Period tickUnit;
        double scale;

    }

}
