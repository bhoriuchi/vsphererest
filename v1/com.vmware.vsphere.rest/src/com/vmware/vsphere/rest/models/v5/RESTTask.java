package com.vmware.vsphere.rest.models.v5;

import java.rmi.RemoteException;

import com.vmware.vim25.mo.Task;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.models.v5.RESTTaskInfo;


public class RESTTask extends RESTExtensibleManagedObject {
	
	private RESTTaskInfo info;
	
	// constructor
	public RESTTask() {
	}

	// overloaded constructor
	public RESTTask(Task mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Task mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("info", fields)) {
				this.setInfo(new RESTTaskInfo(mo.getTaskInfo(), uri, fields));
			}

			// set the extended properties
			this.setExtensibleManagedObject(mo, fields, uri);			



		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the info
	 */
	public RESTTaskInfo getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(RESTTaskInfo info) {
		this.info = info;
	}

}
