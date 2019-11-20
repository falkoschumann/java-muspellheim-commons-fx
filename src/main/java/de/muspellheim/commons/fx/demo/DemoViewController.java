/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import java.time.*;
import java.time.format.*;
import java.util.regex.*;

import de.muspellheim.commons.fx.chart.*;
import de.muspellheim.commons.fx.control.*;
import de.muspellheim.commons.fx.dialog.*;
import de.muspellheim.commons.fx.validation.*;
import de.muspellheim.commons.time.*;
import de.muspellheim.commons.util.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import org.controlsfx.validation.*;

public class DemoViewController {

    @FXML private DateIntervalPicker dateIntervalPicker;
    @FXML private Label dateIntervalPickerValue;

    @FXML private TextField validatedText;
    @FXML private Button validButton;
    private final HintValidationSupport validationSupport = new HintValidationSupport();

    private final ReadOnlyListWrapper<DateTimes> dateTimes = new ReadOnlyListWrapper<>(this, "dateTimes");

    @FXML private XYChart<LocalDate, Integer> chart1;

    @FXML private XYChart<LocalDateTime, Long> chart2;

    @FXML
    void initialize() {
        //
        // Build
        //

        dateIntervalPicker.setValue(LocalDateInterval.lastDays(6));

        setDateTimes(FXCollections.observableArrayList(
            new DateTimes(LocalDateTime.now())
        ));

        validationSupport.registerValidator(validatedText, Validator.combine(
            Validator.createEmptyValidator("Number must be specified"),
            Validator.createRegexValidator("Not a number", Pattern.compile("\\d*"), Severity.ERROR)
        ));

        //
        // Bind
        //

        dateIntervalPickerValue.textProperty().bind(dateIntervalPicker.valueProperty().asString());

        validButton.disableProperty().bind(validationSupport.invalidProperty());

        chart1.dataProperty().addListener(o -> updateChartTooltips1());
        applyChartData1();

        chart2.dataProperty().addListener(o -> updateChartTooltips2());
        applyChartData2();
    }

    public final ObservableList<DateTimes> getDateTimes() {
        return dateTimes.get();
    }

    private void setDateTimes(ObservableList<DateTimes> value) {
        dateTimes.set(value);
    }

    public final ReadOnlyListProperty<DateTimes> dateTimesProperty() {
        return dateTimes.getReadOnlyProperty();
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

    private void applyChartData1() {
        XYChart.Series<LocalDate, Integer> series1 = new XYChart.Series<>();
        series1.setName("Series 1");
        series1.getData().add(new XYChart.Data<>(LocalDate.of(2019, 10, 20), 41));
        series1.getData().add(new XYChart.Data<>(LocalDate.of(2019, 10, 24), 23));
        series1.getData().add(new XYChart.Data<>(LocalDate.of(2019, 11, 5), 65));
        series1.getData().add(new XYChart.Data<>(LocalDate.of(2019, 11, 12), 42));
        series1.getData().add(new XYChart.Data<>(LocalDate.of(2019, 11, 17), 78));
        chart1.getData().add(series1);
    }

    private void updateChartTooltips1() {
        chart1.getData().stream().flatMap(s -> s.getData().stream()).forEach(d -> {
            new DataTooltipBuilder()
                .applyTitle("Hello Data")
                .addData("Date:", d.getXValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                .addData("Integer:", d.getYValue())
                .install(d.getNode());
        });
    }

    private void applyChartData2() {
        XYChart.Series<LocalDateTime, Long> seriesA = new XYChart.Series<>();
        seriesA.setName("Series A");
        seriesA.getData().add(new XYChart.Data<>(LocalDateTime.of(2019, 11, 16, 21, 34), 4L));
        seriesA.getData().add(new XYChart.Data<>(LocalDateTime.of(2019, 11, 16, 23, 45), 2L));
        seriesA.getData().add(new XYChart.Data<>(LocalDateTime.of(2019, 11, 17, 3, 12), 6L));
        seriesA.getData().add(new XYChart.Data<>(LocalDateTime.of(2019, 11, 17, 6, 30), 4L));
        seriesA.getData().add(new XYChart.Data<>(LocalDateTime.of(2019, 11, 17, 10, 1), 7L));
        chart2.getData().add(seriesA);
    }

    private void updateChartTooltips2() {
        chart2.getData().stream().flatMap(s -> s.getData().stream()).forEach(d -> new DataTooltipBuilder()
            .applyTitle("Hello Data")
            .addData("Date time:", d.getXValue().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
            .addData("Long:", d.getYValue())
            .install(d.getNode()));
    }

}
