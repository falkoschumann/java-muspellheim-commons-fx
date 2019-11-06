/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import javafx.application.*;
import javafx.stage.*;

/**
 * This demo app shows controls and dialogs from this library.
 */
public class App extends Application {

    /**
     * Start the demo app.
     *
     * @param args no arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DemoWindow window = new DemoWindow(primaryStage);
        window.show();
    }

}
