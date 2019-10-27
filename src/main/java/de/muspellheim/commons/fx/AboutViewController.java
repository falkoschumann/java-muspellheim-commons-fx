/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import de.muspellheim.commons.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class AboutViewController {

    @FXML
    private ImageView appIcon;

    @FXML
    private Label title;

    @FXML
    private Label version;

    @FXML
    private Label copyright;

    @FXML
    private Label rights;

    public void setAppIcon(Image value) {
        appIcon.setImage(value);
    }

    public void setAbout(About value) {
        title.setText(value.getTitle());
        version.setText(value.getVersionText());
        copyright.setText(value.getCopyright());
        rights.setText(value.getRights());
    }

}
