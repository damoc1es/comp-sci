module com.example.exammap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.exammap to javafx.fxml;
    exports com.example.exammap;
    exports com.example.exammap.ui;
    exports com.example.exammap.domain;
    exports com.example.exammap.repository;
    exports com.example.exammap.service;
    opens com.example.exammap.ui to javafx.fxml;
}