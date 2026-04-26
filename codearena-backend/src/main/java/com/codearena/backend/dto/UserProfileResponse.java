package com.codearena.backend.dto;

public class UserProfileResponse {

    private String username;
    private String email;

    public UserProfileResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
