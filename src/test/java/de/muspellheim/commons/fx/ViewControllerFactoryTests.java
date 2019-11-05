/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import java.util.*;

import de.muspellheim.commons.fx.test.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JavaFXExtension.class)
class ViewControllerFactoryTests {

    @Test
    void created() {
        // Given
        Locale.setDefault(Locale.GERMAN);

        // When
        ViewControllerFactory<Parent, FooViewController> factory = ViewControllerFactory.load(FooViewController.class);
        Parent view = factory.getView();
        FooViewController controller = factory.getController();

        // Then
        assertAll(
            () -> assertTrue(view instanceof AnchorPane, "view"),
            () -> assertNotNull(controller.getResources(), "controller resources"),
            () -> assertEquals("Hallo Welt!", controller.getLabel().getText(), "controllers label text")
        );
    }

}
