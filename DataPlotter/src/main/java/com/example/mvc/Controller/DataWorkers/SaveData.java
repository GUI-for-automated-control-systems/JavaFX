package com.example.mvc.Controller.DataWorkers;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static com.example.mvc.Controller.AppController.series;

public class SaveData {
    public static void saveDATMethod(ActionEvent event) {
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
