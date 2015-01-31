package com.vmware.vsphere.rest.models;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.DatacenterConfigInfo;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTDatacenter extends RESTManagedEntity {
	
	private DatacenterConfigInfo configuration;
	private List<String> datastore;
	private String datastoreFolder;
	private String hostFolder;
	private List<String> network;
	private String networkFolder;
	private String vmFolder;
	
	// constructor
	public RESTDatacenter() {
	}

	// overloaded constructor
	public RESTDatacenter(Datacenter mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("configuration", fields)) {
				this.setConfiguration(mo.getConfiguration());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("datastoreFolder", fields)) {
				this.setDatastoreFolder(new ManagedObjectReferenceUri().getUri(mo.getDatastoreFolder(), uri));
			}
			if (fg.get("hostFolder", fields)) {
				this.setHostFolder(new ManagedObjectReferenceUri().getUri(mo.getHostFolder(), uri));
			}			
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(mo.getNetworks(), uri));
			}			
			if (fg.get("networkFolder", fields)) {
				this.setNetworkFolder(new ManagedObjectReferenceUri().getUri(mo.getNetworkFolder(), uri));
			}			
			if (fg.get("vmFolder", fields)) {
				this.setVmFolder(new ManagedObjectReferenceUri().getUri(mo.getVmFolder(), uri));
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
	 * @return the configuration
	 */
	public DatacenterConfigInfo getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(DatacenterConfigInfo configuration) {
		this.configuration = configuration;
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
	 * @return the datastoreFolder
	 */
	public String getDatastoreFolder() {
		return datastoreFolder;
	}

	/**
	 * @param datastoreFolder the datastoreFolder to set
	 */
	public void setDatastoreFolder(String datastoreFolder) {
		this.datastoreFolder = datastoreFolder;
	}

	/**
	 * @return the hostFolder
	 */
	public String getHostFolder() {
		return hostFolder;
	}

	/**
	 * @param hostFolder the hostFolder to set
	 */
	public void setHostFolder(String hostFolder) {
		this.hostFolder = hostFolder;
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
	 * @return the networkFolder
	 */
	public String getNetworkFolder() {
		return networkFolder;
	}

	/**
	 * @param networkFolder the networkFolder to set
	 */
	public void setNetworkFolder(String networkFolder) {
		this.networkFolder = networkFolder;
	}

	/**
	 * @return the vmFolder
	 */
	public String getVmFolder() {
		return vmFolder;
	}

	/**
	 * @param vmFolder the vmFolder to set
	 */
	public void setVmFolder(String vmFolder) {
		this.vmFolder = vmFolder;
	}


	

}
