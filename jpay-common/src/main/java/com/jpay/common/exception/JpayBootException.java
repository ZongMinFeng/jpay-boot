package com.jpay.common.exception;

public class JpayBootException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JpayBootException(String message){
		super(message);
	}
	
	public JpayBootException(Throwable cause)
	{
		super(cause);
	}
	
	public JpayBootException(String message, Throwable cause)
	{
		super(message,cause);
	}

}
