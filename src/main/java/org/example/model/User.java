package org.example.model;

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
        String updated_at,
        String created_at,
        Boolean feedback,
        String finished_at,
        String linkedin,
        String city,
        String jira
) {
}
