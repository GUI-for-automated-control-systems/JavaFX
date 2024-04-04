package com.example.realtimecharts;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client extends Application {
    private static final int PORT = 19000;
    private static final String HOST = "localhost";

    private BarChart<String, Number> barChart;
    private XYChart.Series<String, Number> dataSeries;
    private TextArea messageLog;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // BarChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        dataSeries = new XYChart.Series<>();
        barChart.getData().add(dataSeries);
        barChart.setTitle("Socket Chart");
        root.setCenter(barChart);

        // Message Log
        messageLog = new TextArea();
        messageLog.setEditable(false);
        messageLog.setWrapText(true);
        messageLog.setStyle("-fx-font-size: 12px; -fx-background-color: #f8f8f8;");
        ScrollPane scrollPane = new ScrollPane(messageLog);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setBottom(scrollPane);

        // Input Field
        TextField inputField = new TextField();
        inputField.setOnAction(event -> sendMessage(inputField.getText()));
        inputField.setStyle("-fx-font-size: 12px;");
        root.setTop(inputField);

        Scene scene = new Scene(root, 720, 480);
        applyStyles(scene);

        primaryStage.setTitle("Styled Chart Client");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();

        connectToServer();
    }

    private void applyStyles(Scene scene) {
        scene.getRoot().setStyle("-fx-font-family: 'Arial', sans-serif;");
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(HOST, PORT);
            Thread readThread = new Thread(() -> handleServerRead(socket));
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServerRead(Socket socket) {
        try (InputStream in = socket.getInputStream()) {
            while (true) {
                byte[] buf = new byte[32 * 1024];
                int readBytes = in.read(buf);

                if (readBytes == -1) {
                    break;
                }

                String serverMessage = new String(buf, 0, readBytes);
                Platform.runLater(() -> {
                    updateChart(serverMessage);
                    logMessage("Server: " + serverMessage);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateChart(String serverMessage) {
        try {
            double numericValue = Double.parseDouble(serverMessage);
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(dataSeries.getData().size() + 1), numericValue));
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value received from the server: " + serverMessage);
        }
    }

    private void logMessage(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        messageLog.appendText(timestamp + " - " + message + "\n");
    }

    private void sendMessage(String message) {
        logMessage("Client: " + message);

        try (Socket socket = new Socket(HOST, PORT)) {
            socket.getOutputStream().write(message.getBytes());
            socket.getOutputStream().flush();

            if (message.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
