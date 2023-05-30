package org.networking.objectprotocol;

import org.networking.dto.AgeGroupDTO;

public class GetAgeGroupsResponse implements Response {
    private AgeGroupDTO[] ageGroups;

    public GetAgeGroupsResponse(AgeGroupDTO[] ageGroups) {
        this.ageGroups = ageGroups;
    }

    public AgeGroupDTO[] getAgeGroups() {
        return ageGroups;
    }
}
