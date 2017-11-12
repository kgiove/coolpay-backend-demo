package com.facebook.coolpay.rs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.facebook.coolpay.rs.exception.CoolpayApiException;
import com.facebook.coolpay.rs.json.login.LoginResponse;
import com.facebook.coolpay.rs.json.payment.PaymenListResponse;
import com.facebook.coolpay.rs.json.payment.Payment;
import com.facebook.coolpay.rs.json.recipient.Recipient;
import com.facebook.coolpay.rs.utils.CoolpayApiUrls;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class CoolpayRestApiTest extends MockStubRestApiService {
	private CoolpayApiServices coolpayApiServices;
	
	@ClassRule
	public static final WireMockClassRule WIREMOCK_RULE = new WireMockClassRule(WIREMOCK_PORT);
		
	@Before
    public void setUp() throws Exception {
		coolpayApiServices = new CoolpayApiServices("http://localhost:" + WIREMOCK_PORT);
    }
	
	@Test
    public void loginTestSuccess() {
        stubFor(post(CoolpayApiUrls.LOGIN_API.getEndpoint()).willReturn(aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody(createJsonGetLoginSuccess())));
        LoginResponse res = coolpayApiServices.login(createLoginRequest(USER_NAME, API_KEY));
        assertEquals(TOKEN, res.getToken());
    }
	
	@Test(expected = WebApplicationException.class)
    public void loginTestInternalServerError() {
		stubFor(post(CoolpayApiUrls.LOGIN_API.getEndpoint()).willReturn(aResponse().withStatus(500)
	            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
	            .withBody("Internal server error")));
	    coolpayApiServices.login(createLoginRequest(USER_NAME, INVALID_API_KEY));
    }
	
    @Test
    public void getRecipientListTestSuccess() {
		stubFor(get(CoolpayApiUrls.RECIPIENT_API.getEndpoint()).willReturn(aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody(createJsongetRecipientListSuccess())));		
        List<Recipient> recipientResponseList = coolpayApiServices.getRecipientList(TOKEN, null);
        assertFalse(recipientResponseList.isEmpty());
        final Recipient recipientResponse = recipientResponseList.stream().findFirst().orElseThrow(RuntimeException::new);
        assertTrue(recipientResponse.getName().equals(RECIPIENT_NAME));
        assertTrue(recipientResponse.getId().equals(RECIPIENT_ID));
    }    

    @Test(expected = CoolpayApiException.class)
    public void getRecipientListTestInvalidToken() {
    	stubFor(get(CoolpayApiUrls.RECIPIENT_API.getEndpoint()).willReturn(aResponse().withStatus(401)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody("Please provide a good token")));
    	coolpayApiServices.getRecipientList(INVALID_TOKEN, null);
    }
    
    @Test(expected = CoolpayApiException.class)
    public void getRecipientListTestInternalServerError() {
    	stubFor(get(CoolpayApiUrls.RECIPIENT_API.getEndpoint()).willReturn(aResponse().withStatus(500)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody("InternalServerError")));
    	coolpayApiServices.getRecipientList(INVALID_TOKEN, null);
    }    

    @Test
    public void addRecipientTest() {        
    	stubFor(post(CoolpayApiUrls.RECIPIENT_API.getEndpoint()).willReturn(aResponse().withStatus(201)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody(createJsonRecipientSuccess())));    	
    	
        final Recipient recipientRes = coolpayApiServices.addRecipient(TOKEN, createRecipientPayloadRequest());
        assertEquals(RECIPIENT_NAME, recipientRes.getName());
        assertEquals(RECIPIENT_ID, recipientRes.getId());
    }
    
    @Test(expected = CoolpayApiException.class)
    public void addRecipientTestInternalServerError() {
    	stubFor(post(CoolpayApiUrls.RECIPIENT_API.getEndpoint()).willReturn(aResponse().withStatus(500)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody("InternalServerError")));    	
    	coolpayApiServices.addRecipient(TOKEN, createRecipientPayloadRequest());
    }    

    @Test
    public void createPaymentTestSuccess() {    	
    	stubFor(post(CoolpayApiUrls.PAYMENTS_API.getEndpoint()).willReturn(aResponse().withStatus(201)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody(createJsonPaymentSuccess())));    	
        final Payment payment = coolpayApiServices.createPayment(TOKEN, createPaymentPayloadRequest());
        assertTrue(payment.getCurrency().equals(PAYMENT_CURRENCY));
        assertTrue(payment.getRecipient_id().equals(RECIPIENT_ID));
        assertTrue(payment.getStatus().equals(PAYMENT_STATUS));
        assertTrue(payment.getAmount().equals(PAYMENT_AMOUNT));
    }
    
	@Test(expected = CoolpayApiException.class)
	public void createPaymentTestInternalServerError() {
		stubFor(post(CoolpayApiUrls.PAYMENTS_API.getEndpoint()).willReturn(aResponse().withStatus(500)
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody("InternalServerError")));
		coolpayApiServices.createPayment(TOKEN, null);
	}
	
    @Test
    public void getPaymentListTestSuccess() {
    	stubFor(get(CoolpayApiUrls.PAYMENTS_API.getEndpoint()).willReturn(aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.withBody(createJsonGetPaymentListSuccess())));   
        final PaymenListResponse paymentListResponse = coolpayApiServices.getPaymentList(TOKEN);
        List<Payment> paymentList = paymentListResponse.getPayments();
        assertNotNull(paymentList);
        assertFalse(paymentList.isEmpty());
        final Payment payment = paymentList.stream().findFirst().orElseThrow(RuntimeException::new);
        assertEquals(PAYMENT_AMOUNT, payment.getAmount());
        assertEquals(PAYMENT_CURRENCY, payment.getCurrency());
        assertEquals(RECIPIENT_ID, payment.getRecipient_id());
        assertEquals(PAYMENT_STATUS, payment.getStatus());
    }
}
