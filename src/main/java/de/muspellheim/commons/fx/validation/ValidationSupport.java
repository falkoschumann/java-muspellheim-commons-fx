/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import java.util.*;
import java.util.function.*;

import javafx.application.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;

public class ValidationSupport {

    private final ReadOnlyObjectWrapper<ValidationResult> validationResult = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyBooleanWrapper invalid = new ReadOnlyBooleanWrapper(this, "invalid");

    private final ObservableMap<Control, ValidationResult> validationResults = FXCollections.observableMap(new WeakHashMap<>());

    public ValidationSupport() {
        validationResult.addListener((o, oldValue, newValue) -> {
            invalid.set(!newValue.getMessages().isEmpty());
            redecorate();
        });
        validationResults.addListener((MapChangeListener.Change<? extends Control, ? extends ValidationResult> change) ->
            validationResult.set(ValidationResult.fromResults(validationResults.values()))
        );
    }

    public final ReadOnlyObjectProperty<ValidationResult> validationResultProperty() {
        return validationResult.getReadOnlyProperty();
    }

    public final ValidationResult getValidationResult() {
        return validationResult.get();
    }

    public final ReadOnlyBooleanProperty invalidProperty() {
        return invalid.getReadOnlyProperty();
    }

    public final boolean isInvalid() {
        return invalid.get();
    }

    /*
    public <T> void registerValidator(Control control, Validator<T> validator) {
        // TODO register validator
    }
    */

    public void registerValidator(Control control, Validator<String> validator) {
        TextField text = (TextField) control;
        StringProperty observable = text.textProperty();

        Consumer<String> updateResults = value -> Platform.runLater(() -> validationResults.put(control, validator.validate(control, value)));
        observable.addListener((o, oldValue, newValue) -> updateResults.accept(newValue));
        updateResults.accept(observable.get());
    }

    protected void redecorate() {
        // TODO redecorate
    }

}
