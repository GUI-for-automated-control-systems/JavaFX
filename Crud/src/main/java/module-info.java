module com.crud.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.sql;


    opens com.crud.crud to javafx.fxml;
    exports com.crud.crud;
    exports com.crud.crud.data;
    exports com.crud.crud.model;
}