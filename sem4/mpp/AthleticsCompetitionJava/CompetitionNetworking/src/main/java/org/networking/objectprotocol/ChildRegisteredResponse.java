package org.networking.objectprotocol;

import org.networking.dto.AllowedGroupDTO;
import org.networking.dto.ChildDTO;
import org.networking.dto.EventDTO;

public class ChildRegisteredResponse implements UpdateResponse {
    private ChildDTO registeredChild;
    private AllowedGroupDTO allowedGroup;

    public ChildRegisteredResponse(ChildDTO registeredChild, AllowedGroupDTO allowedGroup) {
        this.registeredChild = registeredChild;
        this.allowedGroup = allowedGroup;
    }

    public ChildDTO getChild() {
        return registeredChild;
    }

    public AllowedGroupDTO getAllowedGroup() {
        return allowedGroup;
    }
}
