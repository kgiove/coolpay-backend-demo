package com.facebook.coolpay.rs.exception;
import javax.ws.rs.WebApplicationException;
/**
 * @author KGiove
 */
public class CoolpayApiException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public CoolpayApiException(String message, Throwable e, int status) {
        super(message, e, status);
    }    
    public CoolpayApiException(String message) {
        super(message);
    }
}
