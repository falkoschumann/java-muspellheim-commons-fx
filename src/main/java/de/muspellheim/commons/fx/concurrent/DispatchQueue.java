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
 * <p>
 * In <em>testing mode</em> all queues run in current thread, so you can test synchronously.
 */
public final class DispatchQueue {

    private static Executor application = new ApplicationExecutor();
    private static Executor background = new QueuedExecutor("Background Queue Worker");
    private static Executor test = new TestingExecutor();

    private static boolean testing;

    private DispatchQueue() {
    }

    /**
     * Check if queue runs in testing mode.
     *
     * @return {@code true} if queue runs in testing mode, {@code false} otherwise
     */
    public static boolean isTesting() {
        return testing;
    }

    /**
     * Activate testing mode of queue runs.
     */
    public static void setTesting() {
        testing = true;
    }

    /**
     * Deactivate testing mode of queue runs.
     */
    public static void unsetTesting() {
        testing = false;
    }

    /**
     * Obtains executor for the JavaFX application thread.
     *
     * @return the application queue executor
     */
    public static Executor application() {
        return testing ? test : application;
    }

    /**
     * Obtains executor for the background thread.
     *
     * @return the background queue executor
     */
    public static Executor background() {
        return testing ? test : background;
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

    private static class TestingExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            command.run();
        }

    }

}
