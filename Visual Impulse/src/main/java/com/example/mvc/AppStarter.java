package com.example.mvc;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("View/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Visual Impulse");
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        System.out.println("Hello, world!");
    }



    public static void main(String[] args) {
        launch();
    }
}