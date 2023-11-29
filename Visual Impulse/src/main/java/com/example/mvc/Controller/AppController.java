package com.example.mvc.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.example.mvc.Model.SmoothedChart;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AppController {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final AreaChart<Number, Number> mainChart = new AreaChart<>(xAxis, yAxis);
    static Queue<XYChart.Data<Number, Number>> dataQueue = new LinkedList<>();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();



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
        if (!xValue.getText().isEmpty()  && !yValue.getText().isEmpty() ){
            double x = Integer.parseInt(xValue.getText());
            double y = Integer.parseInt(yValue.getText());
            dataQueue.add(new XYChart.Data<>(x, y));
        } else System.out.println("nothing to add");


    }
    public void removeAll(ActionEvent event) throws InterruptedException {
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

    public void uploadDAT() throws FileNotFoundException {
        FileChooser inputFile = new FileChooser();
        inputFile.setInitialDirectory(new File("C:\\Users\\zorin\\OneDrive\\Рабочий стол\\Data"));
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

    public void uploadCouncurrent(ActionEvent event) throws FileNotFoundException {
        FileChooser inputFile = new FileChooser();
        inputFile.setInitialDirectory(new File("C:\\Users\\zorin\\OneDrive\\Рабочий стол"));
        File selectFile = inputFile.showOpenDialog(new Stage());

        if (selectFile != null) {
            newSeries.setName("Values");
            Scanner scanner = new Scanner(selectFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] values = line.split("\\s+");

                if (values.length == 2) {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);

                    System.out.println("x: " + x + ", y: " + y);
                    newSeries.getData().add(new XYChart.Data<>(x, y));
                    newSeries.setNode(new ShowCoordinatesNode(x,y));
                } else {
                    System.out.println("Некорректный формат строки: " + line);
                }
            }

            scanner.close();

            // Добавить новый график к mainChart
            Platform.runLater(() -> {
                mainChart.getData().add(newSeries);
            });
        }
    }

}



