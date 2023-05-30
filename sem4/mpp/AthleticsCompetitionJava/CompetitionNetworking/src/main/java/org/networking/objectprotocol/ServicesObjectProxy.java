package org.networking.objectprotocol;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;
import org.networking.dto.AllowedGroupDTO;
import org.networking.dto.ChildDTO;
import org.networking.dto.OrganiserDTO;
import org.networking.dto.DTOUtils;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesObjectProxy implements ICompetitionServices {
    private String host;

    private int port;

    private ICompetitionObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }


    @Override
    public void login(Organiser org, ICompetitionObserver client) throws CompException {
        initializeConnection();
        OrganiserDTO organiserDTO = new OrganiserDTO(org.getId(), org.getUsername(), org.getPassword());
        sendRequest(new LoginRequest(organiserDTO));
        Response response = readResponse();
        if(response instanceof OkResponse) {
            this.client = client;
            return;
        }
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new CompException(err.getMessage());
        }
    }

    @Override
    public void logout(Organiser organiser, ICompetitionObserver client) throws CompException {
        OrganiserDTO organiserDTO = DTOUtils.getDTO(organiser);
        sendRequest(new LogoutRequest(organiserDTO));
        Response response = readResponse();
        closeConnection();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
    }

    @Override
    public AgeGroup[] getAgeGroups() throws CompException {
        sendRequest(new GetAgeGroupsRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetAgeGroupsResponse getAgeGroupsResponse = (GetAgeGroupsResponse) response;
        return DTOUtils.getFromDTO(getAgeGroupsResponse.getAgeGroups());
    }

    @Override
    public Event[] getEventTypesForAgeGroup(AgeGroup ageGroup) throws CompException {
        sendRequest(new GetEventTypesForAgeGroupRequest(DTOUtils.getDTO(ageGroup)));
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetEventTypesForAgeGroupResponse getEventTypesForAgeGroupResponse = (GetEventTypesForAgeGroupResponse) response;
        return DTOUtils.getFromDTO(getEventTypesForAgeGroupResponse.getEvents());
    }

    @Override
    public Integer getEventsByChild(Child child) throws CompException {
        return null;
    }

    @Override
    public HashMap<Child, Integer> getEventsByChildren() throws CompException {
        sendRequest(new GetEventsByChildrenRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetEventsByChildrenResponse getEventsByChildResponse = (GetEventsByChildrenResponse) response;
        HashMap<Child, Integer> map = new HashMap<>();
        for(Map.Entry<ChildDTO, Integer> entry : getEventsByChildResponse.getEventCounts().entrySet()) {
            map.put(DTOUtils.getFromDTO(entry.getKey()), entry.getValue());
        }
        return map;
    }

    @Override
    public HashMap<AllowedGroup, Integer> getChildrenByEvent() throws CompException {
        sendRequest(new GetChildrenByEventRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetChildrenByEventResponse getChildrenByEventResponse = (GetChildrenByEventResponse) response;
        HashMap<AllowedGroup, Integer> map = new HashMap<>();
        for(Map.Entry<AllowedGroupDTO, Integer> entry : getChildrenByEventResponse.getCounts().entrySet()) {
            map.put(DTOUtils.getFromDTO(entry.getKey()), entry.getValue());
        }
        return map;
    }

    @Override
    public Child[] getChildren() throws CompException {
        sendRequest(new GetChildrenRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetChildrenResponse getChildrenResponse = (GetChildrenResponse) response;
        ChildDTO[] childrenDTO = getChildrenResponse.getChildren();
        return DTOUtils.getFromDTO(childrenDTO);
    }

    @Override
    public Child[] getChildrenFromAgeGroupAndEvent(AgeGroup ageGroup, Event event) throws CompException {
        sendRequest(new GetChildrenFromAgeGroupAndEventRequest(DTOUtils.getDTO(ageGroup), DTOUtils.getDTO(event)));
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetChildrenFromAgeGroupAndEventResponse getChildrenFromAgeGroupAndEventResponse = (GetChildrenFromAgeGroupAndEventResponse) response;
        return DTOUtils.getFromDTO(getChildrenFromAgeGroupAndEventResponse.getChildren());
    }

    @Override
    public Event[] getAllEvents() throws CompException {
        sendRequest(new GetAllEventsRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new CompException(err.getMessage());
        }
        GetAllEventsResponse getAllEventsResponse = (GetAllEventsResponse) response;
        return DTOUtils.getFromDTO(getAllEventsResponse.getEvents());
    }

    @Override
    public void register(String name, Integer age, Event event) throws RegistrationException, CompException {
        sendRequest(new RegisterChildRequest(name, age, DTOUtils.getDTO(event)));
        Response response = readResponse();
        if(response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new RegistrationException(err.getMessage());
        }
    }

    @Override
    public int getNumberOfChildrenFromEvent(AgeGroup ageGroup, Event event) {
        return 0;
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws CompException {
        try {
            output.writeObject(request);
            output.flush();
        } catch(IOException e) {
            throw new CompException("Error sending object " + e);
        }
    }

    private Response readResponse() throws CompException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) {
        if(update instanceof ChildRegisteredResponse) {
            Child child = DTOUtils.getFromDTO(((ChildRegisteredResponse) update).getChild());
            AllowedGroup group = DTOUtils.getFromDTO(((ChildRegisteredResponse) update).getAllowedGroup());
            try {
                client.childRegistered(child, group);
            } catch (CompException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while(!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if(response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch(IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
