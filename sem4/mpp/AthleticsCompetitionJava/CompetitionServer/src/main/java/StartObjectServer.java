import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.networking.utils.CompetitionObjectConcurrentServer;
import org.networking.utils.ServerException;
import org.server.CompetitionServicesImpl;
import org.server.repository.*;
import org.server.repository.db.*;
import org.services.ICompetitionServices;
import org.networking.utils.AbstractServer;

import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort = 55555;

    public static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();

        try {
            props.load(StartObjectServer.class.getResourceAsStream("server.properties"));
            System.out.println("Server properties set.");
            props.list(System.out);
        } catch(IOException e) {
            System.err.println("Cannot find server.properties " + e);
            return;
        }

        initialize();

        AgeGroupsRepo ageGroupsRepo = new AgeGroupsDbRepo(props);
        EventsRepo eventsRepo = new EventsDbRepo(props);
        AllowedGroupsRepo allowedGroupsRepo = new AllowedGroupsDbRepo(props, ageGroupsRepo, eventsRepo);
        ChildrenRepo childrenRepo = new ChildrenDbRepo(props);
        //OrganisersRepo organisersRepo = new OrganisersDbRepo(props);
        OrganisersRepo organisersRepo = new OrganisersHibernateRepo(sessionFactory);
        RegistrationsRepo registrationsRepo = new RegistrationsDbRepo(props, allowedGroupsRepo, childrenRepo);

        ICompetitionServices serverImpl = new CompetitionServicesImpl(ageGroupsRepo, eventsRepo, allowedGroupsRepo, childrenRepo, organisersRepo, registrationsRepo);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(props.getProperty("competition.server.port"));
        } catch(NumberFormatException nef) {
            System.err.println("Wrong Port Number " + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new CompetitionObjectConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch(ServerException e) {
            System.err.println("Error starting the server " + e.getMessage());
        }
    }

    static void close(){
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
