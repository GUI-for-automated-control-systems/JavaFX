package com.example.mvc.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

import static com.example.mvc.Controller.ApiSuber.fetchDataFromServer;


public class AppController {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> mainChart = new LineChart<>(xAxis, yAxis);
    private static final String SERVER_URL = "http://localhost:5000/get_data";
    static Queue<XYChart.Data<Number, Number>> dataQueue = new LinkedList<>();

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

        dataQueue.add(new XYChart.Data<>(1, 2, 0));
        dataQueue.add(new XYChart.Data<>(2, 3, 0));
        dataQueue.add(new XYChart.Data<>(3, 4, 0));

        mainChart.setAnimated(false);
        mainChart.getData().add(series);

        Thread updateThread = new Thread(() -> {
            while (true) {

                try {
                    fetchDataFromServer();
                    Thread.sleep(200);
                    Platform.runLater(() -> {
                        if (!dataQueue.isEmpty()) {
                            XYChart.Data<Number, Number> newData = dataQueue.poll();
                            series.getData().add(newData);
                            System.out.println(dataQueue);
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }




 }



