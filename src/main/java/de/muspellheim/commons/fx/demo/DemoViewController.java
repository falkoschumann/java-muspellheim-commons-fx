/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import java.util.regex.*;

import de.muspellheim.commons.fx.control.*;
import de.muspellheim.commons.fx.dialog.*;
import de.muspellheim.commons.fx.validation.*;
import de.muspellheim.commons.time.*;
import de.muspellheim.commons.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class DemoViewController {

    @FXML private DateIntervalPicker dateIntervalPicker;
    @FXML private Label dateIntervalPickerValue;

    @FXML private StatusBar statusBar;
    @FXML private ToggleButton statusBarProcessButton;

    @FXML private TextField validatedText;
    @FXML private Button validButton;
    private ValidationSupport validationSupport;

    @FXML
    void initialize() {
        //
        // Build
        //

        dateIntervalPicker.setValue(LocalDateInterval.lastDays(6));

        validationSupport = new ValidationSupport();
        validationSupport.registerValidator(validatedText, Validator.combine(
            Validator.createEmptyValidator("Number must be specified"),
            Validator.createRegexValidator("Not a number", Pattern.compile("\\d*"))
        ));

        //
        // Bind
        //

        dateIntervalPickerValue.textProperty().bind(dateIntervalPicker.valueProperty().asString());

        statusBarProcessButton.selectedProperty().addListener(o -> {
            if (statusBarProcessButton.isSelected()) {
                statusBar.setProgress(-1);
            } else {
                statusBar.setProgress(0);
            }
        });

        validButton.disableProperty().bind(validationSupport.invalidProperty());
    }

    @FXML
    void showAbout() {
        About about = About.of("Hello World", Version.parse("1.0.0"), 2019, "Copyright (c) 2019 ACME Ltd.");
        Image appIcon = new Image("/de/muspellheim/commons/fx/dialog/app-icon.png");
        AboutDialog dialog = new AboutDialog(about, appIcon);
        dialog.showAndWait();
    }

    @FXML
    void showException() {
        Throwable exception = new IllegalStateException("Answer to the Ultimate Question of Life, the Universe, and Everything: 42");
        ExceptionDialog dialog = new ExceptionDialog(exception);
        dialog.showAndWait();
    }

}
