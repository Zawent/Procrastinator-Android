package com.micasa.holamundo.model;

public class RespuestaLogin {
    private User user;
    private String access_token;
    private String token_type;

    private String error;

    public RespuestaLogin(User user, String access_token, String token_type) {
        this.user = user;
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public RespuestaLogin() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
