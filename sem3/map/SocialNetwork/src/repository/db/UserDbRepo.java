package repository.db;

import domain.User;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;
import repository.EntityFilterFunction;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDbRepo implements Repository<User> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<User> validator;

    public UserDbRepo(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public void store(User obj) throws DuplicatedException, ValidationException {
        validator.validate(obj);
        String sql = "INSERT INTO users (id, name, handle) VALUES (?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getId().toString());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getHandle());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(User obj) {
        String sql = "DELETE FROM users WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getId().toString());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User oldObj, User newObj) throws ValidationException {
        validator.validate(newObj);
        String sql = "UPDATE users SET name = ?, handle = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newObj.getName());
            statement.setString(2, newObj.getHandle());
            statement.setString(3, oldObj.getId().toString());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getList() {
        List<User> userList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String handle = resultSet.getString("handle");

                User user = new User(name, handle);
                user.setId(id);
                userList.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public int size() {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as size FROM users");
            ResultSet resultSet = statement.executeQuery()) {

            return resultSet.getInt("size");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public User find(User obj) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String handle = resultSet.getString("handle");

                User user = new User(name, handle);
                user.setId(id);

                if(obj.equals(user))
                    return user;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User findByUUID(UUID uuid) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));

                if(id.equals(uuid)) {
                    String name = resultSet.getString("name");
                    String handle = resultSet.getString("handle");

                    User user = new User(name, handle);
                    user.setId(id);

                    return user;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> filterEntities(EntityFilterFunction function) {
        List<User> filteredList = new ArrayList<>();

        for(User user : getList()) {
            if(function.filter(user))
                filteredList.add(user);
        }

        return filteredList;
    }
}
