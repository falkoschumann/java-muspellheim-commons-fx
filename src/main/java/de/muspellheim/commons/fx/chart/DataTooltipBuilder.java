/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Build a tooltip for name/value pairs, useful for chart data points.
 */
public class DataTooltipBuilder {

    private final Tooltip tooltip;
    private final GridPane pane;

    private int row;

    /**
     * Creates a new tooltip builder.
     */
    public DataTooltipBuilder() {
        pane = new GridPane();
        pane.setStyle("-fx-font-size: 12");
        pane.setHgap(5);
        pane.setVgap(3);
        tooltip = new Tooltip();
        tooltip.setGraphic(pane);
    }

    /**
     * Add a name/value pair to tooltip.
     *
     * @param name  the name
     * @param value the value
     * @return the builder for chaining.
     */
    public DataTooltipBuilder addData(String name, Object value) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow");
        pane.add(nameLabel, 0, row);

        Label valueLabel = new Label(String.valueOf(value));
        pane.add(valueLabel, 1, row);

        row++;
        return this;
    }

    /**
     * Install the tooltip to a node.
     *
     * @param node a node.
     */
    public void install(Node node) {
        Tooltip.install(node, tooltip);
    }

}
