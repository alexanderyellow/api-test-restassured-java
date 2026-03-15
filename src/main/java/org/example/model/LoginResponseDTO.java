package org.example.model;

public record LoginResponseDTO(
        User user,
        String accessToken
) {
}
