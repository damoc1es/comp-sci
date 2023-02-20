package com.example.exammap.ui;

import com.example.exammap.ExamApp;
import com.example.exammap.domain.City;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientWindowViewController implements Initializable {
    public ComboBox<City> departureCityCombo;
    public ComboBox<City> destinationCityCombo;
    public CheckBox directRoutesCheckbox;
    public Button searchButton;
    public Label routesLabel;
    public Label otherPeopleLabel;
    ObservableList<City> modelCities = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departureCityCombo.setItems(modelCities);
        destinationCityCombo.setItems(modelCities);
        modelCities.setAll(ExamApp.service.getAllCities());

        searchButton.disableProperty().bind(
            Bindings.or(Bindings.isNull(departureCityCombo.getSelectionModel().selectedItemProperty()),
                    Bindings.isNull(destinationCityCombo.getSelectionModel().selectedItemProperty()))
        );

        departureCityCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            StartViewController.update();
        });
        destinationCityCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            StartViewController.update();
        });
    }

    @FXML
    protected void onSearchButtonClick() {
        if(departureCityCombo.getSelectionModel().isEmpty() || destinationCityCombo.getSelectionModel().isEmpty()) {
            return;
        }

        City city1 = departureCityCombo.getSelectionModel().getSelectedItem();
        City city2 = destinationCityCombo.getSelectionModel().getSelectedItem();

        if(city1.getId().equals(city2.getId())) {
            routesLabel.setText("Deja sunteti in orasul destinatie.");
            return;
        }

        List<List<String>> routes;
        if(directRoutesCheckbox.isSelected()) {
            routes = ExamApp.service.getDirectRoutes(city1.getId(), city2.getId());
        } else routes = ExamApp.service.getRoutes(city1.getId(), city2.getId());

        StringBuilder text = new StringBuilder();
        for(List<String> route : routes) {
            text.append(route.get(0));
            for(int i=1; i<route.size(); i+=2) {
                text.append(String.format(" --%s--> %s", route.get(i), route.get(i + 1)));
            }
            text.append(String.format(", price: %.2f \n", ExamApp.service.getPriceOfRoute(route)));
        }
        routesLabel.setText(text.toString());
    }

    public List<City> getCitiesLooked() {
        if(departureCityCombo.getSelectionModel().isEmpty() || destinationCityCombo.getSelectionModel().isEmpty()) {
            return null;
        }

        List<City> cities = new ArrayList<>();
        cities.add(departureCityCombo.getSelectionModel().getSelectedItem());
        cities.add(destinationCityCombo.getSelectionModel().getSelectedItem());
        return cities;
    }

    public void setOtherPeopleLabel(int number) {
        otherPeopleLabel.setText(number + " other user(s) are looking at the same route");
    }

    public void closing() {
        StartViewController.removeController(this);
        StartViewController.update();
    }
}
