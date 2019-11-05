/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import de.muspellheim.commons.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

/**
 * View controller for about information.
 */
public class AboutViewController {

    @FXML private ImageView appIcon;
    @FXML private Label title;
    @FXML private Label version;
    @FXML private Label copyright;
    @FXML private Label rights;

    /**
     * Set the about information.
     *
     * @param value the about information
     */
    public void setAbout(About value) {
        title.setText(value.getTitle());
        version.setText(value.getVersionText());
        copyright.setText(value.getCopyright());
        rights.setText(value.getRights());
    }

    /**
     * Set the app icon, should be 64x64 pixel.
     *
     * @param value the app icon
     */
    public void setAppIcon(Image value) {
        appIcon.setImage(value);
    }

}
