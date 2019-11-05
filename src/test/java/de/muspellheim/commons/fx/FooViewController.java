/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class FooViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label label;

    ResourceBundle getResources() {
        return resources;
    }

    Label getLabel() {
        return label;
    }

}
