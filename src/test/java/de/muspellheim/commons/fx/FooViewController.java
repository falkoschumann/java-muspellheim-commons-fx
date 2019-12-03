/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FooViewController {

  @FXML private ResourceBundle resources;

  @FXML private Label label;

  ResourceBundle getResources() {
    return resources;
  }

  Label getLabel() {
    return label;
  }
}
