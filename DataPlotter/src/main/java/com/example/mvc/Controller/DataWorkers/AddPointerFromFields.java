package com.example.mvc.Controller.DataWorkers;

import com.example.mvc.Controller.AppController;
import javafx.event.ActionEvent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import static com.example.mvc.Controller.AppController.dataQueue;

public class AddPointerFromFields{
    public static void addPointFromFieldsMethod(ActionEvent event, TextField xValue, TextField yValue) {
        if (xValue.getText().matches("\\d+") && yValue.getText().matches("\\d+")) {
            double x = Integer.parseInt(xValue.getText());
            double y = Integer.parseInt(yValue.getText());
            dataQueue.add(new XYChart.Data<>(x, y));
        } else System.out.println("nothing to add");
    }
}
