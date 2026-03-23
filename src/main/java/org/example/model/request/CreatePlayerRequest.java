package org.example.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreatePlayerRequest {
    String currencyCode;
    String email;
    String name;
    String passwordChange;
    String passwordRepeat;
    String surname;
    String username;
}
