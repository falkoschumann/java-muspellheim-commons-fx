/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import de.muspellheim.commons.fx.test.*;
import javafx.application.*;
import javafx.stage.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class AppTests {

    @Test
    void createdWithoutErrors() throws Exception {
        // When
        AtomicReference<Exception> exception = new AtomicReference<>();
        Phaser started = new Phaser(1);
        Platform.runLater(() -> {
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
