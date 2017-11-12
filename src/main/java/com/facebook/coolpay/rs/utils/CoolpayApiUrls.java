package com.facebook.coolpay.rs.utils;

/**
 * @author KGiove
 */
public enum CoolpayApiUrls {
    LOGIN_API("/api/login"),
    RECIPIENT_API("/api/recipients"),
    PAYMENTS_API("/api/payments");

    private String endpoint;

    CoolpayApiUrls(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
