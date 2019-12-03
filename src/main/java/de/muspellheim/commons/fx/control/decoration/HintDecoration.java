/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control.decoration;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import org.controlsfx.control.decoration.Decoration;

/** A decoration with a hint popup. */
public class HintDecoration extends Decoration {

  private final PopupWindow hint;

  /**
   * Creates a decoration with given popup.
   *
   * @param hint a hint popup
   */
  public HintDecoration(PopupWindow hint) {
    this.hint = hint;
  }

  @Override
  public Node applyDecoration(Node targetNode) {
    Point2D location = getLocation((Region) targetNode);
    hint.show(targetNode, location.getX(), location.getY());
    return null;
  }

  @Override
  public void removeDecoration(Node targetNode) {
    hint.hide();
  }

  private Point2D getLocation(Region owner) {
    Scene scene = owner.getScene();
    Window window = scene.getWindow();
    return owner
        .localToScene(0.0, 0.0)
        .add(0, owner.getHeight() + 3)
        .add(scene.getX(), scene.getY())
        .add(window.getX(), window.getY());
  }
}
