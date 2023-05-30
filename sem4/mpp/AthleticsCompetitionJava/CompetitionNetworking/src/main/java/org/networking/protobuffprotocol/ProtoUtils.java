package org.networking.protobuffprotocol;

import org.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProtoUtils {
    public static CompProtobufs.Request createGetAgeGroupsRequest() {
        return CompProtobufs.Request.newBuilder()
                .setType(CompProtobufs.Request.Type.GetAgeGroups).build();
    }

    public static CompProtobufs.Request createGetAllEventsRequest() {
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetAllEvents).build();
    }

    public static CompProtobufs.Request createGetChildrenByEventRequest() {
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetChildrenByEvent).build();
    }

    public static CompProtobufs.Request createGetChildrenFromAgeGroupAndEventRequest(AgeGroup ageGroup, Event event) {
        CompProtobufs.AgeGroup ageGroupProto = CompProtobufs.AgeGroup.newBuilder()
                .setMinAge(ageGroup.getMinAge())
                .setMaxAge(ageGroup.getMaxAge()).setId(ageGroup.getId().toString())
                .build();
        CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                .setId(event.getId().toString())
                .setMeters(event.getMeters())
                .build();
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetChildrenFromAgeGroupAndEvent)
                .setAgeGroup(ageGroupProto).setEvent(eventProto).build();
    }

    public static CompProtobufs.Request createGetChildrenRequest() {
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetChildren).build();
    }

    public static CompProtobufs.Request createGetEventsByChildrenRequest() {
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetEventsByChildren).build();
    }

    public static CompProtobufs.Request createGetEventTypesForAgeGroupRequest(AgeGroup ageGroup) {
        CompProtobufs.AgeGroup ageGroupProto = CompProtobufs.AgeGroup.newBuilder()
                .setMinAge(ageGroup.getMinAge())
                .setMaxAge(ageGroup.getMaxAge()).setId(ageGroup.getId().toString())
                .build();
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.GetEventTypesForAgeGroup)
                .setAgeGroup(ageGroupProto).build();
    }

    public static CompProtobufs.Request createLoginRequest(Organiser organiser) {
        CompProtobufs.Organiser organiserProto = CompProtobufs.Organiser.newBuilder()
                .setId(organiser.getId().toString())
                .setUsername(organiser.getUsername())
                .setPassword(organiser.getPassword())
                .build();
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.Login)
                .setOrganiser(organiserProto).build();
    }

    public static CompProtobufs.Request createLogoutRequest(Organiser organiser) {
        CompProtobufs.Organiser organiserProto = CompProtobufs.Organiser.newBuilder()
                .setId(organiser.getId().toString())
                .setUsername(organiser.getUsername())
                .setPassword(organiser.getPassword())
                .build();
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.Logout)
                .setOrganiser(organiserProto).build();
    }

    public static CompProtobufs.Request createRegisterChildRequest(String name, int age, Event event) {
        CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                .setId(event.getId().toString())
                .setMeters(event.getMeters()).build();
        return CompProtobufs.Request.newBuilder().setType(CompProtobufs.Request.Type.RegisterChild)
                .setAge(age).setName(name).setEvent(eventProto).build();
    }

    public static CompProtobufs.Response createOkResponse() {
        return CompProtobufs.Response.newBuilder().setType(CompProtobufs.Response.Type.Ok).build();
    }

    public static CompProtobufs.Response createErrorResponse(String message) {
        return CompProtobufs.Response.newBuilder().setType(CompProtobufs.Response.Type.Error)
                .setError(message).build();
    }

    public static CompProtobufs.Response createGetAgeGroupsResponse(AgeGroup[] ageGroups) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetAgeGroups);
        for(AgeGroup ageGroup : ageGroups) {
            CompProtobufs.AgeGroup ageGroupProto = CompProtobufs.AgeGroup.newBuilder()
                    .setMinAge(ageGroup.getMinAge())
                    .setMaxAge(ageGroup.getMaxAge()).setId(ageGroup.getId().toString())
                    .build();
            response.addAgeGroups(ageGroupProto);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetAllEventsResponse(Event[] events) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetAllEvents);
        for(Event event : events) {
            CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                    .setId(event.getId().toString())
                    .setMeters(event.getMeters()).build();
            response.addEvents(eventProto);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetChildrenByEventResponse(HashMap<AllowedGroup, Integer> counts) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetChildrenByEvent);
        for(Map.Entry<AllowedGroup, Integer> entry : counts.entrySet()) {
            AllowedGroup allowedGroup = entry.getKey();
            Integer count = entry.getValue();
            CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                    .setId(allowedGroup.getEvent().getId().toString())
                    .setMeters(allowedGroup.getEvent().getMeters()).build();
            CompProtobufs.AgeGroup ageGroupProto = CompProtobufs.AgeGroup.newBuilder()
                    .setId(allowedGroup.getAgeGroup().getId().toString())
                    .setMinAge(allowedGroup.getAgeGroup().getMinAge())
                    .setMaxAge(allowedGroup.getAgeGroup().getMaxAge()).build();
            CompProtobufs.AllowedGroup allowedGroupProto = CompProtobufs.AllowedGroup.newBuilder()
                    .setId(allowedGroup.getId().toString())
                    .setAgeGroup(ageGroupProto).setEvent(eventProto).build();

            response.addAllowedGroups(allowedGroupProto);
            response.addCounts(count);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetChildrenFromAgeGroupAndEventResponse(Child[] children) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetChildrenFromAgeGroupAndEvent);
        for(Child child : children) {
            CompProtobufs.Child childProto = CompProtobufs.Child.newBuilder()
                    .setId(child.getId().toString())
                    .setName(child.getName())
                    .setAge(child.getAge()).build();
            response.addChildren(childProto);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetChildrenResponse(Child[] children) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetChildren);
        for(Child child : children) {
            CompProtobufs.Child childProto = CompProtobufs.Child.newBuilder()
                    .setId(child.getId().toString())
                    .setName(child.getName())
                    .setAge(child.getAge()).build();
            response.addChildren(childProto);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetEventsByChildrenResponse(HashMap<Child, Integer> counts) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetEventsByChildren);
        for(Map.Entry<Child, Integer> entry : counts.entrySet()) {
            Child child = entry.getKey();
            Integer count = entry.getValue();
            CompProtobufs.Child childProto = CompProtobufs.Child.newBuilder()
                    .setId(child.getId().toString())
                    .setName(child.getName())
                    .setAge(child.getAge()).build();
            response.addChildren(childProto);
            response.addCounts(count);
        }
        return response.build();
    }

    public static CompProtobufs.Response createGetEventTypesForAgeGroupResponse(Event[] events) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.GetEventTypesForAgeGroup);
        for(Event event : events) {
            CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                    .setId(event.getId().toString())
                    .setMeters(event.getMeters()).build();
            response.addEvents(eventProto);
        }
        return response.build();
    }

    public static CompProtobufs.Response createChildRegisteredRespose(Child child, AllowedGroup allowedGroup) {
        CompProtobufs.Response.Builder response = CompProtobufs.Response.newBuilder()
                .setType(CompProtobufs.Response.Type.ChildRegistered);
        CompProtobufs.Child childProto = CompProtobufs.Child.newBuilder()
                .setId(child.getId().toString())
                .setName(child.getName())
                .setAge(child.getAge()).build();
        CompProtobufs.Event eventProto = CompProtobufs.Event.newBuilder()
                .setId(allowedGroup.getEvent().getId().toString())
                .setMeters(allowedGroup.getEvent().getMeters()).build();
        CompProtobufs.AgeGroup ageGroupProto = CompProtobufs.AgeGroup.newBuilder()
                .setId(allowedGroup.getAgeGroup().getId().toString())
                .setMinAge(allowedGroup.getAgeGroup().getMinAge())
                .setMaxAge(allowedGroup.getAgeGroup().getMaxAge()).build();
        CompProtobufs.AllowedGroup allowedGroupProto = CompProtobufs.AllowedGroup.newBuilder()
                .setId(allowedGroup.getId().toString())
                .setAgeGroup(ageGroupProto).setEvent(eventProto).build();
        response.setChild(childProto);
        response.setAllowedGroup(allowedGroupProto);
        return response.build();
    }

    public static AgeGroup getAgeGroup(CompProtobufs.Request request) {
        CompProtobufs.AgeGroup ageGroupProto = request.getAgeGroup();
        return new AgeGroup(UUID.fromString(ageGroupProto.getId()), ageGroupProto.getMinAge(), ageGroupProto.getMaxAge());
    }

    public static Event getEvent(CompProtobufs.Request request) {
        CompProtobufs.Event eventProto = request.getEvent();
        return new Event(UUID.fromString(eventProto.getId()), eventProto.getMeters());
    }

    public static Organiser getOrganiser(CompProtobufs.Request request) {
        CompProtobufs.Organiser organiserProto = request.getOrganiser();
        return new Organiser(UUID.fromString(organiserProto.getId()), organiserProto.getUsername(), organiserProto.getPassword());
    }

    public static Child getChild(CompProtobufs.Response response) {
        CompProtobufs.Child childProto = response.getChild();
        return new Child(UUID.fromString(childProto.getId()), childProto.getName(), childProto.getAge());
    }

    public static AllowedGroup getAllowedGroup(CompProtobufs.Response response) {
        CompProtobufs.AllowedGroup allowedGroupProto = response.getAllowedGroup();
        AgeGroup ageGroup = new AgeGroup(UUID.fromString(allowedGroupProto.getAgeGroup().getId()), allowedGroupProto.getAgeGroup().getMinAge(), allowedGroupProto.getAgeGroup().getMaxAge());
        Event event = new Event(UUID.fromString(allowedGroupProto.getEvent().getId()), allowedGroupProto.getEvent().getMeters());
        return new AllowedGroup(UUID.fromString(allowedGroupProto.getId()), ageGroup, event);
    }

    public static AgeGroup[] getAgeGroups(CompProtobufs.Response response) {
        AgeGroup[] ageGroups = new AgeGroup[response.getAgeGroupsList().size()];

        for(int i=0; i<ageGroups.length; i++) {
            CompProtobufs.AgeGroup ageGroupProto = response.getAgeGroupsList().get(i);
            AgeGroup ageGroup = new AgeGroup(UUID.fromString(ageGroupProto.getId()), ageGroupProto.getMinAge(), ageGroupProto.getMaxAge());
            ageGroups[i] = ageGroup;
        }

        return ageGroups;
    }

    public static Event[] getEvents(CompProtobufs.Response response) {
        Event[] events = new Event[response.getEventsList().size()];

        for(int i=0; i<events.length; i++) {
            CompProtobufs.Event eventProto = response.getEventsList().get(i);
            Event event = new Event(UUID.fromString(eventProto.getId()), eventProto.getMeters());
            events[i] = event;
        }

        return events;
    }

    public static Child[] getChildren(CompProtobufs.Response response) {
        Child[] children = new Child[response.getChildrenList().size()];

        for(int i=0; i<children.length; i++) {
            CompProtobufs.Child childProto = response.getChildrenList().get(i);
            Child child = new Child(UUID.fromString(childProto.getId()), childProto.getName(), childProto.getAge());
            children[i] = child;
        }

        return children;
    }

    public static AllowedGroup[] getAllowedGroups(CompProtobufs.Response response) {
        AllowedGroup[] allowedGroups = new AllowedGroup[response.getAllowedGroupsList().size()];

        for(int i=0; i<allowedGroups.length; i++) {
            CompProtobufs.AllowedGroup allowedGroupProto = response.getAllowedGroupsList().get(i);
            AgeGroup ageGroup = new AgeGroup(UUID.fromString(allowedGroupProto.getAgeGroup().getId()), allowedGroupProto.getAgeGroup().getMinAge(), allowedGroupProto.getAgeGroup().getMaxAge());
            Event event = new Event(UUID.fromString(allowedGroupProto.getEvent().getId()), allowedGroupProto.getEvent().getMeters());
            AllowedGroup allowedGroup = new AllowedGroup(UUID.fromString(allowedGroupProto.getId()), ageGroup, event);
            allowedGroups[i] = allowedGroup;
        }

        return allowedGroups;
    }

    public static Integer[] getCounts(CompProtobufs.Response response) {
        Integer[] counts = new Integer[response.getCountsList().size()];

        for(int i=0; i<counts.length; i++) {
            counts[i] = response.getCountsList().get(i);
        }

        return counts;
    }
}
