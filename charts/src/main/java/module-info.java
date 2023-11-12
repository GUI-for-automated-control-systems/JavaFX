module com.example.charts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.charts to javafx.fxml;
    exports com.example.charts;
}