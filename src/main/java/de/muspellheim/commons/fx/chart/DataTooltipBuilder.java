/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.util.LinkedHashMap;
import java.util.Map;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * Build a tooltip for name/value pairs, useful for chart data points.
 *
 * <p>Optionally a title can set for tooltip.
 */
public class DataTooltipBuilder {

  private static final String PANE_STYLE = "-fx-font-size: 12";
  private static final String TITLE_STYLE = "-fx-font-weight: bold; -fx-text-fill: yellow";

  private final Map<String, Object> values = new LinkedHashMap<>();
  private String title;

  /**
   * Apply title to tooltip.
   *
   * @param s the title string
   * @return the builder for chaining
   */
  public DataTooltipBuilder applyTitle(String s) {
    title = s;
    return this;
  }

  /**
   * Add a name/value pair to tooltip.
   *
   * @param name the name
   * @param value the value
   * @return the builder for chaining
   */
  public DataTooltipBuilder addData(String name, Object value) {
    values.put(name, value);
    return this;
  }

  /**
   * Install the tooltip into a node.
   *
   * @param node a node
   */
  public void install(Node node) {
    Tooltip tooltip = createTooltip();
    Tooltip.install(node, tooltip);
  }

  /**
   * Install the tooltip as hover into a node.
   *
   * @param node a node
   */
  public void installAsHover(Node node) {
    Tooltip tooltip = createTooltip();
    node.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        e -> {
          Node hoveredNode = (Node) e.getSource();
          Window owner = getWindow(hoveredNode);
          final boolean treeVisible = isWindowHierarchyVisible(hoveredNode);
          if (owner != null && treeVisible) {
            tooltip.show(owner, e.getScreenX() + 10, e.getScreenY() + 7);
          }
        });
    node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tooltip.hide());
  }

  private static Window getWindow(final Node node) {
    final Scene scene = node == null ? null : node.getScene();
    return scene == null ? null : scene.getWindow();
  }

  private static boolean isWindowHierarchyVisible(Node node) {
    boolean treeVisible = node != null;
    Parent parent = node == null ? null : node.getParent();
    while (parent != null && treeVisible) {
      treeVisible = parent.isVisible();
      parent = parent.getParent();
    }
    return treeVisible;
  }

  private Tooltip createTooltip() {
    GridPane pane = new GridPane();
    pane.setStyle(PANE_STYLE);
    pane.setHgap(5);
    pane.setVgap(3);

    int row = 0;
    if (title != null) {
      Label titleLabel = new Label(title);
      titleLabel.setStyle(TITLE_STYLE);
      pane.add(titleLabel, 0, row, 2, 1);
      GridPane.setHalignment(titleLabel, HPos.CENTER);
      row++;
    }

    for (Map.Entry<String, Object> e : values.entrySet()) {
      Label nameLabel = new Label(e.getKey());
      nameLabel.setStyle(TITLE_STYLE);
      pane.add(nameLabel, 0, row);

      Label valueLabel = new Label(String.valueOf(e.getValue()));
      pane.add(valueLabel, 1, row);

      row++;
    }

    Tooltip tooltip = new Tooltip();
    tooltip.setGraphic(pane);
    return tooltip;
  }
}
