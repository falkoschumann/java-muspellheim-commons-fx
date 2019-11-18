/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.time.Duration;
import java.time.*;
import java.util.*;

import javafx.beans.property.*;
import javafx.scene.chart.*;
import javafx.util.*;
import javafx.util.converter.*;
import lombok.*;

public class DateTimeAxis extends Axis<LocalDateTime> {

    private final StringConverter<LocalDateTime> defaultFormatter = new LocalDateTimeStringConverter();

    private ObjectProperty<LocalDateTime> lowerBound = new SimpleObjectProperty<LocalDateTime>(this, "lowerBound") {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private ObjectProperty<LocalDateTime> upperBound = new SimpleObjectProperty<LocalDateTime>(this, "upperBound") {
        @Override
        protected void invalidated() {
            if (!isAutoRanging()) {
                invalidateRange();
                requestAxisLayout();
            }
        }
    };

    private ObjectProperty<Duration> tickUnit = new SimpleObjectProperty<Duration>(this, "tickUnit", Duration.ofHours(1)) {
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

    private final ObjectProperty<LocalDateTime> currentLowerBound = new SimpleObjectProperty<>(this, "currentLowerBound");

    private double offset;
    private LocalDateTime dataMinValue;
    private LocalDateTime dataMaxValue;

    public final LocalDateTime getLowerBound() {
        return lowerBound.get();
    }

    public final void setLowerBound(LocalDateTime value) {
        lowerBound.set(value);
    }

    public final ObjectProperty<LocalDateTime> lowerBoundProperty() {
        return lowerBound;
    }

    public final LocalDateTime getUpperBound() {
        return upperBound.get();
    }

    public final void setUpperBound(LocalDateTime value) {
        upperBound.set(value);
    }

    public final ObjectProperty<LocalDateTime> upperBoundProperty() {
        return upperBound;
    }

    public final Duration getTickUnit() {
        return tickUnit.get();
    }

    public final void setTickUnit(Duration value) {
        tickUnit.set(value);
    }

    public final ObjectProperty<Duration> tickUnitProperty() {
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
            double min = toNumericValue(dataMinValue);
            double max = toNumericValue(dataMaxValue);
            int numOfTickMarks = (int) Math.floor(length / labelSize);
            double range = max - min;
            // TODO tick unit: minutes, hours, days, months, years, ...
            long newTickUnit = (long) Math.max(1, range / numOfTickMarks);
            double newScale = calculateNewScale(length, min, max);
            return new Range(toRealValue(min), toRealValue(max), Duration.ofMillis(newTickUnit), newScale);
        } else {
            currentLowerBound.set(getLowerBound());
            setScale(calculateNewScale(length, toNumericValue(getLowerBound()), toNumericValue(getUpperBound())));
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
    public void invalidateRange(List<LocalDateTime> data) {
        if (data.isEmpty()) {
            dataMaxValue = getUpperBound();
            dataMinValue = getLowerBound();
        } else {
            dataMinValue = LocalDateTime.of(1_000_000, 12, 31, 23, 59);
            dataMaxValue = LocalDateTime.of(-1_000_000, 1, 1, 0, 0);
        }
        for (LocalDateTime dataValue : data) {
            dataMinValue = toRealValue(Math.min(toNumericValue(dataMinValue), toNumericValue(dataValue)));
            dataMaxValue = toRealValue(Math.max(toNumericValue(dataMaxValue), toNumericValue(dataValue)));
        }
        super.invalidateRange(data);
    }

    @Override
    public double getDisplayPosition(LocalDateTime value) {
        return offset + ((toNumericValue(value) - toNumericValue(currentLowerBound.get())) * getScale());
    }

    @Override
    public LocalDateTime getValueForDisplay(double displayPosition) {
        return toRealValue(((displayPosition - offset) / getScale()) + toNumericValue(currentLowerBound.get()));
    }

    @Override
    public boolean isValueOnAxis(LocalDateTime value) {
        return (value.equals(getLowerBound()) || value.isAfter(getLowerBound()))
            && (value.equals(getUpperBound()) || value.isBefore(getUpperBound()));
    }

    @Override
    public double toNumericValue(LocalDateTime value) {
        return (value == null) ? Double.NaN : value.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    @Override
    public LocalDateTime toRealValue(double value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli((long) value), ZoneOffset.UTC);
    }

    @Override
    protected List<LocalDateTime> calculateTickValues(double length, Object range) {
        Range r = (Range) range;
        List<LocalDateTime> tickValues = new ArrayList<>();
        for (LocalDateTime tickValue = r.getMin(); tickValue.isBefore(r.getMax()); tickValue = tickValue.plus(r.getTickUnit())) {
            tickValues.add(tickValue);
        }
        tickValues.add(r.getMax());
        return tickValues;
    }

    @Override
    protected String getTickMarkLabel(LocalDateTime value) {
        StringConverter<LocalDateTime> formatter = getTickLabelFormatter();
        if (formatter == null) {
            formatter = defaultFormatter;
        }
        return formatter.toString(value);
    }

    private double calculateNewScale(double length, double minValue, double maxValue) {
        double range = maxValue - minValue;
        if (getSide().isVertical()) {
            offset = length;
            return (range == 0) ? -length : -(length / range);
        } else {
            offset = 0;
            return (range == 0) ? length : length / range;
        }
    }

    @Value
    @SuppressWarnings("checkstyle:VisibilityModifier")
    private static class Range {

        LocalDateTime min;
        LocalDateTime max;
        Duration tickUnit;
        double scale;

    }

}
