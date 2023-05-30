package org.server;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.DuplicatedException;
import org.model.exception.RegistrationException;
import org.server.repository.*;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CompetitionServicesImpl implements ICompetitionServices {
    private final AgeGroupsRepo ageGroupsRepo;
    private final EventsRepo eventsRepo;
    private final AllowedGroupsRepo allowedGroupsRepo;
    private final ChildrenRepo childrenRepo;
    private final OrganisersRepo organisersRepo;
    private final RegistrationsRepo registrationsRepo;
    private Map<String, ICompetitionObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public CompetitionServicesImpl(AgeGroupsRepo ageGroupsRepo, EventsRepo eventsRepo, AllowedGroupsRepo allowedGroupsRepo, ChildrenRepo childrenRepo, OrganisersRepo organisersRepo, RegistrationsRepo registrationsRepo) {
        this.ageGroupsRepo = ageGroupsRepo;
        this.eventsRepo = eventsRepo;
        this.allowedGroupsRepo = allowedGroupsRepo;
        this.childrenRepo = childrenRepo;
        this.organisersRepo = organisersRepo;
        this.registrationsRepo = registrationsRepo;

        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Organiser org, ICompetitionObserver client) throws CompException {
        Organiser organiser = organisersRepo.findByUsername(org.getUsername());

        if(organiser != null) {
            if(organiser.getPassword().equals(org.getPassword())) {
                if(loggedClients.get(organiser.getUsername()) != null)
                    throw new CompException("Organiser already logged in.");
                loggedClients.put(organiser.getUsername(), client);
            }
            else throw new CompException("Wrong password.");
        }
        else throw new CompException("Organiser not found.");
    }

    @Override
    public synchronized void logout(Organiser organiser, ICompetitionObserver client) throws CompException {
        ICompetitionObserver localClient = loggedClients.remove(organiser.getUsername());
        if(localClient == null)
            throw new CompException("Organiser " + organiser.getId() + " is not logged in.");
    }

    @Override
    public AgeGroup[] getAgeGroups() {
        List<AgeGroup> ageGroupList = (List<AgeGroup>) ageGroupsRepo.getAll();
        AgeGroup[] ageGroups = new AgeGroup[ageGroupList.size()];
        for(int i = 0; i < ageGroupList.size(); i++)
            ageGroups[i] = ageGroupList.get(i);
        return ageGroups;
    }

    @Override
    public Event[] getEventTypesForAgeGroup(AgeGroup ageGroup) {
        List<Event> eventList = new ArrayList<>();
        for(AllowedGroup group : allowedGroupsRepo.findByAgeGroupId(ageGroup.getId()))
            eventList.add(group.getEvent());

        Event[] events = new Event[eventList.size()];
        for(int i = 0; i < eventList.size(); i++)
            events[i] = eventList.get(i);
        return events;
    }

    @Override
    public Integer getEventsByChild(Child child) {
        return registrationsRepo.countEventsWhereChildParticipates(child);
    }

    @Override
    public HashMap<Child, Integer> getEventsByChildren() throws CompException {
        HashMap<Child,Integer> eventsByChildren = new HashMap<>();
        for (Child child : childrenRepo.getAll()) {
            eventsByChildren.put(child, registrationsRepo.countEventsWhereChildParticipates(child));
        }
        return eventsByChildren;
    }

    @Override
    public HashMap<AllowedGroup, Integer> getChildrenByEvent() throws CompException {
        HashMap<AllowedGroup,Integer> childrenByEvent = new HashMap<>();
        for (AgeGroup ageGroup : ageGroupsRepo.getAll()) {
            for(AllowedGroup allowedGroup : allowedGroupsRepo.findByAgeGroupId(ageGroup.getId()))
                childrenByEvent.put(allowedGroup, registrationsRepo.fromEventAndAgeGroup(ageGroup, allowedGroup.getEvent()).size());
        }
        return childrenByEvent;
    }

    @Override
    public Child[] getChildren() {
        List<Child> childrenLst = (List<Child>) childrenRepo.getAll();
        Child[] children = new Child[childrenLst.size()];
        for(int i = 0; i < childrenLst.size(); i++)
            children[i] = childrenLst.get(i);
        return children;
    }

    @Override
    public Child[] getChildrenFromAgeGroupAndEvent(AgeGroup ageGroup, Event event) {
        List<Child> childrenLst = new ArrayList<>();
        for(Registration reg : registrationsRepo.fromEventAndAgeGroup(ageGroup, event))
            childrenLst.add(reg.getChild());
        Child[] children = new Child[childrenLst.size()];
        for(int i = 0; i < childrenLst.size(); i++)
            children[i] = childrenLst.get(i);
        return children;
    }

    @Override
    public Event[] getAllEvents() {
        List<Event> eventsLst = (List<Event>) eventsRepo.getAll();
        Event[] events = new Event[eventsLst.size()];
        for (int i = 0; i < eventsLst.size(); i++)
            events[i] = eventsLst.get(i);
        return events;
    }

    @Override
    public synchronized void register(String name, Integer age, Event event) throws RegistrationException {
        String errMsg = "";
        if(name.equals("")) {
            errMsg += "Name can't be empty. ";
        }
        if(age == 0) {
            errMsg += "Age can't be 0. ";
        }
        if(event == null) {
            errMsg += "Event must be selected.";
        }

        if(!errMsg.equals("")) {
            throw new RegistrationException(errMsg);
        }

        Child child = childrenRepo.findChildByNameAndAge(name, age);
        if(child == null) {
            //throw new RegistrationException("Copilul nu a fost gasit.");
            child = new Child(UUID.randomUUID(), name, age);
            try {
                childrenRepo.store(child);
            } catch (DuplicatedException ignored) {
            }
        }

        AllowedGroup allowedGroup = null;
        for(AllowedGroup al : allowedGroupsRepo.findByEventId(event.getId())) {
            if(al.getAgeGroup().getMinAge() <= age && age <= al.getAgeGroup().getMaxAge()) {
                allowedGroup = al;
                break;
            }
        }

        if(allowedGroup == null) {
            throw new RegistrationException("Copilul nu se potriveste in nicio grupa de varsta pentru aceasta proba.");
        }

        Registration registration = new Registration(UUID.randomUUID(), child, allowedGroup);
        try {
            registrationsRepo.store(registration);
            try {
                for (ICompetitionObserver client : loggedClients.values()) {
                    client.childRegistered(child, allowedGroup);
                }
            } catch (CompException e) {
                e.printStackTrace();
            }
        } catch (DuplicatedException e) {
            throw new RegistrationException("Copilul participa deja la proba.");
        }
    }

    @Override
    public int getNumberOfChildrenFromEvent(AgeGroup ageGroup, Event event) {
        return registrationsRepo.fromEventAndAgeGroup(ageGroup, event).size();
    }
}
