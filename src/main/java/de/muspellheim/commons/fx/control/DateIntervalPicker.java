/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import de.muspellheim.commons.time.*;
import javafx.beans.property.*;
import javafx.scene.control.*;

/**
 * Picker for {@link LocalDateInterval}.
 */
public class DateIntervalPicker extends Control {

    private final ObjectProperty<LocalDateInterval> value = new SimpleObjectProperty<>(this, "value");

    public DateIntervalPicker() {
        getStyleClass().add("date-interval-picker");
    }

    /**
     * The current set interval.
     *
     * @return the interval
     */
    public ObjectProperty<LocalDateInterval> valueProperty() {
        return value;
    }

    /**
     * Get current interval.
     *
     * @return the interval
     */
    public LocalDateInterval getValue() {
        return value.get();
    }

    /**
     * Set current  interval.
     *
     * @param value an interval
     */
    public void setValue(LocalDateInterval value) {
        this.value.set(value);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DateIntervalPickerSkin(this);
    }

}
