/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.util.*;

import de.muspellheim.commons.time.*;
import de.muspellheim.commons.util.*;
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

        start = new DatePicker();
        start.setPrefWidth(130);
        getChildren().addAll(start);

        Label until = new Label(bundle.getString("until"));
        getChildren().addAll(until);

        end = new DatePicker();
        end.setPrefWidth(130);
        getChildren().addAll(end);

        // bind
        valueProperty().addListener(o -> handleNewValue());
        start.valueProperty().addListener(o -> handleNewStart());
        end.valueProperty().addListener(o -> handleNewEnd());

        // configure
        setValue(interval);
    }

    @TestSeam
    DatePicker getStart() {
        return start;
    }

    @TestSeam
    DatePicker getEnd() {
        return end;
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

    /* TODO delete main method
    public static void main(String[] args) {
        new JFXPanel();

        Platform.runLater(() -> {
            LocalDateIntervalPicker root = new LocalDateIntervalPicker(LocalDateInterval.of(LocalDate.now().minusDays(1), LocalDate.now()));
            root.valueProperty().addListener(o -> System.out.println("Invalidation Listener: " + root.getValue()));
            root.valueProperty().addListener((a, b, c) -> System.out.println("Change Listener: " + root.getValue()));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        });
    }
     */

}
