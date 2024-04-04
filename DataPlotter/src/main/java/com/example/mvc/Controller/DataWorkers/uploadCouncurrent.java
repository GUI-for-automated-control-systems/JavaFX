package com.example.mvc.Controller.DataWorkers;

import com.example.mvc.Controller.ShowCoordinatesNode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.example.mvc.Controller.AppController.mainChart;
import static com.example.mvc.Controller.AppController.newSeries;

public class uploadCouncurrent {
    public static void uploadCouncurrenMethod(ActionEvent event) throws FileNotFoundException {
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
                try {
                    Thread.sleep(10);
                    mainChart.getData().add(newSeries);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }
}
