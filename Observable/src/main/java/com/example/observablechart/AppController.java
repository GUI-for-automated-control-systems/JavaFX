package com.example.observablechart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AppController {
    private SimpleStringProperty chars= new SimpleStringProperty("A");

    @FXML
    private Text charsSet;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField input;

    @FXML
    private TextField output;

    @FXML
    private Text textRealtime;

    @FXML
    private ListView<String> list;

    ObservableList<String> listik = FXCollections.observableArrayList();



    @FXML
    void initialize() {
        MyTask task = new MyTask();
        Timer timer = new Timer(true);
        timer.schedule(task, 1000, 1000);
        textRealtime.textProperty().bind(chars);

        input.textProperty().bindBidirectional(output.textProperty());
        list.setItems(listik);
    }

    class MyTask extends TimerTask{
        @Override
        public void run() {
            final int ch = chars.get().charAt(0);
            Platform.runLater(()->{
                listik.add(String.valueOf((char) (ch + 1)));
                chars.set(String.valueOf((char) (ch + 1)));
            });

        }
    }
}
