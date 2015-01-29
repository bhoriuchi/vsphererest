package com.vmware.vsphere.rest.models;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineFileLayout;
import com.vmware.vim25.VirtualMachineFileLayoutEx;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineStorageInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTVirtualMachine extends RESTManagedEntity {

	private VirtualMachineCapability capability;
	private VirtualMachineConfigInfo config;
	private List<String> datastore;
	private String environmentBrowser;	
	private GuestInfo guest;
	private ManagedEntityStatus guestHeartbeatStatus;
	private VirtualMachineFileLayout layout;
	private VirtualMachineFileLayoutEx layoutEx;
	private List<String> network;
	private String parentVApp;
	private ResourceConfigSpec resourceConfig;
	private String resourcePool;
	private List<String> rootSnapshot;
	private VirtualMachineRuntimeInfo runtime;
	private VirtualMachineSnapshotInfo snapshot;
	private VirtualMachineStorageInfo storage;
	private VirtualMachineSummary summary;
	
	// constructor
	public RESTVirtualMachine() {
	}

	// overloaded constructor
	public RESTVirtualMachine(VirtualMachine mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {

			// virtual machine specific fields
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}		
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}	
			if (fg.get("environmentBrowser", fields)) {
				this.setEnvironmentBrowser(new ManagedObjectReferenceUri().getUri(mo.getEnvironmentBrowser(), uri));
			}
			if (fg.get("guest", fields)) {
				this.setGuest(mo.getGuest());
			}
			if (fg.get("guestHeartbeatStatus", fields)) {
				this.setGuestHeartbeatStatus(mo.getGuestHeartbeatStatus());
			}
			if (fg.get("layout", fields)) {
				this.setLayout(mo.getLayout());
			}
			if (fg.get("layoutEx", fields)) {
				this.setLayoutEx(mo.getLayoutEx());
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(mo.getNetworks(), uri));
			}
			if (fg.get("parentVApp", fields)) {
				this.setParentVApp(new ManagedObjectReferenceUri().getUri(mo.getParentVApp(), uri));
			}
			if (fg.get("resourceConfig", fields)) {
				this.setResourceConfig(mo.getResourceConfig());
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(mo.getResourcePool(), uri));
			}
			if (fg.get("rootSnapshot", fields)) {
				this.setRootSnapshot(new ManagedObjectReferenceArray().getMORArray(mo.getRootSnapshot(), uri));
			}
			if (fg.get("runtime", fields)) {
				this.setRuntime(mo.getRuntime());
			}
			if (fg.get("snapshot", fields)) {
				this.setSnapshot(mo.getSnapshot());
			}
			if (fg.get("storage", fields)) {
				this.setStorage(mo.getStorage());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
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
	public VirtualMachineCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(VirtualMachineCapability capability) {
		this.capability = capability;
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
	 * @return the environmentBrowser
	 */
	public String getEnvironmentBrowser() {
		return environmentBrowser;
	}

	/**
	 * @param environmentBrowser the environmentBrowser to set
	 */
	public void setEnvironmentBrowser(String environmentBrowser) {
		this.environmentBrowser = environmentBrowser;
	}

	/**
	 * @return the guest
	 */
	public GuestInfo getGuest() {
		return guest;
	}

	/**
	 * @param guest the guest to set
	 */
	public void setGuest(GuestInfo guest) {
		this.guest = guest;
	}

	/**
	 * @return the guestHeartbeatStatus
	 */
	public ManagedEntityStatus getGuestHeartbeatStatus() {
		return guestHeartbeatStatus;
	}

	/**
	 * @param guestHeartbeatStatus the guestHeartbeatStatus to set
	 */
	public void setGuestHeartbeatStatus(ManagedEntityStatus guestHeartbeatStatus) {
		this.guestHeartbeatStatus = guestHeartbeatStatus;
	}

	/**
	 * @return the layout
	 */
	public VirtualMachineFileLayout getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(VirtualMachineFileLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return the layoutEx
	 */
	public VirtualMachineFileLayoutEx getLayoutEx() {
		return layoutEx;
	}

	/**
	 * @param layoutEx the layoutEx to set
	 */
	public void setLayoutEx(VirtualMachineFileLayoutEx layoutEx) {
		this.layoutEx = layoutEx;
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
	 * @return the parentVApp
	 */
	public String getParentVApp() {
		return parentVApp;
	}

	/**
	 * @param parentVApp the parentVApp to set
	 */
	public void setParentVApp(String parentVApp) {
		this.parentVApp = parentVApp;
	}

	/**
	 * @return the resourceConfig
	 */
	public ResourceConfigSpec getResourceConfig() {
		return resourceConfig;
	}

	/**
	 * @param resourceConfig the resourceConfig to set
	 */
	public void setResourceConfig(ResourceConfigSpec resourceConfig) {
		this.resourceConfig = resourceConfig;
	}

	/**
	 * @return the resourcePool
	 */
	public String getResourcePool() {
		return resourcePool;
	}

	/**
	 * @param resourcePool the resourcePool to set
	 */
	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	/**
	 * @return the rootSnapshot
	 */
	public List<String> getRootSnapshot() {
		return rootSnapshot;
	}

	/**
	 * @param rootSnapshot the rootSnapshot to set
	 */
	public void setRootSnapshot(List<String> rootSnapshot) {
		this.rootSnapshot = rootSnapshot;
	}

	/**
	 * @return the runtime
	 */
	public VirtualMachineRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(VirtualMachineRuntimeInfo runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the snapshot
	 */
	public VirtualMachineSnapshotInfo getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(VirtualMachineSnapshotInfo snapshot) {
		this.snapshot = snapshot;
	}

	/**
	 * @return the storage
	 */
	public VirtualMachineStorageInfo getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(VirtualMachineStorageInfo storage) {
		this.storage = storage;
	}

	/**
	 * @return the summary
	 */
	public VirtualMachineSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(VirtualMachineSummary summary) {
		this.summary = summary;
	}
}
