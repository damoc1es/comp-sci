package org.networking.protobuffprotocol;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientProtoWorker implements Runnable, ICompetitionObserver {
    private ICompetitionServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public ClientProtoWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = connection.getOutputStream();
            input = connection.getInputStream();
            connected = true;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(connected) {
            try {
                System.out.println("Waiting requests ...");
                CompProtobufs.Request request = CompProtobufs.Request.parseDelimitedFrom(input);
                System.out.println("Request received: " + request);
                CompProtobufs.Response response = handleRequest(request);

                if(response != null) {
                    sendResponse(response);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch(IOException e) {
            System.out.println("Error " + e);
        }
    }

    // UPDATES FUNCTIONS

    public void childRegistered(Child child, AllowedGroup group) throws CompException {
        System.out.println("Child registered " + child.toString() + " at " + group.toString());
        try {
            sendResponse(ProtoUtils.createChildRegisteredRespose(child, group));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private CompProtobufs.Response handleRequest(CompProtobufs.Request request) {
        switch (request.getType()) {
            case Login: {
                System.out.println("Login request ...");
                Organiser org = ProtoUtils.getOrganiser(request);

                try {
                    server.login(org, this);
                    return ProtoUtils.createOkResponse();
                } catch(CompException e) {
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Logout: {
                System.out.println("Logout request ...");
                Organiser org = ProtoUtils.getOrganiser(request);

                try {
                    server.logout(org, this);
                    connected = false;
                    return ProtoUtils.createOkResponse();
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetChildren: {
                System.out.println("Get children request ...");

                try {
                    return ProtoUtils.createGetChildrenResponse(server.getChildren());
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetEventsByChildren : {
                System.out.println("Get events by child request ...");

                try {
                    return ProtoUtils.createGetEventsByChildrenResponse(server.getEventsByChildren());
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAgeGroups: {
                System.out.println("Get age groups request ...");

                try {
                    return ProtoUtils.createGetAgeGroupsResponse(server.getAgeGroups());
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAllEvents: {
                System.out.println("Get all events request ...");

                try {
                    return ProtoUtils.createGetAllEventsResponse(server.getAllEvents());
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetEventTypesForAgeGroup: {
                System.out.println("Get event types for age group request ...");

                try {
                    return ProtoUtils.createGetEventTypesForAgeGroupResponse(server.getEventTypesForAgeGroup(ProtoUtils.getAgeGroup(request)));
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetChildrenFromAgeGroupAndEvent: {
                System.out.println("Get children from age group and event request ...");

                AgeGroup ageGroup = ProtoUtils.getAgeGroup(request);
                Event event = ProtoUtils.getEvent(request);
                try {
                    return ProtoUtils.createGetChildrenFromAgeGroupAndEventResponse(server.getChildrenFromAgeGroupAndEvent(ageGroup, event));
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case RegisterChild: {
                System.out.println("Register child request ...");

                try {
                    server.register(request.getName(), request.getAge(), ProtoUtils.getEvent(request));
                    return ProtoUtils.createOkResponse();
                } catch(CompException | RegistrationException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetChildrenByEvent: {
                System.out.println("Get children by event request ...");

                try {
                    HashMap<AllowedGroup, Integer> map = server.getChildrenByEvent();
                    return ProtoUtils.createGetChildrenByEventResponse(map);
                } catch(CompException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }
        return null;
    }

    private void sendResponse(CompProtobufs.Response response) throws IOException {
        System.out.println("sending response " + response);
        //synchronized (output) {
            response.writeDelimitedTo(output);
            output.flush();
        //}
    }
}
