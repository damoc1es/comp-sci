package org.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.ui.GroupsController;
import org.client.ui.LoginController;
import org.client.ui.OrganiserController;
import org.networking.objectprotocol.ServicesObjectProxy;
import org.networking.protobuffprotocol.ServicesProtoProxy;
import org.services.ICompetitionServices;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {
    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("client.properties"));
            System.out.println("Client properties set.");
            clientProps.list(System.out);
        } catch(IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("competition.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("competition.server.port"));
        } catch(NumberFormatException nef) {
            System.err.println("Wrong port number " + nef.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        // ICompetitionServices server = new ServicesObjectProxy(serverIP, serverPort); // PROTOBUF EDIT
        ICompetitionServices server = new ServicesProtoProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("org/client/login-view.fxml"));
        Parent root = loader.load();

        LoginController ctrl = loader.getController();
        ctrl.setServer(server);

        FXMLLoader organiserLoader = new FXMLLoader(getClass().getClassLoader().getResource("org/client/organiser-view.fxml"));
        Parent oroot = organiserLoader.load();
        OrganiserController organiserCtrl = organiserLoader.getController();
        organiserCtrl.setServer(server);

        ctrl.setOrganiserController(organiserCtrl);
        ctrl.setParent(oroot);

        primaryStage.setTitle("Please log in");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
