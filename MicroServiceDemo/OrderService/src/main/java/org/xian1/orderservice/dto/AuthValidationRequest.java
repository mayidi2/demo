package org.xian1.orderservice.dto;

public class AuthValidationRequest {
    private String username;
    private String password;

    public AuthValidationRequest() {
    }

    public AuthValidationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
