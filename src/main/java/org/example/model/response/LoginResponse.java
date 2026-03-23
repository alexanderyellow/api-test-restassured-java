package org.example.model.response;

public record LoginResponse(
        User user,
        String accessToken
) {
}
