/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muspellheim.commons.fx.test.JavaFXExtension;
import java.util.Locale;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXExtension.class)
class ViewControllerFactoryTests {

  @Test
  void created() {
    // Given
    Locale.setDefault(Locale.GERMAN);

    // When
    ViewControllerFactory<Parent, FooViewController> factory =
        ViewControllerFactory.load(FooViewController.class);
    Parent view = factory.getView();
    FooViewController controller = factory.getController();

    // Then
    assertAll(
        () -> assertTrue(view instanceof AnchorPane, "view"),
        () -> assertNotNull(controller.getResources(), "controller resources"),
        () ->
            assertEquals("Hallo Welt!", controller.getLabel().getText(), "controllers label text"));
  }
}
