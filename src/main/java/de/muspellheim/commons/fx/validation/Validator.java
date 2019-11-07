/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import javafx.scene.control.*;

public interface Validator<T> {

    ValidationResult validate(Control control, T value);

    static <T> Validator<T> combine(Validator<T>... validators) {
        return (control, value) -> Stream.of(validators)
            .map(validator -> validator.validate(control, value))
            .reduce(new ValidationResult(), ValidationResult::combine);
    }

    static <T> Validator<T> createEmptyValidator(final String message) {
        return (control, value) -> {
            boolean condition = value instanceof String ? value.toString().trim().isEmpty() : value == null;
            return ValidationResult.fromMessageIf(control, message, condition);
        };
    }

    static <T> Validator<T> createPredicateValidator(Predicate<T> predicate, String message) {
        return (control, value) -> {
            boolean condition = !predicate.test(value);
            return ValidationResult.fromMessageIf(control, message, condition);
        };
    }

    static Validator<String> createRegexValidator(final String message, final String regex) {
        return createRegexValidator(message, Pattern.compile(regex));
    }

    static Validator<String> createRegexValidator(final String message, final Pattern regex) {
        return (control, value) -> {
            boolean condition = value == null || !regex.matcher(value).matches();
            return ValidationResult.fromMessageIf(control, message, condition);
        };
    }

}
