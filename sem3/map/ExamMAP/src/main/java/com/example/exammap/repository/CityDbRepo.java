package com.example.exammap.repository;

import com.example.exammap.domain.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDbRepo implements Repository<String, City> {
    private final String url;
    private final String username;
    private final String password;

    public CityDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<City> getAll() {
        List<City> entityList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cities");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                City city = new City(id, name);

                entityList.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entityList;
    }

    @Override
    public City findById(String id) {
        for(City entity : getAll()) {
            if(entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
}
