package com.vmware.vsphere.rest.models;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.DatastoreCapability;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.StorageIORMInfo;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTDatastore extends RESTManagedEntity {
	
	private String browser;
	private DatastoreCapability capability;
	private DatastoreHostMount[] host;
	private DatastoreInfo info;
	private StorageIORMInfo iormConfiguration;
	private DatastoreSummary summary;
	private List<String> vm;
	
	// constructor
	public RESTDatastore() {
	}

	// overloaded constructor
	public RESTDatastore(Datastore mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Datastore mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// datastore specific fields
			if (fg.get("browser", fields)) {
				this.setBrowser(new ManagedObjectReferenceUri().getUri(mo.getBrowser(), uri));
			}		
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("host", fields)) {
				this.setHost(mo.getHost());
			}			
			if (fg.get("info", fields)) {
				this.setInfo(mo.getInfo());
			}
			if (fg.get("iormConfiguration", fields)) {
				this.setIormConfiguration(mo.getIormConfiguration());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
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
	public DatastoreCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(DatastoreCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the host
	 */
	public DatastoreHostMount[] getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(DatastoreHostMount[] host) {
		this.host = host;
	}

	/**
	 * @return the info
	 */
	public DatastoreInfo getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(DatastoreInfo info) {
		this.info = info;
	}

	/**
	 * @return the iormConfiguration
	 */
	public StorageIORMInfo getIormConfiguration() {
		return iormConfiguration;
	}

	/**
	 * @param iormConfiguration the iormConfiguration to set
	 */
	public void setIormConfiguration(StorageIORMInfo iormConfiguration) {
		this.iormConfiguration = iormConfiguration;
	}

	/**
	 * @return the summary
	 */
	public DatastoreSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(DatastoreSummary summary) {
		this.summary = summary;
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
