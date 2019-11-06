/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import de.muspellheim.commons.fx.*;
import javafx.scene.*;
import javafx.stage.*;

public class DemoWindow extends StageController<Parent, DemoViewController> {

    public DemoWindow(Stage stage) {
        super(DemoViewController.class, stage);
    }

}
