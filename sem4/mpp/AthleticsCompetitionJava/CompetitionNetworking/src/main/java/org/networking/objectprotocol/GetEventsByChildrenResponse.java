package org.networking.objectprotocol;

import org.networking.dto.ChildDTO;

import java.util.HashMap;

public class GetEventsByChildrenResponse implements Response {
    private HashMap<ChildDTO, Integer> map;

    public GetEventsByChildrenResponse(HashMap<ChildDTO, Integer> map) {
        this.map = map;
    }

    public HashMap<ChildDTO, Integer> getEventCounts() {
        return map;
    }
}
