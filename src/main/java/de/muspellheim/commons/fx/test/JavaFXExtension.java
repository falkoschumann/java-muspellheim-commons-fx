/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.test;

import javafx.embed.swing.*;
import org.junit.jupiter.api.extension.*;

public class JavaFXExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        new JFXPanel();
    }

}
