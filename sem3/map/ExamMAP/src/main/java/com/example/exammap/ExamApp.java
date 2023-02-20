package com.example.exammap;

import com.example.exammap.repository.CityDbRepo;
import com.example.exammap.repository.TrainStationDbRepo;
import com.example.exammap.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamApp extends Application {
    public static Service service = null;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExamApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 250);
        stage.setTitle("Exam MAP | Cautare Rute");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ExamMAPDatabase";
        String username = "postgres";
        String password = "superman";

        CityDbRepo entityRepo = new CityDbRepo(url, username, password);
        TrainStationDbRepo trainRepo = new TrainStationDbRepo(url, username, password);
        service = new Service(entityRepo, trainRepo);

        launch();
    }
}