package com.facebook.coolpay.rs;

import static com.facebook.coolpay.rs.MockStubRestApiService.createPaymentPayloadRequest;
import static com.facebook.coolpay.rs.MockStubRestApiService.createRecipientPayloadRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.facebook.coolpay.rs.json.login.LoginResponse;
import com.facebook.coolpay.rs.json.payment.PaymenListResponse;
import com.facebook.coolpay.rs.json.payment.Payment;
import com.facebook.coolpay.rs.json.payment.PaymentPayload;
import com.facebook.coolpay.rs.json.recipient.Recipient;
import com.facebook.coolpay.rs.json.recipient.RecipientPayload;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonitorApiTest extends JerseyTest {
	private static String token;
	private static String recipient_id;
	private static String payment_id;	
	private static String username = "KevinG";
	private static String apikey = "BC230BA3C6AD681A";    
	
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(CoolpayApiServices.class);
	}
	
	@Test
	public void stage0_login() {		
		String body = "{\"username\":\"" + username + "\",\"apikey\":\"" + apikey + "\"}";
		Entity<String> payload = Entity.json(body);		
		Response response = target("services/login").request().post(payload);		
		assertEquals("should return status 200", 200, response.getStatus());
		assertNotNull("Should return the token", response.getEntity());
		LoginResponse loginResponse = response.readEntity(LoginResponse.class);
		setToken(loginResponse.getToken());		
	}	
	
	@Test
	public void stage1_addRecipient(){
		Entity<RecipientPayload> payload = Entity.json(createRecipientPayloadRequest());
		Response response = target("services/createRecipient/" + getToken()).request().post(payload);
		assertEquals("Should return status 200", 200, response.getStatus());
		assertNotNull("Should return recipient", response.getEntity());
		setRecipient_id(response.readEntity(Recipient.class).getId());
	}	
	
	@Test
	public void stage2_getRecipientList(){		
		Response response = target("services/recipients/" + getToken() ).request().get();
		assertEquals("Should return status 200", 200, response.getStatus());
		assertNotNull("Should return recipient list", response.getEntity());
		List<Recipient> recipients = response.readEntity(new GenericType<List<Recipient>>(){});		
		assertEquals("Should return recipient added before", true, recipients.stream().anyMatch(recipient -> recipient.getId().equals(getRecipient_id())));					
	}

	@Test
	public void  stage3_createPayment(){
		Entity<PaymentPayload> payload = Entity.json(createPaymentPayloadRequest(getRecipient_id()));
		Response response = target("services/createPayment/" + getToken()).request().post(payload);
		assertEquals("Should return status 200", 200, response.getStatus());
		assertNotNull("Should return payment", response.getEntity());
		setPayment_id(response.readEntity(Payment.class).getId());
	}
	
	@Test
	public void stage4_getPaymentList(){
		Response response = target("services/payments/" + getToken()).request().get();
		assertEquals("Should return status 200", 200, response.getStatus());
		assertNotNull("Should return recipients list", response.getEntity());
		PaymenListResponse paymenListResponse = response.readEntity(PaymenListResponse.class);
		assertEquals("Should return the payment added before", true, paymenListResponse.getPayments().stream().anyMatch(payment -> payment.getId().equals(getPayment_id())));
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		MonitorApiTest.token = token;
	}

	public static String getRecipient_id() {
		return recipient_id;
	}

	public static void setRecipient_id(String recipient_id) {
		MonitorApiTest.recipient_id = recipient_id;
	}

	public static String getPayment_id() {
		return payment_id;
	}

	public static void setPayment_id(String payment_id) {
		MonitorApiTest.payment_id = payment_id;
	}	
}
