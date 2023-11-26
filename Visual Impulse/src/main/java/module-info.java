module com.example.mvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;

    opens com.example.mvc to javafx.fxml;
    exports com.example.mvc;
    exports com.example.mvc.Controller;
    opens com.example.mvc.Controller to javafx.fxml;
}