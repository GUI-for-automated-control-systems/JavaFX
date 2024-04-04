package com.example.mvc.Controller.DataWorkers;

import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.example.mvc.Controller.AppController.dataQueue;

public class uploadDAT {
    public static void uploadDATMethod() throws FileNotFoundException {
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
}
