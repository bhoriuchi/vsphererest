package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;

import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTDistributedVirtualPortgroup extends RESTNetwork {
	
	private DVPortgroupConfigInfo config;
	private String key;
	private String[] portKeys;
	
	// constructor
	public RESTDistributedVirtualPortgroup() {
	}

	// overloaded constructor
	public RESTDistributedVirtualPortgroup(DistributedVirtualPortgroup mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(DistributedVirtualPortgroup mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("key", fields)) {
				this.setKey(mo.getKey());
			}
			if (fg.get("portKeys", fields)) {
				this.setPortKeys(mo.getPortKeys());
			}
			
			
			// extended from RESTNetwork
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(mo.getHosts(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(mo.getVms(), uri));
			}
			
			// set the extended properties
			this.setManagedEntity(mo, fields, uri);


		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the config
	 */
	public DVPortgroupConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(DVPortgroupConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the portKeys
	 */
	public String[] getPortKeys() {
		return portKeys;
	}

	/**
	 * @param portKeys the portKeys to set
	 */
	public void setPortKeys(String[] portKeys) {
		this.portKeys = portKeys;
	}





}
