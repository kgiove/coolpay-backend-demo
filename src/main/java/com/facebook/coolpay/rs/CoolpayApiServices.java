package com.facebook.coolpay.rs;
import static com.facebook.coolpay.rs.utils.CoolpayApiUtils.getTarget;
import static com.facebook.coolpay.rs.utils.CoolpayApiUtils.prepareJsonPayload;
import static com.facebook.coolpay.rs.utils.CoolpayApiUtils.prepareToken;

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

import com.facebook.coolpay.rs.exception.CoolpayApiException;
import com.facebook.coolpay.rs.exception.CoolpayInvalidLoginException;
import com.facebook.coolpay.rs.json.login.LoginPayload;
import com.facebook.coolpay.rs.json.login.LoginResponse;
import com.facebook.coolpay.rs.json.payment.PaymenListResponse;
import com.facebook.coolpay.rs.json.payment.Payment;
import com.facebook.coolpay.rs.json.payment.PaymentPayload;
import com.facebook.coolpay.rs.json.recipient.Recipient;
import com.facebook.coolpay.rs.json.recipient.RecipientListResponse;
import com.facebook.coolpay.rs.json.recipient.RecipientPayload;
import com.facebook.coolpay.rs.utils.CoolpayApiUrls;
import com.facebook.coolpay.rs.utils.CoolpayServicesApi;

/**
 * Root resource (exposed at "CoolpayApiServices" path)
 */
@Path("services")
public class CoolpayApiServices implements CoolpayServicesApi {
	private String host = "https://coolpay.herokuapp.com";
	
	public CoolpayApiServices(String host) {
		this.host = host;
    }
	public CoolpayApiServices(){}
		
	/**
     * Provide to login to Coolpay API.
     * An authentication token is returned.
     * @param LoginPayload request
     * @return token
     */
    @POST
	@Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Override
	public LoginResponse login(LoginPayload request) {
    	Response response = null;
    	try {
    		URI target = getTarget(host + CoolpayApiUrls.LOGIN_API.getEndpoint());
    		Client client = ClientBuilder.newClient();
        	Entity<String> payload = Entity.json(prepareJsonPayload(request));
        	response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).post(payload);
        	LoginResponse loginResponse = response.readEntity(LoginResponse.class);
            return loginResponse;
        } catch (Exception e) {
        	//e.printStackTrace();
            throw new CoolpayInvalidLoginException("Login has failed. Check, please your username or ApiKey", e.getCause(), response.getStatus());
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
	public List<Recipient> getRecipientList(@PathParam("token") String token, @PathParam("name") String name) {
		// TODO Auto-generated method stub
    	Map<String, String> parameters = null;
    	Response response = null;
        try {        	    	
    		if (name!=null && !"".equals(name)){
    			parameters = new HashMap<String, String>();
    			parameters.put("name", name);
            }
    		
    		URI target = getTarget(host +  CoolpayApiUrls.RECIPIENT_API.getEndpoint(), parameters);
    		Client client = ClientBuilder.newClient();        	
			response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).get();
		   	RecipientListResponse recipientListResponse = response.readEntity(RecipientListResponse.class);
            return recipientListResponse.getRecipients();
        } catch (Exception e){
        	//e.printStackTrace();
            throw new CoolpayApiException(String.format("Error trying to get recipient. Token: %s, name: %s", token, name), e.getCause(), response.getStatus());
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
    	Response response = null;    	
		try {
			URI target = getTarget(host + CoolpayApiUrls.RECIPIENT_API.getEndpoint());
			name = request.getRecipient().getName();
			Client client = ClientBuilder.newClient();
			Entity<String> payload = Entity.json(prepareJsonPayload(request));
			response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).post(payload);
			RecipientPayload recipientBody = response.readEntity(RecipientPayload.class);
			return recipientBody.getRecipient();
		} catch (Exception e) {
			throw new CoolpayApiException(String.format("Error trying to add recipient. Token: %s, name: %s", token, name),  e.getCause(), response.getStatus());
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
		Response response = null;
		try {
			URI target = getTarget(host + CoolpayApiUrls.PAYMENTS_API.getEndpoint());
			Client client = ClientBuilder.newClient();
			Entity<String> payload = Entity.json(prepareJsonPayload(request));
			response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).post(payload);
			PaymentPayload paymentBody = response.readEntity(PaymentPayload.class);
			return paymentBody.getPayment();			
		} catch (Exception e) {
			throw new CoolpayApiException(String.format("Error trying to create payment. Token: %s", token), e.getCause(), response.getStatus());			
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
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
	public PaymenListResponse getPaymentList(@PathParam("token") String token) {
		// TODO Auto-generated method stub
		Response response = null;
		try {
			URI target = getTarget(host + CoolpayApiUrls.PAYMENTS_API.getEndpoint());
			Client client = ClientBuilder.newClient();
			response = client.target(target).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", prepareToken(token)).get();	
			PaymenListResponse paymenListResponse = response.readEntity(PaymenListResponse.class);
            return paymenListResponse;
		} catch (Exception e) {
			 throw new CoolpayApiException(String.format("Error trying to get payments. Token: %s", token), e.getCause(), response.getStatus());
		}
	}
}
