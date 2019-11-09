/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import org.controlsfx.validation.*;

/**
 * Validation support with validation decoration by a hint popup.
 */
public class HintValidationSupport extends ValidationSupport {

    /**
     * Creates a hint validation support.
     */
    public HintValidationSupport() {
        setValidationDecorator(new HintValidationDecoration());
    }

}
