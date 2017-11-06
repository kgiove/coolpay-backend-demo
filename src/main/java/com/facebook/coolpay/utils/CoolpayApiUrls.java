package com.facebook.coolpay.utils;

/**
 * @author KGiove
 */
public enum CoolpayApiUrls {
    LOGIN_API("https://coolpay.herokuapp.com/api/login"),
    RECIPIENT_API("https://coolpay.herokuapp.com/api/recipients"),
    PAYMENTS_API("https://coolpay.herokuapp.com/api/payments");

    private String endpoint;

    CoolpayApiUrls(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
