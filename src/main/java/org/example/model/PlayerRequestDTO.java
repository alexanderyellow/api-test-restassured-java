package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerRequestDTO {

    @JsonProperty("currency_code")
    private final String currencyCode;
    private final String email;
    private final String name;
    @JsonProperty("password_change")
    private final String passwordChange;
    @JsonProperty("password_repeat")
    private final String passwordRepeat;
    private final String surname;
    private final String username;

    private PlayerRequestDTO(PlayerRequestDTO.Builder builder) {
        this.currencyCode = builder.currencyCode;
        this.email = builder.email;
        this.name = builder.name;
        this.passwordChange = builder.passwordChange;
        this.passwordRepeat = builder.passwordRepeat;
        this.surname = builder.surname;
        this.username = builder.username;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPasswordChange() {
        return passwordChange;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public static PlayerRequestDTO.Builder builder() {
        return new PlayerRequestDTO.Builder();
    }

    public static class Builder {
        private String currencyCode;
        private String email;
        private String name;
        private String passwordChange;
        private String passwordRepeat;
        private String surname;
        private String username;

        public PlayerRequestDTO.Builder currencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public PlayerRequestDTO.Builder email(String email) {
            this.email = email;
            return this;
        }

        public PlayerRequestDTO.Builder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerRequestDTO.Builder passwordChange(String passwordChange) {
            this.passwordChange = passwordChange;
            return this;
        }

        public PlayerRequestDTO.Builder passwordRepeat(String passwordRepeat) {
            this.passwordRepeat = passwordRepeat;
            return this;
        }

        public PlayerRequestDTO.Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public PlayerRequestDTO.Builder username(String username) {
            this.username = username;
            return this;
        }

        public PlayerRequestDTO build() {
            return new PlayerRequestDTO(this);
        }
    }
}
