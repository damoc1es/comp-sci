package com.example.exammap.repository;

import com.example.exammap.domain.TrainStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainStationDbRepo implements Repository<String, TrainStation> {
    private final String url;
    private final String username;
    private final String password;

    public TrainStationDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<TrainStation> getAll() {
        List<TrainStation> entityList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM train_stations");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("train_id");
                String departure_city = resultSet.getString("departure_city_id");
                String destination_city = resultSet.getString("destination_city_id");

                TrainStation trainStation = new TrainStation(id, departure_city, destination_city);

                entityList.add(trainStation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entityList;
    }

    @Override
    public TrainStation findById(String id) {
        for(TrainStation entity : getAll()) {
            if(entity.getTrainId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
}
