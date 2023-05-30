package org.networking.protobuffprotocol;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesProtoProxy implements ICompetitionServices {
    private String host;

    private int port;

    private ICompetitionObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<CompProtobufs.Response> qresponses;
    private volatile boolean finished;

    public ServicesProtoProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<CompProtobufs.Response>();
    }

    @Override
    public void login(Organiser org, ICompetitionObserver client) throws CompException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(org));
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Ok) {
            this.client = client;
            return;
        }
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            closeConnection();
            throw new CompException(response.getError());
        }
    }

    @Override
    public void logout(Organiser organiser, ICompetitionObserver client) throws CompException {
        sendRequest(ProtoUtils.createLogoutRequest(organiser));
        CompProtobufs.Response response = readResponse();
        closeConnection();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }
    }

    @Override
    public AgeGroup[] getAgeGroups() throws CompException {
        sendRequest(ProtoUtils.createGetAgeGroupsRequest());
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }
        return ProtoUtils.getAgeGroups(response);
    }

    @Override
    public Event[] getEventTypesForAgeGroup(AgeGroup ageGroup) throws CompException {
        sendRequest(ProtoUtils.createGetEventTypesForAgeGroupRequest(ageGroup));
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }
        return ProtoUtils.getEvents(response);
    }

    @Override
    public Integer getEventsByChild(Child child) throws CompException {
        return null;
    }

    @Override
    public HashMap<Child, Integer> getEventsByChildren() throws CompException {
        sendRequest(ProtoUtils.createGetEventsByChildrenRequest());
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }

        HashMap<Child, Integer> map = new HashMap<>();
        Child[] children = ProtoUtils.getChildren(response);
        Integer[] counts = ProtoUtils.getCounts(response);
        for(int i = 0; i < children.length; i++) {
            map.put(children[i], counts[i]);
        }
        return map;
    }

    @Override
    public HashMap<AllowedGroup, Integer> getChildrenByEvent() throws CompException {
        sendRequest(ProtoUtils.createGetChildrenByEventRequest());
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }

        HashMap<AllowedGroup, Integer> map = new HashMap<>();
        AllowedGroup[] allowedGroups = ProtoUtils.getAllowedGroups(response);
        Integer[] counts = ProtoUtils.getCounts(response);
        for(int i = 0; i < allowedGroups.length; i++) {
            map.put(allowedGroups[i], counts[i]);
        }
        return map;
    }

    @Override
    public Child[] getChildren() throws CompException {
        sendRequest(ProtoUtils.createGetChildrenRequest());
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }

        return ProtoUtils.getChildren(response);
    }

    @Override
    public Child[] getChildrenFromAgeGroupAndEvent(AgeGroup ageGroup, Event event) throws CompException {
        sendRequest(ProtoUtils.createGetChildrenFromAgeGroupAndEventRequest(ageGroup, event));
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }
        return ProtoUtils.getChildren(response);
    }

    @Override
    public Event[] getAllEvents() throws CompException {
        sendRequest(ProtoUtils.createGetAllEventsRequest());
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new CompException(response.getError());
        }
        return ProtoUtils.getEvents(response);
    }

    @Override
    public void register(String name, Integer age, Event event) throws RegistrationException, CompException {
        sendRequest(ProtoUtils.createRegisterChildRequest(name, age, event));
        CompProtobufs.Response response = readResponse();
        if(response.getType() == CompProtobufs.Response.Type.Error) {
            throw new RegistrationException(response.getError());
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

    private void sendRequest(CompProtobufs.Request request) throws CompException {
        try {
            System.out.println("Sending request " + request);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent");
        } catch(IOException e) {
            throw new CompException("Error sending object " + e);
        }
    }

    private CompProtobufs.Response readResponse() throws CompException {
        CompProtobufs.Response response = null;
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
            output = connection.getOutputStream();
            // output.flush();
            input = connection.getInputStream();
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

    private void handleUpdate(CompProtobufs.Response update) {
        if(update.getType() == CompProtobufs.Response.Type.ChildRegistered) {
            Child child = ProtoUtils.getChild(update);
            AllowedGroup group = ProtoUtils.getAllowedGroup(update);
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
                    CompProtobufs.Response response = CompProtobufs.Response.parseDelimitedFrom(input);
                    System.out.println("response received " + response);

                    if(isUpdateResponse(response.getType())) {
                        handleUpdate(response);
                    } else {
                        try {
                            qresponses.put(response);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch(IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private boolean isUpdateResponse(CompProtobufs.Response.Type type) {
        return type == CompProtobufs.Response.Type.ChildRegistered;
    }
}
