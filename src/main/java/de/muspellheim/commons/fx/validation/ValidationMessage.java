/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import javafx.scene.control.*;
import lombok.*;

@Value
@SuppressWarnings("checkstyle:VisibilityModifier")
public class ValidationMessage {

    @NonNull Control target;
    @NonNull String text;

}
