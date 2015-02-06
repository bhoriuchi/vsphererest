package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;

import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTVmwareDistributedVirtualSwitch extends RESTDistributedVirtualSwitch {
	
	// constructor
	public RESTVmwareDistributedVirtualSwitch() {
	}

	// overloaded constructor
	public RESTVmwareDistributedVirtualSwitch(VmwareDistributedVirtualSwitch mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(VmwareDistributedVirtualSwitch mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("networkResourcePool", fields)) {
				this.setNetworkResourcePool(mo.getNetworkResourcePool());
			}
			if (fg.get("portgroup", fields)) {
				this.setPortgroup(new ManagedObjectReferenceArray().getMORArray(mo.getPortgroup(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("uuid", fields)) {
				this.setUuid(mo.getUuid());
			}
			
			// set the extended properties
			this.setManagedEntity(mo, fields, uri);

		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
