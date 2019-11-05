/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import de.muspellheim.commons.fx.test.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class StatusBarTests {

    @Test
    void created() {
        // When
        StatusBar statusBar = new StatusBar();
        statusBar.createDefaultSkin();
        statusBar.setText("Ready.");
        statusBar.setProgress(-1);
        statusBar.getLeftItems().add(new Button("Foo"));
        statusBar.getRightItems().add(new Button("Bar"));

        // Then
        HBox leftItems = (HBox) statusBar.lookup(".left-items");
        Label text = (Label) statusBar.lookup(".status-text");
        ProgressBar progress = (ProgressBar) statusBar.lookup(".status-progress");
        HBox rightItems = (HBox) statusBar.lookup(".right-items");
        assertAll(
            () -> assertEquals("Foo", ((Labeled) leftItems.getChildren().get(0)).getText(), "left items"),
            () -> assertEquals("Ready.", text.getText(), "text"),
            () -> assertEquals(-1, progress.getProgress(), "progress"),
            () -> assertEquals("Bar", ((Labeled) rightItems.getChildren().get(0)).getText(), "right items")
        );
    }

}
