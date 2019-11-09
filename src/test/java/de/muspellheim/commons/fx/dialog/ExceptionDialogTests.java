/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.dialog;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import de.muspellheim.commons.fx.test.*;
import javafx.application.*;
import javafx.scene.control.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class ExceptionDialogTests {

    @Test
    void created() throws Exception {
        // Given
        Locale.setDefault(Locale.ENGLISH);
        Throwable throwable = new IllegalStateException("Lorem ipsum");

        // When
        Phaser dialogCreated = new Phaser(1);
        AtomicReference<CustomExceptionDialog> dialog = new AtomicReference<>();
        Platform.runLater(() -> {
            dialog.set(new CustomExceptionDialog(throwable));
            dialogCreated.arrive();
        });
        dialogCreated.awaitAdvanceInterruptibly(0, 2, TimeUnit.SECONDS);

        // Then
        TextArea stackTrace = (TextArea) dialog.get().getDialogPane().lookup(".stack-trace");
        assertAll(
            () -> assertEquals("Illegal State Exception", dialog.get().getHeaderText(), "header text"),
            () -> assertEquals("Lorem ipsum", dialog.get().getContentText(), "content text"),
            () -> assertFalse(stackTrace.getText().isEmpty(), "stack trace not empty")
        );
    }

}
