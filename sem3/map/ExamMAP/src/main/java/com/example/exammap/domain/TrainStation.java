package com.example.exammap.domain;

public class TrainStation {
    String trainId;
    String departureCityId;
    String destinationCityId;

    public TrainStation(String trainId, String departureCityId, String destinationCityId) {
        this.trainId = trainId;
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getDepartureCityId() {
        return departureCityId;
    }

    public String getDestinationCityId() {
        return destinationCityId;
    }
}
