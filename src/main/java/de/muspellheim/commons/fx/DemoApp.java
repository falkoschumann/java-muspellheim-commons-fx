/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import de.muspellheim.commons.fx.control.*;
import de.muspellheim.commons.fx.dialog.*;
import de.muspellheim.commons.time.*;
import de.muspellheim.commons.util.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * This demo app shows controls and dialogs from this library.
 */
public class DemoApp extends Application {

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
        VBox root = new VBox();
        root.getChildren().addAll(
            createDateIntervalPicker(),
            createStatusBar(),
            createDialogs()
        );

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node createDateIntervalPicker() {
        HBox pane = new HBox(10);
        pane.setPadding(new Insets(5));
        pane.setAlignment(Pos.CENTER_LEFT);

        DateIntervalPicker dateIntervalPicker = new DateIntervalPicker();
        dateIntervalPicker.setValue(LocalDateInterval.lastDays(6));
        pane.getChildren().add(dateIntervalPicker);

        Label valueLabel = new Label();
        pane.getChildren().add(valueLabel);

        TitledPane titledPane = new TitledPane("Date Interval Picker", pane);
        titledPane.setCollapsible(false);

        valueLabel.textProperty().bind(dateIntervalPicker.valueProperty().asString());

        return titledPane;
    }

    private Node createStatusBar() {
        VBox pane = new VBox();
        pane.setSpacing(10);

        StatusBar statusBar = new StatusBar();
        statusBar.setText("Status text");
        pane.getChildren().add(statusBar);

        ToggleButton processButton = new ToggleButton("Process");
        statusBar.getRightItems().add(processButton);

        TitledPane titledPane = new TitledPane("Status Bar", pane);
        titledPane.setCollapsible(false);

        processButton.selectedProperty().addListener(o -> {
            if (processButton.isSelected()) {
                statusBar.setProgress(-1);
            } else {
                statusBar.setProgress(0);
            }
        });

        return titledPane;
    }

    private Node createDialogs() {
        HBox pane = new HBox(10);
        pane.setPadding(new Insets(5));

        pane.getChildren().addAll(
            createAboutDialogButton(),
            createExceptionDialogButton()
        );

        TitledPane titledPane = new TitledPane("Dialogs", pane);
        titledPane.setCollapsible(false);
        return titledPane;
    }

    private Button createAboutDialogButton() {
        Button button = new Button("About");
        button.setOnAction(e -> {
            About about = About.of("Hello World", Version.parse("1.0.0"), 2019, "Copyright (c) 2019 ACME Ltd.");
            Image appIcon = new Image("/de/muspellheim/commons/fx/dialog/app-icon.png");
            AboutDialog dialog = new AboutDialog(about, appIcon);
            dialog.showAndWait();
        });
        return button;
    }

    private Button createExceptionDialogButton() {
        Button button = new Button("Exception");
        button.setOnAction(e -> {
            Throwable exception = new IllegalStateException("Answer to the Ultimate Question of Life, the Universe, and Everything: 42");
            ExceptionDialog dialog = new ExceptionDialog(exception);
            dialog.showAndWait();
        });
        return button;
    }

}
