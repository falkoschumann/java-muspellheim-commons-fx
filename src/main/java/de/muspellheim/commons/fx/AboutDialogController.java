/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import de.muspellheim.commons.util.*;
import javafx.scene.*;
import javafx.scene.image.*;

/**
 * A general about dialog.
 */
public class AboutDialogController extends StageController<Parent, AboutViewController> {

    private final About about;
    private Image appIcon;

    /**
     * Create a about dialog with specified information.
     *
     * @param about   the about information
     * @param appIcon the app icon, should be 64x64 pixel
     */
    public AboutDialogController(About about, Image appIcon) {
        super(AboutViewController.class);
        this.about = about;
        this.appIcon = appIcon;
    }

    @Override
    protected void init(Parent view, AboutViewController controller) {
        getStage().setResizable(false);
        controller.setAbout(about);
        controller.setAppIcon(appIcon);
    }

}
