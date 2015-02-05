package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.FileQuery;
import com.vmware.vim25.mo.HostDatastoreBrowser;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTHostDatastoreBrowser extends RESTManagedObject {
	
	private List<String> datastore;
	private FileQuery[] supportedType;
	
	// constructor
	public RESTHostDatastoreBrowser() {
	}

	// overloaded constructor
	public RESTHostDatastoreBrowser(HostDatastoreBrowser mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(HostDatastoreBrowser mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// HostDatastoreBrowser specific fields	
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("supportedType", fields)) {
				this.setSupportedType(mo.getSupportedType());
			}			


			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
			}
			
			
		} catch ( InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the datastore
	 */
	public List<String> getDatastore() {
		return datastore;
	}

	/**
	 * @param datastore the datastore to set
	 */
	public void setDatastore(List<String> datastore) {
		this.datastore = datastore;
	}

	/**
	 * @return the supportedType
	 */
	public FileQuery[] getSupportedType() {
		return supportedType;
	}

	/**
	 * @param supportedType the supportedType to set
	 */
	public void setSupportedType(FileQuery[] supportedType) {
		this.supportedType = supportedType;
	}



}
