package com.example.mvc.Controller.DataWorkers;

import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;

import static com.example.mvc.Controller.AppController.newSeries;
import static com.example.mvc.Controller.AppController.series;

public class RemovePoints {
    public static void removeAllMethod(ActionEvent event) throws InterruptedException {
        if (!series.getData().isEmpty() || !newSeries.getData().isEmpty()){
            series.getData().clear();
            newSeries.getData().clear();
            Thread.sleep(10);
            series.getData().add(new XYChart.Data<>(0, 0));
            newSeries.getData().add(new XYChart.Data<>(0, 0));
            series.getData().clear();
            newSeries.getData().clear();
        } else System.out.println("nothing to delete");

    }
}
