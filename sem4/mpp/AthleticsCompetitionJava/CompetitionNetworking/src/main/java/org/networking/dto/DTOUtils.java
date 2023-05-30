package org.networking.dto;

import org.model.*;

import java.util.ArrayList;
import java.util.List;

public class DTOUtils {
    public static AgeGroup getFromDTO(AgeGroupDTO ageGroupDTO) {
        return new AgeGroup(ageGroupDTO.getId(), ageGroupDTO.getMinAge(), ageGroupDTO.getMaxAge());
    }

    public static AgeGroupDTO getDTO(AgeGroup ageGroup) {
        return new AgeGroupDTO(ageGroup.getId(), ageGroup.getMinAge(), ageGroup.getMaxAge());
    }

    public static Event getFromDTO(EventDTO eventDTO) {
        return new Event(eventDTO.getId(), eventDTO.getMeters());
    }

    public static EventDTO getDTO(Event event) {
        return new EventDTO(event.getId(), event.getMeters());
    }

    public static Child getFromDTO(ChildDTO childDTO) {
        return new Child(childDTO.getId(), childDTO.getName(), childDTO.getAge());
    }

    public static ChildDTO getDTO(Child child) {
        return new ChildDTO(child.getId(), child.getName(), child.getAge());
    }

    public static Organiser getFromDTO(OrganiserDTO organiserDTO) {
        return new Organiser(organiserDTO.getId(), organiserDTO.getUsername(), organiserDTO.getPassword());
    }

    public static OrganiserDTO getDTO(Organiser organiser) {
        return new OrganiserDTO(organiser.getId(), organiser.getUsername(), organiser.getPassword());
    }

    public static AllowedGroup getFromDTO(AllowedGroupDTO allowedGroupDTO) {
        return new AllowedGroup(allowedGroupDTO.getId(), DTOUtils.getFromDTO(allowedGroupDTO.getAgeGroup()), DTOUtils.getFromDTO(allowedGroupDTO.getEvent()));
    }

    public static AllowedGroupDTO getDTO(AllowedGroup allowedGroup) {
        return new AllowedGroupDTO(allowedGroup.getId(), DTOUtils.getDTO(allowedGroup.getAgeGroup()), DTOUtils.getDTO(allowedGroup.getEvent()));
    }

    public static Child[] getFromDTO(ChildDTO[] childrenDTO) {
        Child[] children = new Child[childrenDTO.length];
        for(int i = 0; i < childrenDTO.length; i++) {
            children[i] = DTOUtils.getFromDTO(childrenDTO[i]);
        }
        return children;
    }

    public static ChildDTO[] getDTO(Child[] children) {
        ChildDTO[] childrenDTO = new ChildDTO[children.length];
        for(int i = 0; i < children.length; i++) {
            childrenDTO[i] = DTOUtils.getDTO(children[i]);
        }
        return childrenDTO;
    }

    public static AgeGroup[] getFromDTO(AgeGroupDTO[] ageGroupDTO) {
        AgeGroup[] ageGroups = new AgeGroup[ageGroupDTO.length];
        for(int i = 0; i < ageGroupDTO.length; i++) {
            ageGroups[i] = DTOUtils.getFromDTO(ageGroupDTO[i]);
        }
        return ageGroups;
    }

    public static AgeGroupDTO[] getDTO(AgeGroup[] ageGroups) {
        AgeGroupDTO[] ageGroupDTO = new AgeGroupDTO[ageGroups.length];
        for(int i = 0; i < ageGroupDTO.length; i++) {
            ageGroupDTO[i] = DTOUtils.getDTO(ageGroups[i]);
        }
        return ageGroupDTO;
    }

    public static Event[] getFromDTO(EventDTO[] eventDTO) {
        Event[] events = new Event[eventDTO.length];
        for(int i = 0; i < eventDTO.length; i++) {
            events[i] = DTOUtils.getFromDTO(eventDTO[i]);
        }
        return events;
    }

    public static EventDTO[] getDTO(Event[] events) {
        EventDTO[] eventDTO = new EventDTO[events.length];
        for(int i = 0; i < eventDTO.length; i++) {
            eventDTO[i] = DTOUtils.getDTO(events[i]);
        }
        return eventDTO;
    }
}
