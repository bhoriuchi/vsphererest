package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.DVSCapability;
import com.vmware.vim25.DVSConfigInfo;
import com.vmware.vim25.DVSNetworkResourcePool;
import com.vmware.vim25.DVSRuntimeInfo;
import com.vmware.vim25.DVSSummary;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTDistributedVirtualSwitch extends RESTManagedEntity {
	
	private DVSCapability capability;
	private DVSConfigInfo config;
	private DVSNetworkResourcePool[] networkResourcePool;
	private List<String> portgroup;
	private DVSRuntimeInfo runtime;
	private DVSSummary summary;
	private String uuid;
	
	// constructor
	public RESTDistributedVirtualSwitch() {
	}

	// overloaded constructor
	public RESTDistributedVirtualSwitch(DistributedVirtualSwitch mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(DistributedVirtualSwitch mo, String uri, String fields) {
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
	
	/**
	 * @return the capability
	 */
	public DVSCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(DVSCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the config
	 */
	public DVSConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(DVSConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the networkResourcePool
	 */
	public DVSNetworkResourcePool[] getNetworkResourcePool() {
		return networkResourcePool;
	}

	/**
	 * @param networkResourcePool the networkResourcePool to set
	 */
	public void setNetworkResourcePool(DVSNetworkResourcePool[] networkResourcePool) {
		this.networkResourcePool = networkResourcePool;
	}

	/**
	 * @return the portgroup
	 */
	public List<String> getPortgroup() {
		return portgroup;
	}

	/**
	 * @param portgroup the portgroup to set
	 */
	public void setPortgroup(List<String> portgroup) {
		this.portgroup = portgroup;
	}

	/**
	 * @return the runtime
	 */
	public DVSRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(DVSRuntimeInfo runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the summary
	 */
	public DVSSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(DVSSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



}
