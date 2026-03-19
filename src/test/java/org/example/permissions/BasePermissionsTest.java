package org.example.permissions;

import org.example.BaseTest;
import org.example.actors.Actor;
import org.example.model.CredentialsDTO;
import org.junit.jupiter.api.BeforeAll;


public class BasePermissionsTest extends BaseTest {
    protected Actor notAuthenticatedActor;

    @BeforeAll
    public void createNotAuthenticatedActor() {
        CredentialsDTO credentials = new CredentialsDTO(config.getUserEmail(), config.getUserPassword());
        notAuthenticatedActor = new Actor(config, credentials);
    }
}
