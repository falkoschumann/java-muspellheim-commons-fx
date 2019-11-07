/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import java.util.*;

import javafx.scene.control.*;

public class ValidationResult implements Cloneable {

    private final List<ValidationMessage> messages = new ArrayList<>();

    static ValidationResult fromMessageIf(Control target, String text, boolean condition) {
        return new ValidationResult().addMessageIf(target, text, condition);
    }

    static ValidationResult fromResults(Collection<ValidationResult> results) {
        return new ValidationResult().combineAll(results);
    }

    public final List<ValidationMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    ValidationResult addMessageIf(Control target, String text, boolean condition) {
        return condition ? add(new ValidationMessage(target, text)) : this;
    }

    ValidationResult add(ValidationMessage message) {
        if (message != null) {
            messages.add(message);
        }
        return this;
    }

    ValidationResult addAll(Collection<? extends ValidationMessage> m) {
        m.forEach(this::add);
        return this;
    }

    ValidationResult combine(ValidationResult validationResult) {
        return validationResult == null ? clone() : clone().addAll(validationResult.getMessages());
    }

    ValidationResult combineAll(Collection<ValidationResult> validationResults) {
        return validationResults.stream().reduce(clone(), (r1, r2) -> r1.addAll(r2.getMessages()));
    }

    @Override
    public ValidationResult clone() {
        try {
            return (ValidationResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

}
