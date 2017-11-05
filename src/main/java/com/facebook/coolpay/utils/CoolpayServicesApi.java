package com.facebook.coolpay.utils;

import java.util.List;

import com.facebook.coolpay.json.login.LoginPayload;
import com.facebook.coolpay.json.payment.PaymenListResponse;
import com.facebook.coolpay.json.payment.Payment;
import com.facebook.coolpay.json.payment.PaymentPayload;
import com.facebook.coolpay.json.recipient.Recipient;
import com.facebook.coolpay.json.recipient.RecipientPayload;

public interface CoolpayServicesApi {

	String login(LoginPayload request);
    List<Recipient> getRecipientList(String authorizationToken, String name);
    Recipient addRecipient(String authorizationToken, RecipientPayload request);
    Payment createPayment(String authorizationToken, PaymentPayload request);
    PaymenListResponse getPaymentList(String authorizationToken);	
}
