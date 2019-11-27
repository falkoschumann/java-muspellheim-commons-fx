/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import javafx.scene.*;
import javafx.scene.chart.*;
import lombok.*;

/**
 * Zoom a chart axis.
 */
public final class ScalableAxis {

    // TODO Add mouse move handler for dragging position

    private ScalableAxis() {
    }

    /**
     * Install a scroll handler to zoom in and zoom out a chart axis.
     */
    public static <X, Y> void install(@NonNull XYChart<X, Y> chart, @NonNull NumberAxis axis) {
        Cursor cursor = axis.getSide().isHorizontal() ? Cursor.H_RESIZE : Cursor.V_RESIZE;
        chart.setCursor(cursor);
        chart.setOnScroll(e -> {
            double range = axis.getUpperBound() - axis.getLowerBound();

            Node chartPlotArea = chart.lookup(".chart-plot-background");
            double relativeX = e.getX() - chartPlotArea.getLayoutX() - chart.getInsets().getLeft();
            double offset = axis.getValueForDisplay(relativeX).doubleValue();
            double relativeToOffset = (offset - axis.getLowerBound()) / range;

            double diff = range * 0.1;
            if (e.getDeltaY() < 0) {
                diff *= -1;
            }
            double newLowerBound = axis.getLowerBound() + diff * relativeToOffset;
            axis.setLowerBound(newLowerBound);
            double newUpperBound = axis.getUpperBound() - diff * (1 - relativeToOffset);
            axis.setUpperBound(newUpperBound);
        });
    }

}
