package com.example.mvc.Controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;



public class AppController {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> mainChart = new LineChart<>(xAxis, yAxis);

    private Queue<XYChart.Data<Number, Number>> dataQueue = new LinkedList<>();

    @FXML
    private Button showChart;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuBar menue;

    @FXML
    void initialize() {
        xAxis.setAnimated(false);
        xAxis.setLabel("Tick");
        yAxis.setAnimated(false);
        yAxis.setLabel("Value");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Values");

        borderPane.setCenter(mainChart);

        dataQueue.add(new XYChart.Data<>(1, 2));
        dataQueue.add(new XYChart.Data<>(2, 3));
        dataQueue.add(new XYChart.Data<>(3, 4));

        mainChart.setAnimated(true);
        mainChart.getData().add(series);

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        if (!dataQueue.isEmpty()) {
                            XYChart.Data<Number, Number> newData = dataQueue.poll();
                            series.getData().add(newData);
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }
 }



