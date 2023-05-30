package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.AgeGroup;
import org.model.AllowedGroup;
import org.model.Event;
import org.server.repository.AgeGroupsRepo;
import org.server.repository.AllowedGroupsRepo;
import org.server.repository.EventsRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class AllowedGroupsDbRepo implements AllowedGroupsRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();
    AgeGroupsRepo ageGroupsRepo;
    EventsRepo eventsRepo;

    public AllowedGroupsDbRepo(Properties props, AgeGroupsRepo ageGroupsRepo, EventsRepo eventsRepo) {
        logger.info("Initializing AllowedGroupsDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.ageGroupsRepo = ageGroupsRepo;
        this.eventsRepo = eventsRepo;
    }

    @Override
    public void store(AllowedGroup obj) {

    }

    @Override
    public void remove(AllowedGroup obj) {

    }

    @Override
    public void update(UUID uuid, AllowedGroup newObj) {

    }

    @Override
    public Iterable<AllowedGroup> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<AllowedGroup> allowedGroups = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from allowed_groups")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String ageGroupId = result.getString("age_group_id");
                    String eventTypeId = result.getString("event_type_id");

                    AgeGroup ageGroup = ageGroupsRepo.findById(UUID.fromString(ageGroupId));
                    Event event = eventsRepo.findById(UUID.fromString(eventTypeId));
                    AllowedGroup allowedGroup = new AllowedGroup(UUID.fromString(id), ageGroup, event);
                    allowedGroups.add(allowedGroup);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(allowedGroups);
        return allowedGroups;
    }

    @Override
    public AllowedGroup findById(UUID uuid) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        AllowedGroup allowedGroup = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from allowed_groups where id = ?")) {
            preStmt.setString(1, uuid.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String ageGroupId = result.getString("age_group_id");
                    String eventTypeId = result.getString("event_type_id");

                    AgeGroup ageGroup = ageGroupsRepo.findById(UUID.fromString(ageGroupId));
                    Event event = eventsRepo.findById(UUID.fromString(eventTypeId));
                    allowedGroup = new AllowedGroup(uuid, ageGroup, event);
                    return allowedGroup;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(allowedGroup);
        return null;
    }

    @Override
    public List<AllowedGroup> findByAgeGroupId(UUID uuid) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<AllowedGroup> allowedGroups = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from allowed_groups where age_group_id = ?")) {
            preStmt.setString(1, uuid.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String ageGroupId = result.getString("age_group_id");
                    String eventTypeId = result.getString("event_type_id");

                    AgeGroup ageGroup = ageGroupsRepo.findById(UUID.fromString(ageGroupId));
                    Event event = eventsRepo.findById(UUID.fromString(eventTypeId));
                    AllowedGroup allowedGroup = new AllowedGroup(UUID.fromString(id), ageGroup, event);
                    allowedGroups.add(allowedGroup);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(allowedGroups);
        return allowedGroups;
    }

    @Override
    public AllowedGroup fromEventAndAgeGroup(AgeGroup ageGroup, Event event) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        AllowedGroup allowedGroup = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from allowed_groups where age_group_id = ? and event_type_id = ?")) {
            preStmt.setString(1, ageGroup.getId().toString());
            preStmt.setString(2, event.getId().toString());

            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String ageGroupId = result.getString("age_group_id");
                    String eventTypeId = result.getString("event_type_id");

                    AgeGroup ageGroup2 = ageGroupsRepo.findById(UUID.fromString(ageGroupId));
                    Event event2 = eventsRepo.findById(UUID.fromString(eventTypeId));
                    allowedGroup = new AllowedGroup(UUID.fromString(id), ageGroup2, event2);
                    return allowedGroup;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(allowedGroup);
        return null;
    }

    @Override
    public List<AllowedGroup> findByEventId(UUID uuid) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<AllowedGroup> allowedGroups = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from allowed_groups where event_type_id = ?")) {
            preStmt.setString(1, uuid.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String ageGroupId = result.getString("age_group_id");
                    String eventTypeId = result.getString("event_type_id");

                    AgeGroup ageGroup = ageGroupsRepo.findById(UUID.fromString(ageGroupId));
                    Event event = eventsRepo.findById(UUID.fromString(eventTypeId));
                    AllowedGroup allowedGroup = new AllowedGroup(UUID.fromString(id), ageGroup, event);
                    allowedGroups.add(allowedGroup);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(allowedGroups);
        return allowedGroups;
    }
}