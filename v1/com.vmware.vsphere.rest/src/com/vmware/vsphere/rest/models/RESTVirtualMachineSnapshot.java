package com.vmware.vsphere.rest.models;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTVirtualMachineSnapshot extends RESTExtensibleManagedObject {
	
	private List<String> childSnapshot;
	private VirtualMachineConfigInfo config;
	
	// constructor
	public RESTVirtualMachineSnapshot() {
	}

	// overloaded constructor
	public RESTVirtualMachineSnapshot(VirtualMachineSnapshot mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(VirtualMachineSnapshot mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("childSnapshot", fields)) {
				try {
					this.setChildSnapshot(new ManagedObjectReferenceArray().getMORArray(mo.getChildSnapshot(), uri));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					this.setChildSnapshot(new ManagedObjectReferenceArray().getMORArray(null, uri));
				}
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			
			
			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
			}
			
			// extended from RESTExtensibleManagedObject
			if (fg.get("availableField", fields)) {
				this.setAvailableField(null);
			}		
			if (fg.get("value", fields)) {
				this.setValue(null);
			}			
			

		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the childSnapshot
	 */
	public List<String> getChildSnapshot() {
		return childSnapshot;
	}

	/**
	 * @param childSnapshot the childSnapshot to set
	 */
	public void setChildSnapshot(List<String> childSnapshot) {
		this.childSnapshot = childSnapshot;
	}

	/**
	 * @return the config
	 */
	public VirtualMachineConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(VirtualMachineConfigInfo config) {
		this.config = config;
	}


}
