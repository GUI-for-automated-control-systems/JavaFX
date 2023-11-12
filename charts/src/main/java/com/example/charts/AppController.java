package com.example.charts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bnt3;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn4;

    @FXML
    private Pane firstView;

    @FXML
    private Pane fourthView;

    @FXML
    private Pane secondView;

    @FXML
    private Pane thirdView;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btn1){
            firstView.toFront();
        } else if (event.getSource() == btn2){
            secondView.toFront();
        } else if (event.getSource() == bnt3){
            thirdView.toFront();
        } else if (event.getSource() == btn4){
            fourthView.toFront();
        }
    }

    @FXML
    void initialize() {


    }
}