package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.server.repository.AgeGroupsRepo;
import org.model.AgeGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class AgeGroupsDbRepo implements AgeGroupsRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public AgeGroupsDbRepo(Properties props) {
        logger.info("Initializing AgeGroupsDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void store(AgeGroup obj) {

    }

    @Override
    public void remove(AgeGroup obj) {

    }

    @Override
    public void update(UUID uuid, AgeGroup newObj) {

    }

    @Override
    public Iterable<AgeGroup> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<AgeGroup> ageGroups = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from age_groups")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    int minAge = result.getInt("min_age");
                    int maxAge = result.getInt("max_age");

                    AgeGroup ageGroup = new AgeGroup(UUID.fromString(id), minAge, maxAge);
                    ageGroups.add(ageGroup);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(ageGroups);
        return ageGroups;
    }

    @Override
    public AgeGroup findById(UUID id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        AgeGroup ageGroup = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from age_groups where id = ?")) {
            preStmt.setString(1, id.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int minAge = result.getInt("min_age");
                    int maxAge = result.getInt("max_age");

                    ageGroup = new AgeGroup(id, minAge, maxAge);
                    return ageGroup;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(ageGroup);
        return null;
    }
}