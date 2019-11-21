/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx.chart;

import java.util.*;

import javafx.collections.*;
import javafx.scene.chart.*;
import javafx.scene.shape.*;

public class SimpleParetoChart extends BarChart<String, Number> {

    private CategoryAxis causeAxis;
    private NumberAxis frequencyAxis;
    private Line line80;
    private Polyline paretoCurve;

    public SimpleParetoChart() {
        super(createCauseAxis(), createFrequencyAxis());
        initialize();
    }

    public SimpleParetoChart(ObservableList<Series<String, Number>> data) {
        super(createCauseAxis(), createFrequencyAxis(), data);
        initialize();
    }

    public SimpleParetoChart(ObservableList<Series<String, Number>> data, double categoryGap) {
        super(createCauseAxis(), createFrequencyAxis(), data, categoryGap);
        initialize();
    }

    private void initialize() {
        setLegendVisible(false);

        causeAxis = (CategoryAxis) getXAxis();
        frequencyAxis = (NumberAxis) getYAxis();

        line80 = createLine80();
        paretoCurve = createParetoCurve();
    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();

        double y80 = frequencyAxis.getDisplayPosition(80);
        line80.setStartY(y80);
        line80.setEndY(y80);

        if (getData().isEmpty()) {
            paretoCurve.getPoints().clear();
            return;
        }

        List<Double> paretoCurvePoints = new ArrayList<>();
        List<Data<String, Number>> data = getData().get(0).getData();
        double y = frequencyAxis.getZeroPosition();
        for (Data<String, Number> e : data) {
            double x = causeAxis.getDisplayPosition(e.getXValue());
            paretoCurvePoints.add(x);
            y += e.getYValue().doubleValue() * frequencyAxis.getScale();
            paretoCurvePoints.add(y);
        }
        paretoCurve.getPoints().setAll(paretoCurvePoints);
    }

    private static CategoryAxis createCauseAxis() {
        return new CategoryAxis();
    }

    private static NumberAxis createFrequencyAxis() {
        NumberAxis axis = new NumberAxis();
        axis.setAutoRanging(false);
        axis.setLowerBound(0);
        axis.setUpperBound(100);
        axis.setTickUnit(10);
        axis.setMinorTickCount(1);
        return axis;
    }

    private Line createLine80() {
        Line line = new Line();
        line.setStyle("-fx-stroke-dash-array: 5 5;");
        getPlotChildren().add(line);

        line.startXProperty().bind(causeAxis.layoutXProperty().subtract(causeAxis.categorySpacingProperty()));
        line.endXProperty().bind(causeAxis.layoutXProperty().add(causeAxis.widthProperty()));

        return line;
    }

    private Polyline createParetoCurve() {
        Polyline line = new Polyline();
        line.setStyle("-fx-stroke: royalblue; -fx-stroke-width: 2px");
        getPlotChildren().add(line);
        return line;
    }

}
