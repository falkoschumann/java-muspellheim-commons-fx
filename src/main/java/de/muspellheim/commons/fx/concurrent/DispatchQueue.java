/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.concurrent;

import java.util.concurrent.*;

import de.muspellheim.commons.concurrent.*;
import javafx.application.*;

/**
 * Provide queues for task in an JavaFX app.
 * <p>
 * The <code>application</code> queue run task in JavaFX application thread. The <code>background</code> queue runs task
 * in non-UI thread.
 */
public final class DispatchQueue {

    private static Executor application = new ApplicationExecutor();
    private static Executor background = new QueuedExecutor("Background Queue Worker");

    private DispatchQueue() {
    }

    /**
     * Obtains executor for the JavaFX application thread.
     *
     * @return the application queue executor
     */
    public static Executor application() {
        return application;
    }

    /**
     * Obtains executor for the background thread.
     *
     * @return the background queue executor
     */
    public static Executor background() {
        return background;
    }

    /**
     * Replace the JavaFX application executor for testing.
     *
     * @param executor an alternative executor.
     */
    public static void setApplicationExecutor(Executor executor) {
        application = executor;
    }

    private static class ApplicationExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            Platform.runLater(command);
        }

    }

}
