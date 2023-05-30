package org.services;

import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;

import java.util.HashMap;

public interface ICompetitionServices {
    void login(Organiser org, ICompetitionObserver client) throws CompException;
    void logout(Organiser organiser, ICompetitionObserver client) throws CompException;
    AgeGroup[] getAgeGroups() throws CompException;
    Event[] getEventTypesForAgeGroup(AgeGroup ageGroup) throws CompException;
    Integer getEventsByChild(Child child) throws CompException;
    HashMap<Child, Integer> getEventsByChildren() throws CompException;
    HashMap<AllowedGroup, Integer> getChildrenByEvent() throws CompException;
    Child[] getChildren() throws CompException;
    Child[] getChildrenFromAgeGroupAndEvent(AgeGroup ageGroup, Event event) throws CompException;
    Event[] getAllEvents() throws CompException;
    void register(String name, Integer age, Event event) throws RegistrationException, CompException;
    int getNumberOfChildrenFromEvent(AgeGroup ageGroup, Event event) throws CompException;
}
