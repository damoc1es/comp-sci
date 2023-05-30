package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Child;
import org.server.repository.ChildrenRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class ChildrenDbRepo implements ChildrenRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ChildrenDbRepo(Properties props) {
        logger.info("Initializing ChildrenDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void store(Child obj) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into children (id, name, age) values (?, ?, ?)")) {
            preStmt.setString(1, obj.getId().toString());
            preStmt.setString(2, obj.getName());
            preStmt.setInt(3, obj.getAge());

            preStmt.executeUpdate();
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void remove(Child obj) {

    }

    @Override
    public void update(UUID uuid, Child newObj) {

    }

    @Override
    public Iterable<Child> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Child> children = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from children")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");

                    Child child = new Child(UUID.fromString(id), name, age);
                    children.add(child);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(children);
        return children;
    }

    @Override
    public Child findById(UUID id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Child child = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from children where id = ?")) {
            preStmt.setString(1, id.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String name = result.getString("name");
                    int age = result.getInt("age");

                    child = new Child(id, name, age);
                    return child;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(child);
        return null;
    }

    @Override
    public Child findChildByNameAndAge(String name, Integer age) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Child child = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from children where name = ? and age = ?")) {
            preStmt.setString(1, name);
            preStmt.setInt(2, age);
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    child = new Child(UUID.fromString(id), name, age);
                    return child;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(child);
        return null;
    }
}
