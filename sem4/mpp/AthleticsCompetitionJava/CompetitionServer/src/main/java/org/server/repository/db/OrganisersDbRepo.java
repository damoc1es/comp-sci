package org.server.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.server.repository.OrganisersRepo;
import org.model.Organiser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class OrganisersDbRepo implements OrganisersRepo {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public OrganisersDbRepo(Properties props) {
        logger.info("Initializing OrganisersDbRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void store(Organiser obj) {

    }

    @Override
    public void remove(Organiser obj) {

    }

    @Override
    public void update(UUID uuid, Organiser newObj) {

    }

    @Override
    public Iterable<Organiser> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Organiser> organisers = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from organisers")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Organiser organiser = new Organiser(UUID.fromString(id), username, password);
                    organisers.add(organiser);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(organisers);
        return organisers;
    }

    @Override
    public Organiser findById(UUID uuid) {
        return null;
    }

    @Override
    public Organiser findByUsername(String username) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("select * from organisers where username = ?")) {
            preStmt.setString(1, username);
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    String id = result.getString("id");
                    String password = result.getString("password");

                    Organiser organiser = new Organiser(UUID.fromString(id), username, password);
                    return organiser;
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(null);
        return null;
    }
}
