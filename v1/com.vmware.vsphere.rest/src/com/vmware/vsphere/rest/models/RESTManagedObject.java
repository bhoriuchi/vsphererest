package com.vmware.vsphere.rest.models;

public class RESTManagedObject {
	
	private String id;
	private String moRef;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the moRef
	 */
	public String getMoRef() {
		return moRef;
	}

	/**
	 * @param moRef the moRef to set
	 */
	public void setMoRef(String moRef) {
		this.moRef = moRef;
	}
	

}
