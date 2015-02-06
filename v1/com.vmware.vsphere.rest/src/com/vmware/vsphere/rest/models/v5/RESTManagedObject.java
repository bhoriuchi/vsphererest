package com.vmware.vsphere.rest.models.v5;

import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;

public class RESTManagedObject {
	
	private String id;
	private String moRef;
	private String resource;

	
	public void setManagedObject(ManagedObject mo, String fields, String uri) {
		
		FieldGet fg = new FieldGet();
		
		if (fg.get("id", fields)) {
			this.setId(mo.getMOR().getVal());
		}
		if (fg.get("moRef", fields)) {
			this.setMoRef(mo.getMOR().getType() + "-"
					+ mo.getMOR().getVal());
		}
		if (fg.get("resource", fields)) {
			this.setResource(new ManagedObjectReferenceUri().getUri(
					mo, uri));
		}
	}
	
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

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	

}
