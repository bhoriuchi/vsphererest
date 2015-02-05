package com.vmware.vsphere.rest.models.v5;

import com.vmware.vim25.mo.EnvironmentBrowser;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;

public class RESTEnvironmentBrowser extends RESTManagedObject {
	
	private String datastoreBrowser;
	
	// constructor
	public RESTEnvironmentBrowser() {
	}

	// overloaded constructor
	public RESTEnvironmentBrowser(EnvironmentBrowser mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}
	
	public void init(EnvironmentBrowser mo, String uri, String fields)  {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		// EnvironmentBrowser specific fields	
		if (fg.get("datastoreBrowser", fields)) {
			this.setDatastoreBrowser(new ManagedObjectReferenceUri().getUri(mo.getDatastoreBrowser(), uri));
		}		


		// extended from RESTManagedObject
		if (fg.get("id", fields)) {
			this.setId(mo.getMOR().getVal());
		}
		if (fg.get("moRef", fields)) {
			this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
		}
	}

	/**
	 * @return the datastoreBrowser
	 */
	public String getDatastoreBrowser() {
		return datastoreBrowser;
	}

	/**
	 * @param datastoreBrowser the datastoreBrowser to set
	 */
	public void setDatastoreBrowser(String datastoreBrowser) {
		this.datastoreBrowser = datastoreBrowser;
	}



}
