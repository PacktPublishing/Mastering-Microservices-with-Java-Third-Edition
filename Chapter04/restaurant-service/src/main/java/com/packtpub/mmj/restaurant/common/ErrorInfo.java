package com.packtpub.mmj.restaurant.common;

public class ErrorInfo {
	private String url;
	private String message;

	public ErrorInfo() {
		
	}
	
	public ErrorInfo(String message) {
		this.message = message;
	}
	
	public ErrorInfo(String url, String message) {
		this.url = url;
		this.message = message;
	}	
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
