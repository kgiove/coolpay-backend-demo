package com.facebook.coolpay.exception;
/**
 * @author KGiove
 */
public class CoolpayInvalidLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CoolpayInvalidLoginException(String message, Exception e) {
        super(message, e);
    }
}
