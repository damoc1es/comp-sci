package repository.db;

import domain.Friendship;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;
import repository.EntityFilterFunction;
import repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class FriendshipDbRepo implements Repository<Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;

    public FriendshipDbRepo(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public void store(Friendship obj) throws DuplicatedException, ValidationException {
        validator.validate(obj);
        String sql = "INSERT INTO friendships (id, userid1, userid2, friendsfrom) VALUES (?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getId().toString());
            statement.setString(2, obj.getUserId1().toString());
            statement.setString(3, obj.getUserId2().toString());
            statement.setString(4, obj.getFriendsFrom().toString());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Friendship obj) {
        String sql = "DELETE FROM friendships WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getId().toString());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Friendship oldObj, Friendship newObj) throws ValidationException {
        validator.validate(newObj);
        String sql = "UPDATE friendships SET userid1 = ?, userid2 = ?, friendsfrom = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newObj.getUserId1().toString());
            statement.setString(2, newObj.getUserId1().toString());
            statement.setString(3, newObj.getFriendsFrom().toString());
            statement.setString(4, oldObj.getId().toString());

            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Friendship> getList() {
        List<Friendship> friendshipList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                UUID userId1 = UUID.fromString(resultSet.getString("userid1"));
                UUID userId2 = UUID.fromString(resultSet.getString("userid2"));
                LocalDateTime friendsFrom = LocalDateTime.parse(resultSet.getString("friendsfrom"));

                Friendship friendship = new Friendship(userId1, userId2, friendsFrom);
                friendship.setId(id);
                friendshipList.add(friendship);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return friendshipList;
    }

    @Override
    public int size() {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as size FROM friendships");
            ResultSet resultSet = statement.executeQuery()) {

            return resultSet.getInt("size");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendshipSet = new HashSet<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                UUID userId1 = UUID.fromString(resultSet.getString("userid1"));
                UUID userId2 = UUID.fromString(resultSet.getString("userid2"));
                LocalDateTime friendsFrom = LocalDateTime.parse(resultSet.getString("friendsfrom"));

                Friendship friendship = new Friendship(userId1, userId2, friendsFrom);
                friendship.setId(id);
                friendshipSet.add(friendship);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return friendshipSet;
    }

    @Override
    public Friendship find(Friendship obj) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                UUID userId1 = UUID.fromString(resultSet.getString("userid1"));
                UUID userId2 = UUID.fromString(resultSet.getString("userid2"));
                LocalDateTime friendsFrom = LocalDateTime.parse(resultSet.getString("friendsfrom"));

                Friendship friendship = new Friendship(userId1, userId2, friendsFrom);
                friendship.setId(id);

                if(obj.equals(friendship))
                    return friendship;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Friendship findByUUID(UUID uuid) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));

                if(id.equals(uuid)) {
                    UUID userId1 = UUID.fromString(resultSet.getString("userid1"));
                    UUID userId2 = UUID.fromString(resultSet.getString("userid2"));
                    LocalDateTime friendsFrom = LocalDateTime.parse(resultSet.getString("friendsfrom"));

                    Friendship friendship = new Friendship(userId1, userId2, friendsFrom);
                    friendship.setId(id);

                    return friendship;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Friendship> filterEntities(EntityFilterFunction function) {
        List<Friendship> filteredList = new ArrayList<>();

        for(Friendship friendship : getList()) {
            if(function.filter(friendship))
                filteredList.add(friendship);
        }

        return filteredList;
    }
}
