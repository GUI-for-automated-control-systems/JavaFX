package com.example.mvc.Controller;

import java.text.DecimalFormat;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ShowCoordinatesNode extends StackPane {

    public ShowCoordinatesNode(Number x, Number y) {

        final Label label = createDataThresholdLabel(x, y);

        setOnMouseEntered(mouseEvent -> {
            setScaleX(1);
            setScaleY(1);
            getChildren().setAll(label);
            toFront();
        });
        setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getChildren().clear();
            }
        });
    }

    private Label createDataThresholdLabel(Number x, Number y) {
        DecimalFormat df = new DecimalFormat("0.##");
        final Label label = new Label("(" + df.format(x) + "; " + df.format(y) + ")");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}