package com.example.mvc.Controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import static com.example.mvc.Controller.DataWorkers.AddPointerFromFields.addPointFromFieldsMethod;
import static com.example.mvc.Controller.DataWorkers.RemovePoints.removeAllMethod;
import static com.example.mvc.Controller.DataWorkers.SaveData.saveDATMethod;
import static com.example.mvc.Controller.DataWorkers.uploadCouncurrent.uploadCouncurrenMethod;
import static com.example.mvc.Controller.DataWorkers.uploadDAT.uploadDATMethod;


public class AppController {
    public static NumberAxis xAxis = new NumberAxis();
    public static NumberAxis yAxis = new NumberAxis();
    public static AreaChart<Number, Number> mainChart = new AreaChart<>(xAxis, yAxis);
    public static Queue<XYChart.Data<Number, Number>> dataQueue = new LinkedList<>();
    public static XYChart.Series<Number, Number> series = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();



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
    private Button addPoint;

    @FXML
    private TextField xValue;

    @FXML
    private TextField yValue;


    @FXML
    void initialize() {
        xAxis.setAnimated(false);
        xAxis.setLabel("Tick");
        yAxis.setAnimated(false);
        yAxis.setLabel("Value");


        series.setName("Values");

        mainChart.setOnScroll(event -> {
            event.consume();

            if (event.getDeltaY() == 0) {
                return;
            }

            double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 1 / 1.1;

            mainChart.setScaleX(mainChart.getScaleX() * scaleFactor);
            mainChart.setScaleY(mainChart.getScaleY() * scaleFactor);
        });

        borderPane.setCenter(mainChart);

        mainChart.setAnimated(false);
        mainChart.getData().add(series);

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);

                    Platform.runLater(() -> {
                        if (!dataQueue.isEmpty()) {
                            XYChart.Data<Number, Number> newData = dataQueue.poll();
                            newData.setNode(new ShowCoordinatesNode(newData.getXValue(),newData.getYValue()));
                            series.getData().add(newData);
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

    public void addPointFromFields(ActionEvent event) {
        addPointFromFieldsMethod(event, xValue, yValue);
    }
    public void removeAll(ActionEvent event) throws InterruptedException {
        removeAllMethod(event);
    }

    public void uploadDAT() throws FileNotFoundException {
        uploadDATMethod();
    }


    public void saveDAT(ActionEvent event) {
        saveDATMethod(event);
    }

    public void uploadCouncurrent(ActionEvent event) throws FileNotFoundException {
        uploadCouncurrenMethod(event);
    }
}



