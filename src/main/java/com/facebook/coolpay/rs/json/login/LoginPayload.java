package com.facebook.coolpay.rs.json.login;
/**
 * @author KGiove
 */
public class LoginPayload {

    private String username;
    private String apikey;

    public String getUsername() {
        return username;
    }

    public String getApikey() {
        return apikey;
    }

	public void setUsername(String username) {
		this.username = username;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
    
}
