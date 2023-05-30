package org.networking.objectprotocol;

import org.model.Organiser;
import org.networking.dto.OrganiserDTO;

import java.util.UUID;

public class LogoutRequest implements Request {
    private OrganiserDTO organiser;

    public LogoutRequest(OrganiserDTO organiser) {
        this.organiser = organiser;
    }

    public OrganiserDTO getOrganiser() { return organiser; }
}
