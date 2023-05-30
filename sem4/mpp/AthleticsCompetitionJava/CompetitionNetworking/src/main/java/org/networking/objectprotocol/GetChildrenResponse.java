package org.networking.objectprotocol;

import org.networking.dto.ChildDTO;

import java.util.List;

public class GetChildrenResponse implements Response {
    private ChildDTO[] childrenDTO;

    public GetChildrenResponse(ChildDTO[] childrenDTO) {
        this.childrenDTO = childrenDTO;
    }

    public ChildDTO[] getChildren() {
        return childrenDTO;
    }
}
