/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.concurrent;

import de.muspellheim.commons.concurrent.QueuedExecutor;
import java.util.concurrent.Executor;
import javafx.application.Platform;

/**
 * Provide queues for task in an JavaFX app.
 *
 * <p>The <code>application</code> queue run task in JavaFX application thread. The <code>background
 * </code> queue runs task in non-UI thread.
 *
 * <p>In <em>testing mode</em> all queues run in current thread, so you can test synchronously.
 */
public final class DispatchQueue {

  private static Executor application = new ApplicationExecutor();
  private static Executor background = new QueuedExecutor("Background Queue Worker");
  private static Executor test = new TestingExecutor();

  private static boolean testing;

  private DispatchQueue() {}

  /**
   * Check if queue runs in testing mode.
   *
   * @return {@code true} if queue runs in testing mode, {@code false} otherwise
   */
  public static boolean isTesting() {
    return testing;
  }

  /** Activate or deactivate testing mode of queue runs. */
  public static void setTesting(boolean value) {
    testing = value;
  }

  /**
   * Activate testing mode of queue runs.
   *
   * @deprecated use {@link DispatchQueue#setTesting(boolean)}
   */
  @Deprecated
  public static void setTesting() {
    testing = true;
  }

  /**
   * Deactivate testing mode of queue runs.
   *
   * @deprecated use {@link DispatchQueue#setTesting(boolean)}
   */
  @Deprecated
  public static void unsetTesting() {
    testing = false;
  }

  /**
   * Obtains executor for the JavaFX application thread.
   *
   * @return the application queue executor
   * @deprecated use {@link DispatchQueue#application(Runnable)}
   */
  @Deprecated
  public static Executor application() {
    return testing ? test : application;
  }

  /** Execute command in JavaFX application thread. */
  public static void application(Runnable command) {
    if (testing) {
      test.execute(command);
    } else {
      application.execute(command);
    }
  }

  /**
   * Obtains executor for the background thread.
   *
   * @return the background queue executor
   * @deprecated use {@link DispatchQueue#background(Runnable)}
   */
  @Deprecated
  public static Executor background() {
    return testing ? test : background;
  }

  /** Execute comman in background thread. */
  public static void background(Runnable command) {
    if (testing) {
      test.execute(command);
    } else {
      background.execute(command);
    }
  }

  /**
   * Replace the JavaFX application executor for testing.
   *
   * @param executor an alternative executor.
   * @deprecated use {@link DispatchQueue#setTesting(boolean)}
   */
  @Deprecated
  public static void setApplicationExecutor(Executor executor) {
    application = executor;
  }

  private static class ApplicationExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
      if (Platform.isFxApplicationThread()) {
        command.run();
      } else {
        Platform.runLater(command);
      }
    }
  }

  private static class TestingExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
      command.run();
    }
  }
}
