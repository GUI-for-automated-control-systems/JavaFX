package com.example.charts;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import java.sql.*;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class AppController {
    private AtomicInteger tick = new AtomicInteger(0);
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    public Text message;

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
    private BorderPane fourthView;

    @FXML
    private BorderPane secondView;

    @FXML
    private Pane thirdView;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btn1){
            String url = "jdbc:mysql://localhost/student_list";
            Connection connection = null;
            Statement statement = null;
            Text message = new Text();
            try{

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, "root", "");
                statement = connection.createStatement();

                String query = "SELECT x, y FROM data;";

                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<String> x = new ArrayList<String>();
                ArrayList<Integer> y = new ArrayList<Integer>();



                while (resultSet.next()){
                    x.add(resultSet.getString(1));
                    y.add(resultSet.getInt(2));

                }
                resultSet.close();

                CategoryAxis aAxis = new CategoryAxis();
                aAxis.setLabel("x");

                NumberAxis bAxis = new NumberAxis();
                bAxis.setLabel("y");

                LineChart chart = new LineChart(aAxis, bAxis);

                chart.setTitle("График данных из mysql");


                XYChart.Series dataSeries = new XYChart.Series();
                dataSeries.setName("Chart");

                for (int i = 0; i < x.size(); i++){
                    dataSeries.getData().add(new XYChart.Data<>(x.get(i), y.get(i)));
                }
                chart.getData().add(dataSeries);

                message.setText("ok: true");
                VBox vbox = new VBox();
                vbox.getChildren().add(chart);
                firstView.setCenter(vbox);
                firstView.setBottom(message);


            } catch (ClassNotFoundException | SQLException | RuntimeException e) {
                message.setText("ok: " + e);
                firstView.setBottom(message);
            }
            firstView.toFront();
        } else if (event.getSource() == btn2){

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();

            xAxis.setAnimated(false);
            xAxis.setLabel("Tick");

            yAxis.setAnimated(false);
            yAxis.setLabel("Value");

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Values");

            AreaChart<Number, Number> chart = new AreaChart<>(xAxis, yAxis);
            chart.setAnimated(false);
            chart.getData().add(series);

            chart.setTitle("Dummy data");

            Thread updateThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(50);
                        Platform.runLater(() -> series.getData().add(new XYChart.Data<>(tick.incrementAndGet(), (int) (Math.random() * 100))));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            updateThread.setDaemon(true);
            updateThread.start();
            secondView.setCenter(chart);
            secondView.toFront();
        } else if (event.getSource() == bnt3){
            thirdView.toFront();
        } else if (event.getSource() == btn4){
            Text text = new Text("todo page 4");
            firstView.setBottom(text);
            fourthView.toFront();
        }
    }

    @FXML
    void initialize() {

    }
}