/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.util.*;

import de.muspellheim.commons.time.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

class DateIntervalPickerSkin extends SkinBase<DateIntervalPicker> {

    private final ResourceBundle bundle = ResourceBundle.getBundle(DateIntervalPicker.class.getName());

    private final DatePicker start;
    private final ComboBoxBase<LocalDate> end;

    DateIntervalPickerSkin(DateIntervalPicker picker) {
        super(picker);

        //
        // Create
        //

        HBox root = new HBox();
        root.setSpacing(7);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getStyleClass().add("local-date-interval-picker");
        getChildren().add(root);

        start = new DatePicker();
        start.getStyleClass().add("interval-start");
        start.setPrefWidth(130);
        root.getChildren().addAll(start);

        Label until = new Label(bundle.getString("until"));
        root.getChildren().addAll(until);

        end = new DatePicker();
        end.getStyleClass().add("interval-end");
        end.setPrefWidth(130);
        root.getChildren().addAll(end);

        handleNewValue();

        //
        // Bind
        //

        getSkinnable().valueProperty().addListener(o -> handleNewValue());
        start.valueProperty().addListener(o -> handleNewStart());
        end.valueProperty().addListener(o -> handleNewEnd());
    }

    private void handleNewValue() {
        LocalDateInterval interval = getSkinnable().getValue();
        if (interval != null) {
            start.setValue(interval.getStart());
            end.setValue(interval.getEnd());
        } else {
            start.setValue(null);
            end.setValue(null);
        }
    }

    private void handleNewStart() {
        LocalDateInterval interval = getSkinnable().getValue();
        LocalDate newStart = start.getValue();
        LocalDate oldStart = interval != null ? interval.getStart() : null;
        LocalDate currentEnd = interval != null ? interval.getEnd() : end.getValue();
        if (newStart == null || currentEnd == null) {
            return;
        }

        try {
            getSkinnable().setValue(LocalDateInterval.of(newStart, currentEnd));
        } catch (IllegalArgumentException ignored) {
            start.setValue(oldStart);
        }
    }

    private void handleNewEnd() {
        LocalDateInterval interval = getSkinnable().getValue();
        LocalDate newEnd = end.getValue();
        LocalDate oldEnd = interval != null ? interval.getEnd() : null;
        LocalDate currentStart = interval != null ? interval.getStart() : start.getValue();
        if (currentStart == null || newEnd == null) {
            return;
        }

        try {
            interval = LocalDateInterval.of(currentStart, newEnd);
            getSkinnable().setValue(interval);
        } catch (IllegalArgumentException ignored) {
            end.setValue(oldEnd);
        }
    }

}
