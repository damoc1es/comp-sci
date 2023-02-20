package com.example.exammap.ui;

import com.example.exammap.ExamApp;
import com.example.exammap.domain.City;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartViewController {
    public Button openNewClientWindowButton;
    static List<ClientWindowViewController> clientControllers = new ArrayList<>();

    public static void openAnotherWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExamApp.class.getResource("client-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        Stage stage = new Stage();
        stage.setTitle("Client | Rute Trenuri");
        stage.setScene(scene);
        stage.show();

        clientControllers.add(fxmlLoader.getController());
        stage.setOnHiding(e -> ((ClientWindowViewController) fxmlLoader.getController()).closing());
    }

    public static void removeController(ClientWindowViewController controller) {
        clientControllers.remove(controller);
    }

    public static void update() {
        for (int i = 0; i < clientControllers.size(); i++) {

            List<City> cities1 = clientControllers.get(i).getCitiesLooked();
            if(cities1 == null) {
                clientControllers.get(i).setOtherPeopleLabel(0);
                continue;
            }

            int k = 0;
            for(int j = 0; j < clientControllers.size(); j++) {
                if(i != j) {
                    List<City> cities2 = clientControllers.get(j).getCitiesLooked();
                    if(cities2 == null)
                        continue;

                    if(cities1.get(0).getId().equals(cities2.get(0).getId()) && cities1.get(1).getId().equals(cities2.get(1).getId()))
                        k++;
                }
            }
            clientControllers.get(i).setOtherPeopleLabel(k);
        }
    }

    @FXML
    protected void onOpenNewClientWindowButtonClick() throws IOException {
        openAnotherWindow();
    }
}