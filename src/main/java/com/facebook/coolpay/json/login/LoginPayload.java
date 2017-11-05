package com.facebook.coolpay.json.login;

public class LoginPayload {

    private final String username;
    private final String apikey;

    public LoginPayload(String username, String apikey) {
        this.username = username;
        this.apikey = apikey;
    }

    public String getUsername() {
        return username;
    }

    public String getApikey() {
        return apikey;
    }
}
