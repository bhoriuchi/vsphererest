package com.vmware.vsphere.rest.models;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;


import com.vmware.vim25.HostCapability;
import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostLicensableResourceInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.HostSystemResourceInfo;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTHostSystem extends RESTManagedEntity {
	
	private HostCapability capability;
	private HostConfigInfo config;
	private RESTHostConfigManager configManager;
	private List<String> datastore;
	private String datastoreBrowser;
	private HostHardwareInfo hardware;
	private HostLicensableResourceInfo licensableResource;
	private List<String> network;
	private HostRuntimeInfo runtime;
	private HostListSummary summary;
	private HostSystemResourceInfo systemResources;
	private List<String> vm;
	
	// constructor
	public RESTHostSystem() {
	}

	// overloaded constructor
	public RESTHostSystem(HostSystem mo, String uri, String fields) {

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
			if (fg.get("configManager", fields)) {
				this.setConfigManager(new RESTHostConfigManager(mo, uri, fields));
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("datastoreBrowser", fields)) {
				this.setDatastoreBrowser(new ManagedObjectReferenceUri().getUri(mo.getDatastoreBrowser(), uri));
			}
			if (fg.get("hardware", fields)) {
				this.setHardware(mo.getHardware());
			}
			if (fg.get("licensableResource", fields)) {
				this.setLicensableResource(mo.getLicensableResource());
			}	
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(mo.getNetworks(), uri));
			}
			if (fg.get("runtime", fields)) {
				this.setRuntime(mo.getRuntime());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("systemResources", fields)) {
				this.setSystemResources(mo.getConfig().systemResources);
			}	
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(mo.getVms(), uri));
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
				this.setAvailableField(mo.getAvailableField());
			}		
			if (fg.get("value", fields)) {
				this.setValue(mo.getValues());
			}			
			
			// extended from RESTManagedEntity
			if (fg.get("alarmActionsEnabled", fields)) {
				this.setAlarmActionsEnabled(mo.getAlarmActionEabled());
			}
			if (fg.get("configIssue", fields)) {
				this.setConfigIssue(mo.getConfigIssue());
			}
			if (fg.get("configStatus", fields)) {
				this.setConfigStatus(mo.getConfigStatus());
			}
			if (fg.get("customValue", fields)) {
				this.setCustomValue(mo.getCustomValue());
			}
			if (fg.get("declaredAlarmState", fields)) {
				this.setDeclaredAlarmState(mo.getDeclaredAlarmState());
			}
			if (fg.get("disabledMethod", fields)) {
				this.setDisabledMethod(mo.getDisabledMethod());
			}
			if (fg.get("effectiveRole", fields)) {
				this.setEffectiveRole(mo.getEffectiveRole());
			}
			if (fg.get("name", fields)) {
				this.setName(mo.getName());
			}
			if (fg.get("overallStatus", fields)) {
				this.setOverallStatus(mo.getOverallStatus());
			}
			if (fg.get("parent", fields)) {
				this.setParent(new ManagedObjectReferenceUri().getUri(mo.getParent(), uri));
			}
			if (fg.get("permission", fields)) {
				this.setPermission(mo.getPermission());
			}
			if (fg.get("recentTask", fields)) {
				this.setRecentTask(new ManagedObjectReferenceArray().getMORArray(mo.getRecentTasks(), uri));
			}
			if (fg.get("tag", fields)) {
				this.setTag(mo.getTag());
			}
			if (fg.get("triggeredAlarmState", fields)) {
				this.setTriggeredAlarmState(mo.getTriggeredAlarmState());
			}



		} catch (RemoteException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the capability
	 */
	public HostCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(HostCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the config
	 */
	public HostConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(HostConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the configManager
	 */
	public RESTHostConfigManager getConfigManager() {
		return configManager;
	}

	/**
	 * @param configManager the configManager to set
	 */
	public void setConfigManager(RESTHostConfigManager configManager) {
		this.configManager = configManager;
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

	/**
	 * @return the hardware
	 */
	public HostHardwareInfo getHardware() {
		return hardware;
	}

	/**
	 * @param hardware the hardware to set
	 */
	public void setHardware(HostHardwareInfo hardware) {
		this.hardware = hardware;
	}

	/**
	 * @return the licensableResource
	 */
	public HostLicensableResourceInfo getLicensableResource() {
		return licensableResource;
	}

	/**
	 * @param licensableResource the licensableResource to set
	 */
	public void setLicensableResource(HostLicensableResourceInfo licensableResource) {
		this.licensableResource = licensableResource;
	}

	/**
	 * @return the network
	 */
	public List<String> getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(List<String> network) {
		this.network = network;
	}

	/**
	 * @return the runtime
	 */
	public HostRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(HostRuntimeInfo runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the summary
	 */
	public HostListSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(HostListSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the systemResource
	 */
	public HostSystemResourceInfo getSystemResources() {
		return systemResources;
	}

	/**
	 * @param systemResource the systemResource to set
	 */
	public void setSystemResources(HostSystemResourceInfo systemResources) {
		this.systemResources = systemResources;
	}

	/**
	 * @return the vm
	 */
	public List<String> getVm() {
		return vm;
	}

	/**
	 * @param vm the vm to set
	 */
	public void setVm(List<String> vm) {
		this.vm = vm;
	}


}
