package com.example.mvc.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AppController {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final StackedAreaChart<Number, Number> mainChart = new StackedAreaChart <>(xAxis, yAxis);
    static Queue<XYChart.Data<Number, Number>> dataQueue = new LinkedList<>();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();


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

        mainChart.setOnMousePressed(event -> {
            if (event.getClickCount() == 2) {
                mainChart.setScaleX(mainChart.getScaleX() * 1.9);
                mainChart.setScaleY(mainChart.getScaleX() * 0.9);
            }
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
        double x = Integer.parseInt(xValue.getText());
        double y = Integer.parseInt(yValue.getText());
        dataQueue.add(new XYChart.Data<>(x, y));

    }
    public void removeAll(ActionEvent event) {
        series.getData().clear();
        System.out.println(dataQueue);
    }

    public void uploadDAT() throws FileNotFoundException {
        FileChooser inputFile = new FileChooser();
        inputFile.setInitialDirectory(new File("C:\\Users\\zorin\\OneDrive\\Рабочий стол"));
        File selectFile = inputFile.showOpenDialog(new Stage());

        if (selectFile != null) {
            Scanner scanner = new Scanner(selectFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] values = line.split("\\s+");

                if (values.length == 2) {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);

                    System.out.println("x: " + x + ", y: " + y);
                    dataQueue.add(new XYChart.Data<>(x, y));

                } else {
                    System.out.println("Некорректный формат строки: " + line);
                }
            }

            scanner.close();
        }
    }


    public void saveDAT(ActionEvent event) {
        FileChooser ouputFile = new FileChooser();
        ouputFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add all", "dat"));
        ouputFile.setTitle("Save file");
        File saveDUMP = ouputFile.showSaveDialog(new Stage());
        if (saveDUMP != null){
            try {
                PrintStream printStream = new PrintStream(saveDUMP);
                printStream.println(series.getData());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}



