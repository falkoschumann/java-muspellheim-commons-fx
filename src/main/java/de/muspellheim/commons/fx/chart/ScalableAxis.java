/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.input.*;
import lombok.*;

/**
 * Zoom and move a chart axis.
 */
public final class ScalableAxis {

    // TODO Add mouse move handler for dragging position

    private ScalableAxis() {
    }

    /**
     * Install scroll handler to zoom in and zoom out a chart axis and drag handler to move a chart axis.
     * <p>
     * You can call this function for the x axis and y axis of a xy chart to handle both axis.
     *
     * @param chart the chart with the axis
     * @param axis  the axis to zoom and move
     */
    public static <T extends Number> void install(@NonNull XYChart<?, ?> chart, @NonNull ValueAxis<T> axis) {
        installZoomable(chart, axis);
        installMoveable(chart, axis);
    }

    private static <T extends Number> void installZoomable(XYChart<?, ?> chart, ValueAxis<T> axis) {
        chart.addEventHandler(ScrollEvent.SCROLL, e -> {
            Node chartPlotArea = chart.lookup(".chart-plot-background");
            double displayPosition = axis.getSide().isHorizontal()
                ? e.getX() - chartPlotArea.getLayoutX() - chart.getInsets().getLeft()
                : e.getY() - chartPlotArea.getLayoutY() - chart.getInsets().getTop();
            double value = axis.getValueForDisplay(displayPosition).doubleValue();
            double range = axis.getUpperBound() - axis.getLowerBound();
            double relativeToOffset = (value - axis.getLowerBound()) / range;
            double factor = e.getDeltaY() < 0 ? -1 : 1;
            double delta = range * 0.1 * factor;
            axis.setLowerBound(axis.getLowerBound() + delta * relativeToOffset);
            axis.setUpperBound(axis.getUpperBound() - delta * (1 - relativeToOffset));
        });
    }

    private static <T extends Number> void installMoveable(XYChart<?, ?> chart, ValueAxis<T> axis) {
        MoveData moveData = new MoveData();
        chart.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            moveData.startLowerBound = axis.getLowerBound();
            moveData.startUpperBound = axis.getUpperBound();
            double value = axis.getSide().isHorizontal() ? e.getX() : e.getY();
            moveData.start = axis.getValueForDisplay(value).doubleValue();
            chart.setCursor(Cursor.CLOSED_HAND);
        });
        chart.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            double value = axis.getSide().isHorizontal() ? e.getX() : e.getY();
            double end = axis.getValueForDisplay(value).doubleValue();
            double delta = end - moveData.start;
            axis.setLowerBound(moveData.startLowerBound - delta);
            axis.setUpperBound(moveData.startUpperBound - delta);
            chart.setCursor(null);
        });
        // TODO Using mouse dragged instead of mouse release to set bounds is lagging
        //chart.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {});
    }

    private static class MoveData {
        private double startLowerBound;
        private double startUpperBound;
        private double start;
    }

}
