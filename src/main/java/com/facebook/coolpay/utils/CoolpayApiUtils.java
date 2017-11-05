package com.facebook.coolpay.utils;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;

public class CoolpayApiUtils {
	
	public static String prepareJsonPayload(Object request){
		Gson gson = new Gson();
    	String jsonString = gson.toJson(request);
    	return jsonString;
	}
	
	public static String prepareToken(String token){	
    	return "Bearer " + token;
	}
	
	public static URI getTarget(String url, Map<String, String> parameters) {
		UriBuilder builder = UriBuilder.fromPath(url);
		if(parameters == null){
			return builder.build();
		}		
		parameters.forEach((k,v)->builder.queryParam(k, v));
		return builder.build();
	}
	
	public static URI getTarget(String url) {
		UriBuilder builder = UriBuilder.fromPath(url);
		return builder.build();
	}
	
	public static void validateResponse(Response response){
		Response.StatusType statusInfo = response.getStatusInfo();
    	if (!statusInfo.equals(Response.Status.OK)) {
    		throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());   
    	}
	}
}
