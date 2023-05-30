package org.networking.objectprotocol;

import org.networking.dto.AgeGroupDTO;

public class GetEventTypesForAgeGroupRequest implements Request {
    private AgeGroupDTO ageGroup;

    public GetEventTypesForAgeGroupRequest(AgeGroupDTO ageGroup) {
        this.ageGroup = ageGroup;
    }

    public AgeGroupDTO getAgeGroup() {
        return ageGroup;
    }
}
