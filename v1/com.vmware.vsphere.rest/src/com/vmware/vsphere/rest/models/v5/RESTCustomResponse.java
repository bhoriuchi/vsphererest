package com.vmware.vsphere.rest.models.v5;

public class RESTCustomResponse {
	
	private String responseStatus;
	private String responseMessage;
	
	
	public RESTCustomResponse() {
	}
	
	public RESTCustomResponse(String responseStatus, String responseMessage)
	{
		this.init(responseStatus, responseMessage);
	}
	
	public void init(String responseStatus, String responseMessage) {
		this.setResponseStatus(responseStatus);
		this.setResponseMessage(responseMessage);
	}
	
	
	/**
	 * @return the status
	 */
	public String getResponseStatus() {
		return responseStatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	/**
	 * @return the message
	 */
	public String getResponseMessage() {
		return responseMessage;
	}
	/**
	 * @param message the message to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
