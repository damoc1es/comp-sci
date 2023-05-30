package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.server.repository.AllowedGroupsRepo;
import org.server.repository.ChildrenRepo;
import org.server.repository.RegistrationsRepo;
import org.model.*;
import org.model.exception.DuplicatedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class RegistrationsDbRepo implements RegistrationsRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();
    AllowedGroupsRepo allowedGroupsRepo;
    ChildrenRepo childrenRepo;

    public RegistrationsDbRepo(Properties props, AllowedGroupsRepo allowedGroupsRepo, ChildrenRepo childrenRepo) {
        logger.info("Initializing RegistrationsDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.allowedGroupsRepo = allowedGroupsRepo;
        this.childrenRepo = childrenRepo;
    }

    @Override
    public void store(Registration obj) throws DuplicatedException {
        for(Registration reg : fromEventAndAgeGroup(obj.getAllowedGroup().getAgeGroup(), obj.getAllowedGroup().getEvent()))
            if(reg.getChild().getId().equals(obj.getChild().getId()))
                throw new DuplicatedException("Copilul participa deja la proba.");

        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into registrations (id, child_id, event_id) values (?, ?, ?)")) {
            preStmt.setString(1, obj.getId().toString());
            preStmt.setString(2, obj.getChild().getId().toString());
            preStmt.setString(3, obj.getAllowedGroup().getId().toString());

            preStmt.executeUpdate();
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void remove(Registration obj) {

    }

    @Override
    public void update(UUID uuid, Registration newObj) {

    }

    @Override
    public Iterable<Registration> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Registration> registrations = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from registrations")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String childId = result.getString("child_id");
                    String eventId = result.getString("event_id");

                    Child child = childrenRepo.findById(UUID.fromString(childId));
                    AllowedGroup allowedGroup = allowedGroupsRepo.findById(UUID.fromString(eventId));
                    Registration registration = new Registration(UUID.fromString(id), child, allowedGroup);
                    registrations.add(registration);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(registrations);
        return registrations;
    }

    @Override
    public Registration findById(UUID uuid) {
        return null;
    }

    @Override
    public Integer countEventsWhereChildParticipates(Child child) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Registration> registrations = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select count(*) from registrations where child_id = ?")) {
            preStmt.setString(1, child.getId().toString());

            try(ResultSet result = preStmt.executeQuery()) {
                result.next();
                return result.getInt(1);
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(registrations);
        return 0;
    }

    @Override
    public List<Registration> fromEventAndAgeGroup(AgeGroup ageGroup, Event event) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Registration> registrations = new ArrayList<>();

        AllowedGroup allowedGroup = allowedGroupsRepo.fromEventAndAgeGroup(ageGroup, event);
        if(allowedGroup == null)
            return registrations;

        try(PreparedStatement preStmt = con.prepareStatement("select * from registrations where event_id = ?")) {
            preStmt.setString(1, allowedGroup.getId().toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String childId = result.getString("child_id");
                    String eventId = result.getString("event_id");

                    Child child = childrenRepo.findById(UUID.fromString(childId));
                    //AllowedGroup allowedGroup = allowedGroupsRepo.findById(UUID.fromString(eventId));
                    Registration registration = new Registration(UUID.fromString(id), child, allowedGroup);
                    registrations.add(registration);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(registrations);
        return registrations;
    }
}
