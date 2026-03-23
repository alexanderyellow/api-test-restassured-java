package org.example.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlayerResponse(
        @JsonAlias({"_id", "id"})
        @JsonProperty("_id")
        String id,
        String username,
        String email,
        String name,
        String surname,
        String currencyCode,
        String passwordChange,
        String passwordRepeat,
        @JsonProperty("__v")
        Integer v
) {
}
