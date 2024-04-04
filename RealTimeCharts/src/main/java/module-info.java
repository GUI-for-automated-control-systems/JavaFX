module com.example.realtimecharts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.realtimecharts to javafx.fxml;
    exports com.example.realtimecharts;
}