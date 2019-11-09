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
import org.controlsfx.validation.*;

public class DemoViewController {

    @FXML private DateIntervalPicker dateIntervalPicker;
    @FXML private Label dateIntervalPickerValue;

    @FXML private TextField validatedText;
    @FXML private Button validButton;
    private HintValidationSupport validationSupport;

    @FXML
    void initialize() {
        //
        // Build
        //

        dateIntervalPicker.setValue(LocalDateInterval.lastDays(6));

        validationSupport = new HintValidationSupport();
        validationSupport.registerValidator(validatedText, Validator.combine(
            Validator.createEmptyValidator("Number must be specified"),
            Validator.createRegexValidator("Not a number", Pattern.compile("\\d*"), Severity.ERROR)
        ));

        //
        // Bind
        //

        dateIntervalPickerValue.textProperty().bind(dateIntervalPicker.valueProperty().asString());

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
        Throwable exception = new IllegalStateException("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod "
            + "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores "
            + "et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit "
            + "amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam "
            + "voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
            + "Lorem ipsum dolor sit amet.");
        CustomExceptionDialog dialog = new CustomExceptionDialog(exception);
        dialog.showAndWait();
    }

}
