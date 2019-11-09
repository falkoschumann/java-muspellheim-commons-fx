/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import java.time.*;

import lombok.*;

@Value
@SuppressWarnings("checkstyle:VisibilityModifier")
public class DateTimes {

    @NonNull LocalDateTime dateTime;

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

}
