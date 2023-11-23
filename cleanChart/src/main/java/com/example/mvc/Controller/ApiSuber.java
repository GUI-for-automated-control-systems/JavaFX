package com.example.mvc.Controller;

import javafx.scene.chart.XYChart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.mvc.Controller.AppController.dataQueue;

public class ApiSuber {
    private static final String SERVER_URL = "http://localhost:5000/get_data";
    protected static void fetchDataFromServer() throws Exception {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;

            // Читаем ответ от сервера
            if ((line = reader.readLine()) != null && !line.isEmpty()) {
                // Разбиваем строку по пробелу и парсим числа
                String[] values = line.split(" ");
                if (values.length == 2) {
                    int x = Integer.parseInt(values[0]);
                    int y = Integer.parseInt(values[1]);
                    // Добавляем данные в очередь
                    dataQueue.add(new XYChart.Data<>(x, y));
                } else {
                    dataQueue.add(new XYChart.Data<>(0, 0));
                    System.err.println("Некорректный формат данных от сервера.");
                }
            } else {

                System.err.println("С сервера не получено данных.");
            }
        } finally {
            connection.disconnect();
        }
    }
}
