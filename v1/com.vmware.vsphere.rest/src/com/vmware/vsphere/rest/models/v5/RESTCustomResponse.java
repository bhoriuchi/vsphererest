package com.vmware.vsphere.rest.models.v5;

import java.util.List;

public class RESTCustomResponse {
	
	private String responseStatus;
	private List<String> responseMessage;
	
	
	public RESTCustomResponse() {
	}
	
	public RESTCustomResponse(String responseStatus, List<String> responseMessage)
	{
		this.init(responseStatus, responseMessage);
	}
	
	public void init(String responseStatus, List<String> responseMessage) {
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
	 * @return the responseMessage
	 */
	public List<String> getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(List<String> responseMessage) {
		this.responseMessage = responseMessage;
	}

}
