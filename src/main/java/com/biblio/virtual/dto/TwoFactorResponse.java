package com.biblio.virtual.dto;

public class TwoFactorResponse {

    private boolean requires2FA;
    private String username;
    private String message;
    private String token;
    private String role;

    public TwoFactorResponse(boolean requires2FA, String username, String message) {
        this.requires2FA = requires2FA;
        this.username = username;
        this.message = message;
    }

    public TwoFactorResponse(String token, String username, String role) {
        this.requires2FA = false;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public boolean isRequires2FA() {
        return requires2FA;
    }

    public void setRequires2FA(boolean requires2FA) {
        this.requires2FA = requires2FA;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
