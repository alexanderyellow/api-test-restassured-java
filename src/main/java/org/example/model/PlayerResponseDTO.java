package org.example.model;

public record PlayerResponseDTO(
        String id,
        String username,
        String email,
        String name,
        String surname
) {
}
