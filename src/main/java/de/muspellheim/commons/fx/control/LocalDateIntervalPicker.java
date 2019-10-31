/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.util.*;

import de.muspellheim.commons.time.*;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LocalDateIntervalPicker extends HBox {

    private final ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
    private final ObjectProperty<LocalDateInterval> value = new SimpleObjectProperty<>(this, "value");

    private final DatePicker start;
    private final DatePicker end;

    public LocalDateIntervalPicker() {
        this(null);
    }

    public LocalDateIntervalPicker(LocalDateInterval interval) {
        // build
        setSpacing(7);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add("local-date-interval-picker");

        start = new DatePicker();
        start.getStyleClass().add("interval-start");
        start.setPrefWidth(130);
        getChildren().addAll(start);

        Label until = new Label(bundle.getString("until"));
        getChildren().addAll(until);

        end = new DatePicker();
        end.getStyleClass().add("interval-end");
        end.setPrefWidth(130);
        getChildren().addAll(end);

        // bind
        valueProperty().addListener(o -> handleNewValue());
        start.valueProperty().addListener(o -> handleNewStart());
        end.valueProperty().addListener(o -> handleNewEnd());

        // configure
        setValue(interval);
    }

    public ObjectProperty<LocalDateInterval> valueProperty() {
        return value;
    }

    public LocalDateInterval getValue() {
        return value.get();
    }

    public void setValue(LocalDateInterval value) {
        this.value.set(value);
    }

    private void handleNewValue() {
        LocalDateInterval interval = value.get();
        if (interval != null) {
            start.setValue(interval.getStart());
            end.setValue(interval.getEnd());
        } else {
            start.setValue(null);
            end.setValue(null);
        }
    }

    private void handleNewStart() {
        LocalDateInterval interval = getValue();
        LocalDate newStart = start.getValue();
        LocalDate oldStart = interval != null ? interval.getStart() : null;
        LocalDate currentEnd = interval != null ? interval.getEnd() : end.getValue();
        if (newStart == null || currentEnd == null) {
            return;
        }

        try {
            setValue(LocalDateInterval.of(newStart, currentEnd));
        } catch (IllegalArgumentException ignored) {
            start.setValue(oldStart);
        }
    }

    private void handleNewEnd() {
        LocalDateInterval interval = getValue();
        LocalDate newEnd = end.getValue();
        LocalDate oldEnd = interval != null ? interval.getEnd() : null;
        LocalDate currentStart = interval != null ? interval.getStart() : start.getValue();
        if (currentStart == null || newEnd == null) {
            return;
        }

        try {
            interval = LocalDateInterval.of(currentStart, newEnd);
            setValue(interval);
        } catch (IllegalArgumentException ignored) {
            end.setValue(oldEnd);
        }
    }

}
