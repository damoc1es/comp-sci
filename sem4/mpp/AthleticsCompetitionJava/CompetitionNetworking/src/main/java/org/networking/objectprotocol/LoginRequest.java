package org.networking.objectprotocol;

import org.networking.dto.OrganiserDTO;

public class LoginRequest implements Request {
    private OrganiserDTO organiser;

    public LoginRequest(OrganiserDTO organiser) {
        this.organiser = organiser;
    }

    public OrganiserDTO getOrganiser() {
        return organiser;
    }
}
