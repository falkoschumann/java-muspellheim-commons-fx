/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import static org.junit.jupiter.api.Assertions.assertNull;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class AppTests {

  @Test
  void createdWithoutErrors() throws Exception {
    // When
    AtomicReference<Exception> exception = new AtomicReference<>();
    Phaser started = new Phaser(1);
    Platform.runLater(
        () -> {
          try {
            Stage stage = new Stage();
            App app = new App();
            app.start(stage);
          } catch (Exception e) {
            exception.set(e);
          }
          started.arrive();
        });

    // Then
    started.awaitAdvanceInterruptibly(0, 5, TimeUnit.SECONDS);
    assertNull(exception.get());
  }
}
