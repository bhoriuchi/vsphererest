package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.DVSCapability;
import com.vmware.vim25.DVSConfigInfo;
import com.vmware.vim25.DVSNetworkResourcePool;
import com.vmware.vim25.DVSRuntimeInfo;
import com.vmware.vim25.DVSSummary;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
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
