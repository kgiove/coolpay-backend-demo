package com.facebook.coolpay.rs;

import static com.facebook.coolpay.rs.utils.CoolpayApiUtils.prepareJsonPayload;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.facebook.coolpay.rs.json.login.LoginPayload;
import com.facebook.coolpay.rs.json.login.LoginResponse;
import com.facebook.coolpay.rs.json.payment.PaymenListResponse;
import com.facebook.coolpay.rs.json.payment.Payment;
import com.facebook.coolpay.rs.json.payment.PaymentPayload;
import com.facebook.coolpay.rs.json.recipient.Recipient;
import com.facebook.coolpay.rs.json.recipient.RecipientListResponse;
import com.facebook.coolpay.rs.json.recipient.RecipientPayload;

public class MockStubRestApiService {
	
	protected static final String PAYMENT_ID = "ca44-4cae-a544";
	protected static final BigDecimal PAYMENT_AMOUNT = new BigDecimal("15.00");	
	protected static final String PAYMENT_CURRENCY = "GBP";
	protected static final String PAYMENT_STATUS = "processing";	
	protected static final String USER_NAME = "Kgiove";
	protected static final String API_KEY = "mockApi";
	protected static final String TOKEN = "b5aba7e3-ca44-4cae-a544-73bd4e1b40d6";
	protected static final String RECIPIENT_ID = "b5aba7e3";
	protected static final String RECIPIENT_NAME = "Jake McFriend";
	protected static final String INVALID_TOKEN = "invalid_token";
	protected static final String INVALID_API_KEY = "invalid_api_key";
	protected static final int WIREMOCK_PORT = 9999;
	
	public static LoginPayload createLoginRequest(String username, String apikey){
		LoginPayload loginPayload = new LoginPayload();        
		loginPayload.setUsername(username);
		loginPayload.setApikey(apikey);
        return loginPayload;
	}
	
	public static RecipientPayload createRecipientPayloadRequest(){
		RecipientPayload recipientPayload = new RecipientPayload();
		Recipient recipient = new Recipient();
		recipient.setName(RECIPIENT_NAME);
		recipientPayload.setRecipient(recipient);
		return recipientPayload;
	}
	
	public static PaymentPayload createPaymentPayloadRequest(String recipient_id){
		PaymentPayload paymentPayload = new PaymentPayload();
		Payment payment = new Payment();
		payment.setAmount(PAYMENT_AMOUNT);
		payment.setCurrency(PAYMENT_CURRENCY);
		payment.setRecipient_id(recipient_id);
		paymentPayload.setPayment(payment);
		return paymentPayload;
	}
	
	public static PaymentPayload createPaymentPayloadRequest(){
		PaymentPayload paymentPayload = new PaymentPayload();
		Payment payment = new Payment();
		payment.setAmount(PAYMENT_AMOUNT);
		payment.setCurrency(PAYMENT_CURRENCY);
		payment.setRecipient_id(RECIPIENT_ID);
		paymentPayload.setPayment(payment);
		return paymentPayload;
	}
	
	public static String createJsonGetLoginSuccess(){
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(TOKEN);
		return prepareJsonPayload(loginResponse);
	}
	
	 public static String createJsonRecipientSuccess(){
		 RecipientPayload recipientBody = new RecipientPayload();		
		 Recipient recipient = new Recipient();
		 recipient.setId(RECIPIENT_ID);
		 recipient.setName(RECIPIENT_NAME);
		 recipientBody.setRecipient(recipient);
		 return prepareJsonPayload(recipientBody); 
	 }
	 
	 public static String createJsonPaymentSuccess(){
		 PaymentPayload paymentPayload = new PaymentPayload();
		 Payment payment = new Payment();
		 payment.setId(PAYMENT_ID);
		 payment.setAmount(PAYMENT_AMOUNT);
		 payment.setRecipient_id(RECIPIENT_ID);
		 payment.setCurrency(PAYMENT_CURRENCY);
		 payment.setStatus(PAYMENT_STATUS);
		 paymentPayload.setPayment(payment);
		 return prepareJsonPayload(paymentPayload);
	 }
	
	public static String createJsongetRecipientListSuccess(){
		RecipientListResponse recipientListResponse = new RecipientListResponse();
		List<Recipient> recipients = new ArrayList<Recipient>();
		Recipient recipient = new Recipient();
		recipient.setId(RECIPIENT_ID);
		recipient.setName(RECIPIENT_NAME);
		recipients.add(recipient);
		recipientListResponse.setRecipients(recipients);
		return prepareJsonPayload(recipientListResponse);
	}	
	
	public static String createJsonGetPaymentListSuccess(){
		
		PaymenListResponse paymenListResponse = new PaymenListResponse();
		List<Payment> payments = new ArrayList<Payment>();
		Payment payment = new Payment();
		payment.setAmount(PAYMENT_AMOUNT);
		payment.setCurrency(PAYMENT_CURRENCY);
		payment.setRecipient_id(RECIPIENT_ID);
		payment.setStatus(PAYMENT_STATUS);
		payment.setId(PAYMENT_ID);
		payments.add(payment);
		paymenListResponse.setPayments(payments);
		return prepareJsonPayload(paymenListResponse);		
	}
}
