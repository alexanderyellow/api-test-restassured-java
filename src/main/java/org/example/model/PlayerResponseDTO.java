package org.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerResponseDTO(
        @JsonAlias({"_id", "id"})
        @JsonProperty("_id")
        String id,
        String username,
        String email,
        String name,
        String surname,
        @JsonProperty("currency_code")
        String currencyCode,
        @JsonProperty("password_change")
        String passwordChange,
        @JsonProperty("password_repeat")
        String passwordRepeat,
        @JsonProperty("__v")
        Integer v
) {
}
