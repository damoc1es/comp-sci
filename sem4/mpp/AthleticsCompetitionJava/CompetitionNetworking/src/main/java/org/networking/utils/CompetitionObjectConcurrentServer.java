package org.networking.utils;

import org.networking.objectprotocol.ClientObjectWorker;
import org.networking.protobuffprotocol.ClientProtoWorker;
import org.services.ICompetitionServices;

import java.net.Socket;

public class CompetitionObjectConcurrentServer extends AbsConcurrentServer {
    private ICompetitionServices competitionServer;

    public CompetitionObjectConcurrentServer(int port, ICompetitionServices competitionServer) {
        super(port);
        this.competitionServer = competitionServer;
        System.out.println("Competition - CompetitionObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ClientObjectWorker worker = new ClientObjectWorker(competitionServer, client); // PROTOBUF EDIT
        ClientProtoWorker worker = new ClientProtoWorker(competitionServer, client);
        Thread tw =new Thread(worker);
        return tw;
    }
}
