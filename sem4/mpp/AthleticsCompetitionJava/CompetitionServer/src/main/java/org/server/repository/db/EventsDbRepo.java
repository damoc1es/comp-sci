package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Event;
import org.server.repository.EventsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Component
public class EventsDbRepo implements EventsRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public EventsDbRepo(Properties props) {
        logger.info("Initializing EventTypesDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void store(Event obj) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into event_types (id, meters) values (?, ?)")) {
            preStmt.setString(1, obj.getId().toString());
            preStmt.setInt(2, obj.getMeters());

            preStmt.executeUpdate();
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void remove(Event obj) throws Exception {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from event_types where id = ?")) {
            preStmt.setString(1, obj.getId().toString());

            preStmt.executeUpdate();
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
            throw new Exception(e);
        }
    }

    @Override
    public void update(UUID uuid, Event newObj) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update event_types set meters = ? where id = ?")) {
            preStmt.setInt(1, newObj.getMeters());
            preStmt.setString(2, uuid.toString());

            int result = preStmt.executeUpdate();
            logger.trace("Update {} instances in events", result);
            System.out.println("Update instances in events " + result);
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public Iterable<Event> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Event> events = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from event_types")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    int meters = result.getInt("meters");

                    Event event = new Event(UUID.fromString(id), meters);
                    events.add(event);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(events);
        return events;
    }

    @Override
    public Event findById(UUID id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Event event = null;

        try(PreparedStatement preStmt = con.prepareStatement("select * from event_types where id = ?")) {
            preStmt.setString(1, id.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int meters = result.getInt("meters");

                    event = new Event(id, meters);
                    return event;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(event);

        return null;
    }
}
