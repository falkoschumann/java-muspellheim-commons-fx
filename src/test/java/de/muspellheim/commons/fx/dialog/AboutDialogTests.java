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
import javafx.scene.control.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class AboutDialogTests {

    @Test
    void created() {
        // Given
        Locale.setDefault(Locale.ENGLISH);
        About about = About.of("Hello World", Version.parse("1.0.0"), 2019, "ACME Ltd.");

        // When
        Phaser dialogCreated = new Phaser(1);
        AtomicReference<AboutDialog> dialog = new AtomicReference<>();
        Platform.runLater(() -> {
            dialog.set(new AboutDialog(about, null));
            dialogCreated.arrive();
        });
        dialogCreated.awaitAdvance(0);

        // Then
        Label title = (Label) dialog.get().getDialogPane().lookup(".title");
        Label version = (Label) dialog.get().getDialogPane().lookup(".version");
        Label copyright = (Label) dialog.get().getDialogPane().lookup(".copyright");
        Label rights = (Label) dialog.get().getDialogPane().lookup(".rights");
        assertAll(
            () -> assertEquals("Hello World", title.getText(), "title"),
            () -> assertEquals("Version 1.0.0", version.getText(), "version"),
            () -> assertEquals("Copyright © 2019 ACME Ltd.", copyright.getText(), "copyright"),
            () -> assertEquals("All rights reserved.", rights.getText(), "rights")
        );
    }

}
