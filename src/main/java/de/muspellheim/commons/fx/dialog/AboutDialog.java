/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import de.muspellheim.commons.util.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * A general about dialog.
 */
public class AboutDialog extends Dialog<Void> {

    /**
     * Create a about dialog with specified information.
     *
     * @param about   the about information
     * @param appIcon the app icon, should be 64x64 pixel
     */
    public AboutDialog(About about, Image appIcon) {
        Pane content = createContent(about, appIcon);
        Pane additional = createAdditional(about);
        content.getChildren().add(additional);
        getDialogPane().setContent(content);
    }

    private Pane createContent(About about, Image appIcon) {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(6);
        content.setPadding(new Insets(12, 36, 12, 36));
        content.setStyle("-fx-font-size: 11");

        ImageView appIconView = new ImageView(appIcon);
        appIconView.getStyleClass().add("app-icon");
        appIconView.setFitWidth(64);
        appIconView.setFitHeight(64);
        appIconView.setPreserveRatio(true);
        content.getChildren().add(appIconView);

        Label title = new Label();
        title.getStyleClass().add("title");
        title.setText(about.getTitle());
        title.setFont(new Font("System Bold", 15));
        content.getChildren().add(title);

        Label version = new Label();
        version.getStyleClass().add("version");
        version.setText(about.getVersionText());
        content.getChildren().add(version);
        return content;
    }

    private Pane createAdditional(About about) {
        VBox additional = new VBox();
        additional.setAlignment(Pos.CENTER);

        Label copyright = new Label();
        copyright.getStyleClass().add("copyright");
        copyright.setText(about.getCopyright());
        additional.getChildren().add(copyright);

        Label rights = new Label();
        rights.getStyleClass().add("rights");
        rights.setText(about.getRights());
        additional.getChildren().add(rights);

        return additional;
    }

}
