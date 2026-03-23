package org.example.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record User(
        String id,
        String email,
        String name,
        String surname,
        String role,
        String position,
        String status, 
        Boolean isReport,
        String comment,
        String createBy,
        String report,
        String updatedAt,
        String createdAt,
        Boolean feedback,
        String finishedAt,
        String linkedin,
        String city,
        String jira
) {
}
