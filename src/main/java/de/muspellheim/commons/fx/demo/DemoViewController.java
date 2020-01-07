/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.demo;

import de.muspellheim.commons.fx.chart.DataTooltipBuilder;
import de.muspellheim.commons.fx.chart.ScalableAxis;
import de.muspellheim.commons.fx.chart.SimpleParetoChart;
import de.muspellheim.commons.fx.control.AutocompleteTextField;
import de.muspellheim.commons.fx.control.DateIntervalPicker;
import de.muspellheim.commons.fx.control.StatusBar;
import de.muspellheim.commons.fx.dialog.AboutDialog;
import de.muspellheim.commons.fx.dialog.ExceptionDialog;
import de.muspellheim.commons.fx.validation.Hint;
import de.muspellheim.commons.fx.validation.Severity;
import de.muspellheim.commons.time.LocalDateInterval;
import de.muspellheim.commons.util.About;
import de.muspellheim.commons.util.Version;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;

public class DemoViewController {

  @FXML private DateIntervalPicker dateIntervalPicker;
  @FXML private Label dateIntervalPickerValue;

  @FXML private TextField validatedText;
  @FXML private Button validButton;

  private final ReadOnlyListWrapper<DateTimes> dateTimes =
      new ReadOnlyListWrapper<>(this, "dateTimes");

  @FXML private XYChart<LocalDate, Integer> chart1;

  @FXML private XYChart<Double, Double> chart2;
  @FXML private NumberAxis scalableX2;
  @FXML private NumberAxis scalableY2;

  @FXML private SimpleParetoChart paretoChart;

  @FXML private ToggleButton progressToggle;
  @FXML private StatusBar statusBar;

  @FXML private AutocompleteTextField<Integer> autoCompleteTextField;

  @FXML
  void initialize() {
    //
    // Build
    //

    dateIntervalPicker.setValue(LocalDateInterval.lastDays(6));

    ScalableAxis.install(chart2, scalableX2);
    ScalableAxis.install(chart2, scalableY2);
    setDateTimes(FXCollections.observableArrayList(new DateTimes(LocalDateTime.now())));

    // TODO Extract validator API
    // TODO Higher order function to combine validators for a control
    Hint hint = new Hint(validatedText);
    Consumer<String> validator =
        value -> {
          if (value.isEmpty()) {
            hint.setText("Number must be specified");
            hint.setSeverity(Severity.ERROR);
          } else if (!Pattern.matches("\\d*", value)) {
            hint.setText("Not a number");
            hint.setSeverity(Severity.ERROR);
          } else {
            hint.setText("");
            hint.setSeverity(Severity.OK);
          }
        };
    validatedText
        .textProperty()
        .addListener((observable, oldValue, newValue) -> validator.accept(newValue));
    validator.accept(validatedText.textProperty().get());

    List<Integer> suggestions = Arrays.asList(1, 2, 3, 11, 12, 13, 21, 22, 23, 31, 32, 33);
    autoCompleteTextField.setSuggestionProvider(
        request ->
            suggestions.stream()
                .filter(s -> !request.isEmpty() && s.toString().contains(request))
                .collect(Collectors.toList()));

    //
    // Bind
    //

    dateIntervalPickerValue.textProperty().bind(dateIntervalPicker.valueProperty().asString());

    // TODO Higher order function to combine validators for a form
    validButton.disableProperty().bind(hint.textProperty().isNotEmpty());

    applyChartData1();
    applyChartData2();
    applyParetoChartData();

    progressToggle
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                statusBar.setProgress(-1);
              } else {
                statusBar.setProgress(0);
              }
            });

    autoCompleteTextField
        .onSuggestionSelected()
        .addHandler(
            it -> {
              Alert dlg = new Alert(AlertType.INFORMATION, "You selected " + it + ".");
              dlg.showAndWait();
            });
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
    About about =
        About.of("Hello World", Version.parse("1.0.0"), 2019, "Copyright (c) 2019 ACME Ltd.");
    Image appIcon = new Image("/de/muspellheim/commons/fx/dialog/app-icon.png");
    AboutDialog dialog = new AboutDialog(about, appIcon);
    dialog.showAndWait();
  }

  @FXML
  void showException() {
    Throwable exception =
        new IllegalStateException(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor"
                + " invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero"
                + " eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no"
                + " sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit "
                + "amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut"
                + " labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et"
                + " accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea"
                + " takimata sanctus est Lorem ipsum dolor sit amet.");
    ExceptionDialog dialog = new ExceptionDialog(exception);
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
    chart1.getData().stream()
        .flatMap(s -> s.getData().stream())
        .forEach(
            d -> {
              new DataTooltipBuilder()
                  .applyTitle("Hello Data")
                  .addData(
                      "Date:",
                      d.getXValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                  .addData("Integer:", d.getYValue())
                  .install(d.getNode());
            });
  }

  private void applyChartData2() {
    XYChart.Series<Double, Double> seriesA = new XYChart.Series<>();
    seriesA.setName("Series A");
    seriesA.getData().add(new XYChart.Data<>(0.2, 41.0));
    seriesA.getData().add(new XYChart.Data<>(0.6, 23.0));
    seriesA.getData().add(new XYChart.Data<>(0.5, 65.0));
    seriesA.getData().add(new XYChart.Data<>(0.7, 42.0));
    seriesA.getData().add(new XYChart.Data<>(0.4, 78.0));
    chart2.getData().add(seriesA);
  }

  private void applyParetoChartData() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Causes");
    series.getData().add(new XYChart.Data<>("Damaged radiator core", 31 / 71.0 * 100.0));
    series.getData().add(new XYChart.Data<>("Faulty fans", 20 / 71.0 * 100.0));
    series.getData().add(new XYChart.Data<>("Faulty thermostat", 8 / 71.0 * 100.0));
    series.getData().add(new XYChart.Data<>("Loose fan belt", 5 / 71.0 * 100.0));
    series.getData().add(new XYChart.Data<>("Damaged fins", 4 / 71.0 * 100.0));
    series.getData().add(new XYChart.Data<>("Coolant leakage", 3 / 71.0 * 100.0));
    paretoChart.getData().add(series);
    paretoChart.getData().stream()
        .flatMap(s -> s.getData().stream())
        .forEach(
            d ->
                new DataTooltipBuilder()
                    .addData("Cause:", d.getXValue())
                    .addData("Frequency:", d.getYValue())
                    .install(d.getNode()));
  }
}
