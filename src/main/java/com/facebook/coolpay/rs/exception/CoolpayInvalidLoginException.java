package com.facebook.coolpay.rs.exception;

import javax.ws.rs.WebApplicationException;

/**
 * @author KGiove
 */
public class CoolpayInvalidLoginException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	   public CoolpayInvalidLoginException(String message, Throwable e, int status) {
	        super(message, e, status);
	    }    
	    public CoolpayInvalidLoginException(String message) {
	        super(message);
	    }
}
