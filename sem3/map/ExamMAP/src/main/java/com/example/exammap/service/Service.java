package com.example.exammap.service;

import com.example.exammap.domain.City;
import com.example.exammap.domain.TrainStation;
import com.example.exammap.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.exammap.constants.PriceConstants.PRICE_PER_STATION;

public class Service {
    Repository<String, City> cityRepo;
    Repository<String, TrainStation> trainRepo;

    public Service(Repository<String, City> cityRepo, Repository<String, TrainStation> trainRepo) {
        this.cityRepo = cityRepo;
        this.trainRepo = trainRepo;
    }

    public List<City> getAllCities() {
        return cityRepo.getAll();
    }

    public void DFS(String cityId, String finalId, List<String> route, List<List<String>> allRoutes) {
        if(cityId.equals(finalId))
            allRoutes.add(route);

        String thisCity = cityRepo.findById(cityId).getName();
        for(TrainStation train : trainRepo.getAll()) {
            if(train.getDepartureCityId().equals(cityId)) {
                String dest = cityRepo.findById(train.getDestinationCityId()).getName();

                boolean repeat = false;
                for(int i=1; i<route.size(); i+=2) {
                    if (route.get(i).equals(train.getTrainId()) && route.get(i - 1).equals(thisCity) && route.get(i + 1).equals(dest)) {
                        repeat = true;
                        break;
                    }
                }
                if(repeat)
                    continue;

                List<String> newList = new ArrayList<>(route);
                newList.add(train.getTrainId());
                newList.add(dest);
                DFS(train.getDestinationCityId(), finalId, newList, allRoutes);
            }
        }
    }

    public List<List<String>> getDirectRoutes(String departureId, String destinationId) {
        List<List<String>> possibleRoutes = getRoutes(departureId, destinationId);
        List<List<String>> routes = new ArrayList<>();

        for(List<String> route : possibleRoutes) {
            String trainId = route.get(1);
            boolean isOk = true;
            for(int i=1; i<route.size(); i+=2) {
                if(!trainId.equals(route.get(i))) {
                    isOk = false;
                    break;
                }
            }
            if(isOk) {
                routes.add(route);
            }
        }
        return routes;
    }

    public List<List<String>> getRoutes(String departureId, String destinationId) {
        List<List<String>> allRoutes = new ArrayList<>();
        List<String> empty = new ArrayList<>();
        empty.add(cityRepo.findById(departureId).getName());
        DFS(departureId, destinationId, empty, allRoutes);
        return allRoutes;
    }

    public Float getPriceOfRoute(List<String> route) {
        int NUMBER_OF_STATIONS = route.size()/2;
        return PRICE_PER_STATION * NUMBER_OF_STATIONS;
    }
}