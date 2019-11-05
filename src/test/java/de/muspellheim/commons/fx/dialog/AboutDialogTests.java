/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import de.muspellheim.commons.fx.test.*;
import de.muspellheim.commons.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class AboutDialogTests {

    @Test
    void created() throws Exception {
        // Given
        Locale.setDefault(Locale.GERMAN);
        About about = About.of("Foobar", Version.parse("1.2.3"), 2018, "Muspellheim");

        // When
        AtomicReference<AboutDialog> dialog = new AtomicReference<>();
        Phaser dialogCeated = new Phaser(1);
        Platform.runLater(() -> {
            dialog.set(new AboutDialog(about, new Image("/de/muspellheim/commons/fx/dialog/app-icon.png")));
            dialog.get().show();
            dialogCeated.arrive();
        });
        dialogCeated.awaitAdvanceInterruptibly(0, 2, TimeUnit.SECONDS);
        Platform.runLater(() -> dialog.get().hide());

        // Then
        Scene scene = dialog.get().getStage().getScene();
        ImageView appIcon = (ImageView) scene.lookup(".app-icon");
        Label title = (Label) scene.lookup(".title");
        Label version = (Label) scene.lookup(".version");
        Label copyright = (Label) scene.lookup(".copyright");
        Label rights = (Label) scene.lookup(".rights");
        assertAll(
            () -> assertNotNull(appIcon.getImage(), "appIcon"),
            () -> assertEquals("Foobar", title.getText(), "title"),
            () -> assertEquals("Version 1.2.3", version.getText(), "version"),
            () -> assertEquals("Copyright © 2018 Muspellheim", copyright.getText(), "copyright"),
            () -> assertEquals("Alle Rechte vorbehalten.", rights.getText(), "rights")
        );
    }

}
