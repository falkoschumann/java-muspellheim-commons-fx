/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import de.muspellheim.commons.util.About;
import de.muspellheim.commons.util.Version;
import java.util.Locale;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
    Platform.runLater(
        () -> {
          dialog.set(
              new AboutDialog(about, new Image("/de/muspellheim/commons/fx/dialog/app-icon.png")));
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
        () -> assertEquals("Alle Rechte vorbehalten.", rights.getText(), "rights"));
  }
}
