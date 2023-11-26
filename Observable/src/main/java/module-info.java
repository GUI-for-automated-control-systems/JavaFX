module com.example.observablechart {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.observablechart to javafx.fxml;
    exports com.example.observablechart;
}