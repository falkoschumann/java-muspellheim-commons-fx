/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.time.format.*;

import javafx.scene.control.*;
import javafx.util.*;
import lombok.*;

/**
 * Table cell factory for a {@link LocalDateTime}.
 *
 * @param <S> type of table items
 */
public class DateTimeTableCellFactory<S> implements Callback<TableColumn<S, LocalDateTime>, TableCell<S, LocalDateTime>> {

    /**
     * The date style for formatting the date in cell.
     *
     * @return the date style
     */
    @Getter
    @Setter
    @NonNull
    private FormatStyle dateStyle = FormatStyle.MEDIUM;

    /**
     * The time style for formatting the time in cell.
     *
     * @return the time style
     */
    @Getter
    @Setter
    @NonNull
    private FormatStyle timeStyle = FormatStyle.MEDIUM;

    @Override
    public TableCell<S, LocalDateTime> call(TableColumn<S, LocalDateTime> param) {
        return new DateTimeTableCell<>(dateStyle, timeStyle);
    }

}
