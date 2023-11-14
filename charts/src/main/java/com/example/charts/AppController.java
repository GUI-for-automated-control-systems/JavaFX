package com.example.charts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.sql.*;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
            String url = "jdbc:mysql://localhost/student_list";
            Connection connection = null;
            Statement statement = null;

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, "root", "");
                statement = connection.createStatement();

                String query = "SELECT FirstName, id FROM student_list;";

                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<String> emp_no = new ArrayList<String>();
                ArrayList<Integer> birth_date = new ArrayList<Integer>();



                while (resultSet.next()){
                    emp_no.add(resultSet.getString(1));
                    birth_date.add(resultSet.getInt(2));

                }
                resultSet.close();

                CategoryAxis aAxis = new CategoryAxis();
                aAxis.setLabel("FirstName");

                NumberAxis bAxis = new NumberAxis();
                bAxis.setLabel("id");

                BarChart barChart = new BarChart(aAxis, bAxis);

                XYChart.Series dataSeries = new XYChart.Series();
                dataSeries.setName("Emp table");

                for (int i = 0; i < emp_no.size(); i++){
                    dataSeries.getData().add(new XYChart.Data<>(emp_no.get(i), birth_date.get(i)));
                }
                barChart.getData().add(dataSeries);
                firstView.setCenter(barChart);


            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

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