/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.test;

import javafx.embed.swing.*;
import org.junit.jupiter.api.extension.*;

/**
 * This JUnit 5 extension initialize the toolkit.
 * <p>
 * Use {@link javafx.application.Platform#runLater(Runnable)} to run specific
 * code in UI thread and synchronize UI thread with test thread like in this
 * example:
 * <pre>
 * Phaser dialogCreated = new Phaser(1);
 * AtomicReference&lt;AboutDialog&gt; dialog = new AtomicReference&lt;&gt;();
 * Platform.runLater(() -&gt; {
 *     dialog.set(new AboutDialog(about, null));
 *     dialogCreated.arrive();
 * });
 * dialogCreated.awaitAdvance(0);
 * </pre>
 */
public class JavaFXExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        new JFXPanel();
    }

}
