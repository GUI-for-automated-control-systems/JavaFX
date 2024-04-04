package com.crud.crud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("student.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.getIcons().add(new Image("https://github.com/NeZorinEgor/JavaFX/blob/main/sample/src/main/resources/sryles/image/icon.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println(new App());
    }
}