/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.util.Locale;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class ExceptionDialogTests {

  @Test
  void created() throws Exception {
    // Given
    Locale.setDefault(Locale.ENGLISH);
    Throwable throwable = new IllegalStateException("Lorem ipsum");

    // When
    Phaser dialogCreated = new Phaser(1);
    AtomicReference<ExceptionDialog> dialog = new AtomicReference<>();
    Platform.runLater(
        () -> {
          dialog.set(new ExceptionDialog(throwable));
          dialogCreated.arrive();
        });
    dialogCreated.awaitAdvanceInterruptibly(0, 2, TimeUnit.SECONDS);

    // Then
    assertEquals("Illegal State Exception", dialog.get().getHeaderText(), "header text");
    assertEquals("Lorem ipsum", dialog.get().getContentText(), "content text");
  }
}
