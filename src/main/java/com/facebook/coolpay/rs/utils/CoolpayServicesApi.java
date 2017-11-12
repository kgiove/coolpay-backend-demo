package com.facebook.coolpay.rs.utils;
/**
 * @author KGiove
 */
import java.util.List;

import com.facebook.coolpay.rs.json.login.LoginPayload;
import com.facebook.coolpay.rs.json.login.LoginResponse;
import com.facebook.coolpay.rs.json.payment.PaymenListResponse;
import com.facebook.coolpay.rs.json.payment.Payment;
import com.facebook.coolpay.rs.json.payment.PaymentPayload;
import com.facebook.coolpay.rs.json.recipient.Recipient;
import com.facebook.coolpay.rs.json.recipient.RecipientPayload;

public interface CoolpayServicesApi {

	LoginResponse login(LoginPayload request);
    List<Recipient> getRecipientList(String authorizationToken, String name);
    Recipient addRecipient(String authorizationToken, RecipientPayload request);
    Payment createPayment(String authorizationToken, PaymentPayload request);
    PaymenListResponse getPaymentList(String authorizationToken);
}
