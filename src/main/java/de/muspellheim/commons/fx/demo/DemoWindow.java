/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import de.muspellheim.commons.fx.StageController;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class DemoWindow extends StageController<Parent, DemoViewController> {

  public DemoWindow(Stage stage) {
    super(DemoViewController.class, stage);
  }
}
