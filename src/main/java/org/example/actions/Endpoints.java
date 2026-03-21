package org.example.actions;

/// Central registry of all API endpoints.
/// Add new endpoints here when new services or resources are introduced — no new action classes needed.
public final class Endpoints {

    private Endpoints() {}

    // Auth
    public static final Endpoint LOGIN = new Endpoint(HttpMethod.POST, "/tester/login", 201);

    // Players
    public static final Endpoint CREATE_PLAYER  = new Endpoint(HttpMethod.POST,   "/automationTask/create",        201);
    public static final Endpoint GET_ALL_PLAYERS = new Endpoint(HttpMethod.GET,   "/automationTask/getAll",         200);
    public static final Endpoint GET_ONE_PLAYER  = new Endpoint(HttpMethod.POST,  "/automationTask/getOne",         201);
    public static final Endpoint DELETE_PLAYER   = new Endpoint(HttpMethod.DELETE, "/automationTask/deleteOne/{id}", 200);
}
