package org.example.permissions;

import org.example.BaseTest;
import org.example.actors.Actor;
import org.example.model.request.LoginRequest;
import org.junit.jupiter.api.BeforeAll;


public class BasePermissionsTest extends BaseTest {
    protected Actor notAuthenticatedActor;

    @BeforeAll
    public void createNotAuthenticatedActor() {
        LoginRequest credentials = new LoginRequest(config.auth().email(), config.auth().password());
        notAuthenticatedActor = new Actor(credentials);
    }
}
