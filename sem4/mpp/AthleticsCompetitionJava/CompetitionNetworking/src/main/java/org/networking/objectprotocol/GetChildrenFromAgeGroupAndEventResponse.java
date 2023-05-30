package org.networking.objectprotocol;

import org.networking.dto.ChildDTO;

public class GetChildrenFromAgeGroupAndEventResponse implements Response {
    private ChildDTO[] children;

    public GetChildrenFromAgeGroupAndEventResponse(ChildDTO[] children) {
        this.children = children;
    }

    public ChildDTO[] getChildren() {
        return children;
    }
}
