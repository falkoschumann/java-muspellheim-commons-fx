/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import de.muspellheim.commons.fx.StageController;
import de.muspellheim.commons.util.About;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import lombok.NonNull;

/**
 * A general about dialog.
 *
 * <p><img src="doc-files/about-dialog.png" alt="About dialog example">
 */
public class AboutDialog extends StageController<Parent, AboutViewController> {

  private final About about;
  private final Image appIcon;

  /**
   * Create a about dialog with specified information.
   *
   * @param about the about information
   * @param appIcon the app icon, should be 64x64 pixel
   */
  public AboutDialog(@NonNull About about, @NonNull Image appIcon) {
    super(AboutViewController.class);
    this.about = about;
    this.appIcon = appIcon;

    getStage().initModality(Modality.APPLICATION_MODAL);
    getStage().setResizable(false);
  }

  @Override
  protected void init(Parent view, AboutViewController controller) {
    controller.setAppIcon(appIcon);
    controller.setAbout(about);
  }
}
