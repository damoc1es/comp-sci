package org.networking.objectprotocol;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;
import org.networking.dto.*;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientObjectWorker implements Runnable, ICompetitionObserver {
    private ICompetitionServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientObjectWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if(response != null) {
                    sendResponse((Response) response);
                }
            } catch(IOException | ClassNotFoundException e) {
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
        ChildDTO childDTO = DTOUtils.getDTO(child);
        System.out.println("Child registered " + child.toString() + " at " + group.toString());
        try {
            sendResponse(new ChildRegisteredResponse(childDTO, DTOUtils.getDTO(group)));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        if(request instanceof LoginRequest) {
            System.out.println("Login request ...");
            LoginRequest logReq = (LoginRequest) request;
            OrganiserDTO organiserDTO = logReq.getOrganiser();
            Organiser org = DTOUtils.getFromDTO(organiserDTO);

            try {
                server.login(org, this);
                return new OkResponse();
            } catch(CompException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof LogoutRequest) {
            System.out.println("Logout request ...");
            LogoutRequest logReq = (LogoutRequest) request;
            OrganiserDTO organiserDTO = logReq.getOrganiser();
            Organiser org = DTOUtils.getFromDTO(organiserDTO);

            try {
                server.logout(org, this);
                connected = false;
                return new OkResponse();
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetChildrenRequest) {
            System.out.println("Get children request ...");
            GetChildrenRequest getReq = (GetChildrenRequest) request;

            try {
                return new GetChildrenResponse(DTOUtils.getDTO(server.getChildren()));
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetEventsByChildrenRequest) {
            System.out.println("Get events by child request ...");
            GetEventsByChildrenRequest getReq = (GetEventsByChildrenRequest) request;
            try {
                HashMap<ChildDTO, Integer> map = new HashMap<>();
                for(Map.Entry<Child, Integer> entry : server.getEventsByChildren().entrySet()) {
                    map.put(DTOUtils.getDTO(entry.getKey()), entry.getValue());
                }
                return new GetEventsByChildrenResponse(map);
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetAgeGroupsRequest) {
            System.out.println("Get age groups request ...");
            GetAgeGroupsRequest getReq = (GetAgeGroupsRequest) request;
            try {
                return new GetAgeGroupsResponse(DTOUtils.getDTO(server.getAgeGroups()));
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetAllEventsRequest) {
            System.out.println("Get all events request ...");
            GetAllEventsRequest getReq = (GetAllEventsRequest) request;
            try {
                return new GetAllEventsResponse(DTOUtils.getDTO(server.getAllEvents()));
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetEventTypesForAgeGroupRequest) {
            System.out.println("Get event types for age group request ...");
            GetEventTypesForAgeGroupRequest getReq = (GetEventTypesForAgeGroupRequest) request;
            try {
                return new GetEventTypesForAgeGroupResponse(DTOUtils.getDTO(server.getEventTypesForAgeGroup(DTOUtils.getFromDTO(getReq.getAgeGroup()))));
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetChildrenFromAgeGroupAndEventRequest) {
            System.out.println("Get children from age group and event request ...");
            GetChildrenFromAgeGroupAndEventRequest getReq = (GetChildrenFromAgeGroupAndEventRequest) request;
            AgeGroup ageGroup = DTOUtils.getFromDTO(getReq.getAgeGroup());
            Event event = DTOUtils.getFromDTO(getReq.getEvent());
            try {
                return new GetChildrenFromAgeGroupAndEventResponse(DTOUtils.getDTO(server.getChildrenFromAgeGroupAndEvent(ageGroup, event)));
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof RegisterChildRequest) {
            System.out.println("Register child request ...");
            RegisterChildRequest regReq = (RegisterChildRequest) request;
            try {
                server.register(regReq.getName(), regReq.getAge(), DTOUtils.getFromDTO(regReq.getEvent()));
                return new OkResponse();
            } catch(CompException | RegistrationException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetChildrenByEventRequest) {
            System.out.println("Get children by event request ...");
            GetChildrenByEventRequest getReq = (GetChildrenByEventRequest) request;
            try {
                HashMap<AllowedGroupDTO, Integer> map = new HashMap<>();
                for(Map.Entry<AllowedGroup, Integer> entry : server.getChildrenByEvent().entrySet()) {
                    map.put(DTOUtils.getDTO(entry.getKey()), entry.getValue());
                }
                return new GetChildrenByEventResponse(map);
            } catch(CompException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        return null;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
