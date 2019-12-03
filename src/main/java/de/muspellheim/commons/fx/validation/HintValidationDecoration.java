/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.validation;

import de.muspellheim.commons.fx.control.decoration.HintDecoration;
import java.util.Collection;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.AbstractValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

/**
 * Use a hint popup for validation decoration.
 *
 * <p><img src="doc-files/hint-validation-decoration.png" alt="Hint validation decoration example">
 */
public class HintValidationDecoration extends AbstractValidationDecoration {

  private static final Image REQUIRED_IMAGE =
      new Image(
          GraphicValidationDecoration.class
              .getResource("/impl/org/controlsfx/control/validation/required-indicator.png")
              .toExternalForm());

  private static final String POPUP_SHADOW_EFFECT =
      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 5);";
  private static final String TOOLTIP_COMMON_EFFECTS =
      "-fx-font-weight: bold; -fx-padding: 5; -fx-border-width:1;";

  private static final String ERROR_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: FBEFEF; -fx-text-fill: cc0033; -fx-border-color:cc0033;";

  private static final String WARNING_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: FFFFCC; -fx-text-fill: CC9900; -fx-border-color: CC9900;";

  private static final String INFO_TOOLTIP_EFFECT =
      POPUP_SHADOW_EFFECT
          + TOOLTIP_COMMON_EFFECTS
          + "-fx-background-color: c4d0ef; -fx-text-fill: FFFFFF; -fx-border-color: a8c8ff;";

  @Override
  protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
    return Collections.singletonList(new HintDecoration(createTooltip(message)));
  }

  @Override
  protected Collection<Decoration> createRequiredDecorations(Control target) {
    return Collections.singletonList(
        new GraphicDecoration(
            new ImageView(REQUIRED_IMAGE),
            Pos.TOP_LEFT,
            REQUIRED_IMAGE.getWidth() / 2,
            REQUIRED_IMAGE.getHeight() / 2));
  }

  private Tooltip createTooltip(ValidationMessage message) {
    Tooltip tooltip = new Tooltip(message.getText());
    tooltip.setOpacity(.9);
    tooltip.setAutoFix(true);
    tooltip.setStyle(getStyleBySeverity(message.getSeverity()));
    return tooltip;
  }

  private String getStyleBySeverity(Severity severity) {
    switch (severity) {
      case ERROR:
        return ERROR_TOOLTIP_EFFECT;
      case WARNING:
        return WARNING_TOOLTIP_EFFECT;
      default:
        return INFO_TOOLTIP_EFFECT;
    }
  }
}
