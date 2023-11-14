package com.example.charts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Button bnt3;

    @FXML
    private Button btn1;


    @FXML
    private Button btn2;

    @FXML
    private Button btn4;

    @FXML
    private BorderPane firstView;

    @FXML
    private Pane fourthView;

    @FXML
    private Pane secondView;

    @FXML
    private Pane thirdView;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btn1){
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Field 1");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Field 2");

            BarChart<String, Integer> barChart =new BarChart(xAxis, yAxis);

            XYChart.Series data = new XYChart.Series();
            data.setName("Production");

            data.getData().add(new XYChart.Data<>("Example 1", 1));
            data.getData().add(new XYChart.Data<>("Example 2", 3));
            data.getData().add(new XYChart.Data<>("Example 3", 4));
            data.getData().add(new XYChart.Data<>("Example 32", 5));
            data.getData().add(new XYChart.Data<>("Example 12", 1));
            data.getData().add(new XYChart.Data<>("Example 55", 9));
            data.getData().add(new XYChart.Data<>("Example 312", 6));
            data.getData().add(new XYChart.Data<>("Example 311", 3));
            data.getData().add(new XYChart.Data<>("Example 5", 2));

            XYChart.Series data2 = new XYChart.Series();
            data2.setName("Concurrent");

            data2.getData().add(new XYChart.Data<>("Example 1", 1));
            data2.getData().add(new XYChart.Data<>("Example 2", 3));
            data2.getData().add(new XYChart.Data<>("Example 3", 4));
            data2.getData().add(new XYChart.Data<>("Example 32", 5));
            data2.getData().add(new XYChart.Data<>("Example 12", 1));
            data2.getData().add(new XYChart.Data<>("Example 55", 9));
            data2.getData().add(new XYChart.Data<>("Example 312", 6));
            data2.getData().add(new XYChart.Data<>("Example 311", 3));
            data2.getData().add(new XYChart.Data<>("Example 5", 2));

            barChart.getData().add(data);
            barChart.getData().add(data2);
            firstView.setCenter(barChart);
            firstView.toFront();

        } else if (event.getSource() == btn2){
            secondView.toFront();
        } else if (event.getSource() == bnt3){
            thirdView.toFront();
        } else if (event.getSource() == btn4){
            fourthView.toFront();
        }
    }

    @FXML
    void initialize() {

    }
}