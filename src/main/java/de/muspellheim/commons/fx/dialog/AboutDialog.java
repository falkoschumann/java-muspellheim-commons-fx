/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import de.muspellheim.commons.fx.*;
import de.muspellheim.commons.util.*;
import javafx.scene.*;
import javafx.scene.image.*;
import lombok.*;

/**
 * A general about dialog.
 */
public class AboutDialog extends StageController<Parent, AboutViewController> {

    private final About about;
    private final Image appIcon;

    /**
     * Create a about dialog with specified information.
     *
     * @param about   the about information
     * @param appIcon the app icon, should be 64x64 pixel
     */
    public AboutDialog(@NonNull About about, @NonNull Image appIcon) {
        super(AboutViewController.class);
        this.about = about;
        this.appIcon = appIcon;
    }

    @Override
    protected void init(Parent view, AboutViewController controller) {
        controller.setAppIcon(appIcon);
        controller.setAbout(about);
    }

}
