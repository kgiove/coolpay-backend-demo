package com.facebook.coolpay.exception;

public class CoolpayApiException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoolpayApiException(String message, Exception e) {
        super(message, e);
    }

    public CoolpayApiException(String message) {
        super(message);
    }
}
