/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.time.*;
import java.time.format.*;

import javafx.scene.control.*;

/**
 * Table cell for a {@link LocalDateTime}.
 *
 * @param <S> type of table items
 */
public class DateTimeTableCell<S> extends TableCell<S, LocalDateTime> {

    private final DateTimeFormatter formatter;

    /**
     * Create a cell with given date time style.
     *
     * @param dateTimeStyle the format style to formatting date time in cell
     */
    public DateTimeTableCell(FormatStyle dateTimeStyle) {
        this(DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle));
    }

    /**
     * Create a cell with given date style and time style.
     *
     * @param dateStyle the format style to formatting date in cell
     * @param timeStyle the format style to formatting time in cell
     */
    public DateTimeTableCell(FormatStyle dateStyle, FormatStyle timeStyle) {
        this(DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle));
    }

    /**
     * Create a cell with given formatter.
     *
     * @param formatter the formatter to formatting date time in cell
     */
    public DateTimeTableCell(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    protected void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.format(formatter));
        }
    }

}
