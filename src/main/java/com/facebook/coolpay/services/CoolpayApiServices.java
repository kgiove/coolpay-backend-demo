package com.facebook.coolpay.services;

import static com.facebook.coolpay.utils.CoolpayApiUtils.getTarget;
import static com.facebook.coolpay.utils.CoolpayApiUtils.prepareJsonPayload;
import static com.facebook.coolpay.utils.CoolpayApiUtils.prepareToken;
import static com.facebook.coolpay.utils.CoolpayApiUtils.validateResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.facebook.coolpay.exception.CoolpayApiException;
import com.facebook.coolpay.exception.CoolpayInvalidLoginException;
import com.facebook.coolpay.json.login.LoginPayload;
import com.facebook.coolpay.json.login.LoginResponse;
import com.facebook.coolpay.json.payment.PaymenListResponse;
import com.facebook.coolpay.json.payment.Payment;
import com.facebook.coolpay.json.payment.PaymentPayload;
import com.facebook.coolpay.json.recipient.Recipient;
import com.facebook.coolpay.json.recipient.RecipientListResponse;
import com.facebook.coolpay.json.recipient.RecipientPayload;
import com.facebook.coolpay.utils.CoolpayApiUrls;
import com.facebook.coolpay.utils.CoolpayServicesApi;

/**
 * Root resource (exposed at "CoolpayApiServices" path)
 */
@Path("services")
public class CoolpayApiServices implements CoolpayServicesApi {

	 /**
     * Provide to login to Coolpay API.
     * A token will be returned that will be used to call API.
     * @param LoginPayload request
     * @return token
     */
    @POST
	@Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
	@Override
	public String login(LoginPayload request) {
    	
    	try {
    		Client client = ClientBuilder.newClient();
        	Entity<String> payload = Entity.json(prepareJsonPayload(request));    	
        	Response response = client.target(CoolpayApiUrls.LOGIN_API.getEndpoint()).request(MediaType.APPLICATION_JSON_TYPE).post(payload);
        	validateResponse(response);
        	LoginResponse loginResponse = response.readEntity(LoginResponse.class);
            return loginResponse.getToken();
        } catch (Exception e) {
            throw new CoolpayInvalidLoginException("Invalid username or ApiKey ", e);
        }	
	}
    
    /**
     * Get a list of all the recipients associated to a specific token and a name if passed.
     * @param token
     * @param name
     * @return List<Recipient>
     */
    @GET
   	@Path("/recipients/{token}{p:/?}{name:.*}")	
	@Produces(MediaType.APPLICATION_JSON)
    @Override
	public List<Recipient> getRecipientList(@PathParam("token") String token, @PathParam("name") String name) {
		// TODO Auto-generated method stub
    	Map<String, String> parameters = null;
        try {        	    	
    		if (name!=null && !"".equals(name)){
    			parameters = new HashMap<String, String>();
    			parameters.put("name", name);
            }		
    		URI target = getTarget(CoolpayApiUrls.RECIPIENT_API.getEndpoint(), parameters);
        	Client client = ClientBuilder.newClient();        	
			Response response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).get();
			validateResponse(response);
        	RecipientListResponse recipientListResponse = response.readEntity(RecipientListResponse.class);
            return recipientListResponse.getRecipients();
        } catch (Exception e){
        	throw new CoolpayApiException(String.format("Error trying to get recipient. Token: %s, name: %s", token, name), e);
        }
	}
    
    /**
     * Create a new recipient
     * @param token
     * @param RecipientPayload request
     * @return Recipient
     */
    @POST
   	@Path("/createRecipient/{token}")	
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Recipient addRecipient(@PathParam("token") String token, RecipientPayload request) {
		// TODO Auto-generated method stub
    	String name = null;
		try {
			URI target = getTarget(CoolpayApiUrls.RECIPIENT_API.getEndpoint());
			name = request.getRecipient().getName();
			Client client = ClientBuilder.newClient();
			Entity<String> payload = Entity.json(prepareJsonPayload(request));
			Response response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).post(payload);
			validateResponse(response);
			RecipientPayload recipientBody = response.readEntity(RecipientPayload.class);
			return recipientBody.getRecipient();

		} catch (Exception e) {
			throw new CoolpayApiException(String.format("Error trying to add recipient. authorizationToken: %s, name: %s", token, name), e);
		}
	}
    
    /**
     * Create a new payment
     * @param token
     * @param PaymentPayload request
     * @return Payment
     */
	@POST
	@Path("/createPayment/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Payment createPayment(@PathParam("token") String token, PaymentPayload request) {
		// TODO Auto-generated method stub
		try {
			URI target = getTarget(CoolpayApiUrls.PAYMENTS_API.getEndpoint());
			Client client = ClientBuilder.newClient();
			Entity<String> payload = Entity.json(prepareJsonPayload(request));
			Response response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).post(payload);
			validateResponse(response);
			PaymentPayload paymentBody = response.readEntity(PaymentPayload.class);
			return paymentBody.getPayment();			
		} catch (Exception e) {
			throw new CoolpayApiException(String.format("Error trying to create payment. Token: %s", token), e);
		}
	}
	
	/**
     * Get a list of all the payments associated to a specific token.
     * @param token
     * @return PaymenListResponse
     */	
	@GET
   	@Path("/payments/{token}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
	public PaymenListResponse getPaymentList(@PathParam("token") String token) {
		// TODO Auto-generated method stub
		try {
			URI target = getTarget(CoolpayApiUrls.PAYMENTS_API.getEndpoint());
			Client client = ClientBuilder.newClient();
			Response response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).get();	
			validateResponse(response);
			PaymenListResponse paymenListResponse = response.readEntity(PaymenListResponse.class);
            return paymenListResponse;
		} catch (Exception e) {
			 throw new CoolpayApiException(String.format("Error trying to get payments. Token: %s", token), e);
		} 
	}
}
