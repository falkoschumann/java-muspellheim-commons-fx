/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * A hint is a popup with a message near an owner node, like a text field.
 *
 * <p>The popup is showing if severity is <strong>not</strong> {@code OK} and hide if severity is
 * {@code OK}.
 */
public class Hint extends Tooltip {

  private static final String POPUP_SHADOW_EFFECT =
      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 5);";

  private static final String TOOLTIP_COMMON_EFFECTS =
      "-fx-font-weight: bold; -fx-padding: 5; -fx-border-width:1;";

  private static final String ERROR_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: #fbefef; -fx-text-fill: #cc0033; -fx-border-color: #cc0033;";

  private static final String WARNING_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: #ffffcc; -fx-text-fill: #cc9900; -fx-border-color: #cc9900;";

  private static final String INFO_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: #c4d0ef; -fx-text-fill: #ffffff; -fx-border-color: #a8c8ff;";

  private ObjectProperty<Severity> severityProperty =
      new SimpleObjectProperty<Severity>(this, "severity", Severity.OK) {
        @Override
        protected void invalidated() {
          Severity severity = getValue();
          if (severity == null) {
            hide();
          } else {
            switch (severity) {
              case ERROR:
                setStyle(ERROR_TOOLTIP_EFFECT);
                showAtOwner();
                break;
              case WARNING:
                setStyle(WARNING_TOOLTIP_EFFECT);
                showAtOwner();
                break;
              case INFO:
                setStyle(INFO_TOOLTIP_EFFECT);
                showAtOwner();
                break;
              case OK:
                setStyle(null);
                hide();
                break;
            }
          }
        }
      };

  private final Node owner;

  /**
   * Obtain a hint for an owner node.
   *
   * @param owner a node.
   */
  public Hint(Node owner) {
    this.owner = owner;
    setOpacity(.9);
    setAutoFix(true);
  }

  public final ObjectProperty<Severity> severityPropertyProperty() {
    return severityProperty;
  }

  public final Severity getSeverity() {
    return severityProperty.get() == null ? Severity.OK : severityProperty.get();
  }

  public final void setSeverity(Severity value) {
    severityProperty.set(value);
  }

  private void showAtOwner() {
    Point2D location = getLocation((Region) owner);
    if (location != null) {
      show(owner, location.getX(), location.getY());
    }
  }

  private Point2D getLocation(Region owner) {
    Scene scene = owner.getScene();
    if (scene == null) {
      return null;
    }
    Window window = scene.getWindow();
    return owner
        .localToScene(0.0, 0.0)
        .add(0, owner.getHeight() + 3)
        .add(scene.getX(), scene.getY())
        .add(window.getX(), window.getY());
  }
}
