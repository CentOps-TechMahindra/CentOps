package com.techm.psd.exception;

public class PSDException  extends Exception{
	
	/**
	 * This element is to remove the warning.
	 * Read more on this one!
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception message (Human readable message)
	 */
	private String message;

	/**
	 * No default constructor
	 * @param msg
	 * @return 
	 */
	public PSDException(String msg){
		super(msg);
		this.message = msg;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}