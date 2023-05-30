package org.networking.objectprotocol;

import org.networking.dto.AllowedGroupDTO;

import java.util.HashMap;

public class GetChildrenByEventResponse implements Response {
    private HashMap<AllowedGroupDTO, Integer> counts;

    public GetChildrenByEventResponse(HashMap<AllowedGroupDTO, Integer> counts) {
        this.counts = counts;
    }

    public HashMap<AllowedGroupDTO, Integer> getCounts() {
        return counts;
    }
}
