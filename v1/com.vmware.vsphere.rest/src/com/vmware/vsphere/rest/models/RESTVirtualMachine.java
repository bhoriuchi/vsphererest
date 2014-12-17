package com.vmware.vsphere.rest.models;

import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.AlarmState;
import com.vmware.vim25.CustomFieldDef;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.Event;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.Permission;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.Tag;
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

public class RESTVirtualMachine {

	private String id;
	private String name;

	private boolean alarmActionsEnabled;
	private CustomFieldDef[] availableField;
	private VirtualMachineCapability capability;
	private VirtualMachineConfigInfo config;
	private Event[] configIssues;
	private ManagedEntityStatus configStatus;
	private CustomFieldValue[] customValue;
	private List<String> datastore;
	private AlarmState[] declaredAlarmState;
	private String[] disabledMethod;
	private int[] effectiveRole;
	private String environmentBrowser;
	private GuestInfo guest;
	private ManagedEntityStatus guestHeartbeatStatus;
	private VirtualMachineFileLayout layout;
	private VirtualMachineFileLayoutEx layoutEx;
	private List<String> network;
	private ManagedEntityStatus overallStatus;
	private String parent;
	private String parentVApp;
	private Permission[] permission;
	private ResourceConfigSpec resourceConfig;
	private String resourcePool;
	private List<String> recentTask;
	private List<String> rootSnapshot;
	private VirtualMachineRuntimeInfo runtime;
	private VirtualMachineSnapshotInfo snapshot;
	private VirtualMachineStorageInfo storage;
	private VirtualMachineSummary summary;
	private Tag[] tag;
	private AlarmState[] triggeredAlarmState;
	private CustomFieldValue[] value;
	private String moRef;

	// constructor
	public RESTVirtualMachine() {
	}

	// overloaded constructor
	public RESTVirtualMachine(VirtualMachine vm, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {

			// basic values
			if (fg.get("id", fields)) {
				this.setId(vm.getMOR().getVal());
			}
			if (fg.get("name", fields)) {
				this.setName(vm.getName());
			}

			// extended values
			if (fg.get("alarmActionsEnabled", fields)) {
				this.setAlarmActionsEnabled(vm.getAlarmActionEabled());
			}
			if (fg.get("availableField", fields)) {
				this.setAvailableField(vm.getAvailableField());
			}
			if (fg.get("capability", fields)) {
				this.setCapability(vm.getCapability());
			}
			if (fg.get("config", fields)) {
				this.setConfig(vm.getConfig());
			}
			if (fg.get("configIssues", fields)) {
				this.setConfigIssues(vm.getConfigIssue());
			}
			if (fg.get("configStatus", fields)) {
				this.setConfigStatus(vm.getConfigStatus());
			}
			if (fg.get("customValue", fields)) {
				this.setCustomValue(vm.getCustomValue());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(vm.getDatastores(), uri));
			}
			if (fg.get("declaredAlarmState", fields)) {
				this.setDeclaredAlarmState(vm.getDeclaredAlarmState());
			}
			if (fg.get("disabledMethod", fields)) {
				this.setDisabledMethod(vm.getDisabledMethod());
			}
			if (fg.get("effectiveRole", fields)) {
				this.setEffectiveRole(vm.getEffectiveRole());
			}
			if (fg.get("environmentBrowser", fields)) {
				this.setEnvironmentBrowser(new ManagedObjectReferenceUri().getUri(vm.getEnvironmentBrowser().getMOR(), uri));
			}
			if (fg.get("guest", fields)) {
				this.setGuest(vm.getGuest());
			}
			if (fg.get("guestHeartbeatStatus", fields)) {
				this.setGuestHeartbeatStatus(vm.getGuestHeartbeatStatus());
			}
			if (fg.get("layout", fields)) {
				this.setLayout(vm.getLayout());
			}
			if (fg.get("layoutEx", fields)) {
				this.setLayoutEx(vm.getLayoutEx());
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(vm.getNetworks(), uri));
			}
			if (fg.get("overallStatus", fields)) {
				this.setOverallStatus(vm.getOverallStatus());
			}
			if (fg.get("parent", fields)) {
				this.setParent(new ManagedObjectReferenceUri().getUri(vm.getParent().getMOR(), uri));
			}
			if (fg.get("parentVApp", fields)) {
				this.setParentVApp(new ManagedObjectReferenceUri().getUri(vm.getParentVApp().getMOR(), uri));
			}
			if (fg.get("permission", fields)) {
				this.setPermission(vm.getPermission());
			}
			if (fg.get("recentTask", fields)) {
				this.setRecentTask(new ManagedObjectReferenceArray().getMORArray(vm.getRecentTasks(), uri));
			}
			if (fg.get("resourceConfig", fields)) {
				this.setResourceConfig(vm.getResourceConfig());
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(vm.getResourcePool().getMOR(), uri));
			}
			if (fg.get("rootSnapshot", fields)) {
				this.setRootSnapshot(new ManagedObjectReferenceArray().getMORArray(vm.getRootSnapshot(), uri));
			}
			if (fg.get("runtime", fields)) {
				this.setRuntime(vm.getRuntime());
			}
			if (fg.get("snapshot", fields)) {
				this.setSnapshot(vm.getSnapshot());
			}
			if (fg.get("storage", fields)) {
				this.setStorage(vm.getStorage());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(vm.getSummary());
			}
			if (fg.get("tag", fields)) {
				this.setTag(vm.getTag());
			}
			if (fg.get("triggeredAlarmState", fields)) {
				this.setTriggeredAlarmState(vm.getTriggeredAlarmState());
			}
			if (fg.get("value", fields)) {
				this.setValue(vm.getValues());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(vm.getMOR().getType() + "-" + vm.getMOR().getVal());
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// access functions
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the alarmActionsEnabled
	 */
	public boolean isAlarmActionsEnabled() {
		return alarmActionsEnabled;
	}

	/**
	 * @param alarmActionsEnabled the alarmActionsEnabled to set
	 */
	public void setAlarmActionsEnabled(boolean alarmActionsEnabled) {
		this.alarmActionsEnabled = alarmActionsEnabled;
	}

	/**
	 * @return the availableField
	 */
	public CustomFieldDef[] getAvailableField() {
		return availableField;
	}

	/**
	 * @param availableField the availableField to set
	 */
	public void setAvailableField(CustomFieldDef[] availableField) {
		this.availableField = availableField;
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
	 * @return the configIssues
	 */
	public Event[] getConfigIssues() {
		return configIssues;
	}

	/**
	 * @param configIssues the configIssues to set
	 */
	public void setConfigIssues(Event[] configIssues) {
		this.configIssues = configIssues;
	}

	/**
	 * @return the configStatus
	 */
	public ManagedEntityStatus getConfigStatus() {
		return configStatus;
	}

	/**
	 * @param configStatus the configStatus to set
	 */
	public void setConfigStatus(ManagedEntityStatus configStatus) {
		this.configStatus = configStatus;
	}

	/**
	 * @return the customValue
	 */
	public CustomFieldValue[] getCustomValue() {
		return customValue;
	}

	/**
	 * @param customValue the customValue to set
	 */
	public void setCustomValue(CustomFieldValue[] customValue) {
		this.customValue = customValue;
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
	 * @return the declaredAlarmState
	 */
	public AlarmState[] getDeclaredAlarmState() {
		return declaredAlarmState;
	}

	/**
	 * @param declaredAlarmState the declaredAlarmState to set
	 */
	public void setDeclaredAlarmState(AlarmState[] declaredAlarmState) {
		this.declaredAlarmState = declaredAlarmState;
	}

	/**
	 * @return the disabledMethod
	 */
	public String[] getDisabledMethod() {
		return disabledMethod;
	}

	/**
	 * @param disabledMethod the disabledMethod to set
	 */
	public void setDisabledMethod(String[] disabledMethod) {
		this.disabledMethod = disabledMethod;
	}

	/**
	 * @return the effectiveRole
	 */
	public int[] getEffectiveRole() {
		return effectiveRole;
	}

	/**
	 * @param effectiveRole the effectiveRole to set
	 */
	public void setEffectiveRole(int[] effectiveRole) {
		this.effectiveRole = effectiveRole;
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
	 * @return the overallStatus
	 */
	public ManagedEntityStatus getOverallStatus() {
		return overallStatus;
	}

	/**
	 * @param overallStatus the overallStatus to set
	 */
	public void setOverallStatus(ManagedEntityStatus overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
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
	 * @return the permission
	 */
	public Permission[] getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Permission[] permission) {
		this.permission = permission;
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
	 * @return the recentTask
	 */
	public List<String> getRecentTask() {
		return recentTask;
	}

	/**
	 * @param recentTask the recentTask to set
	 */
	public void setRecentTask(List<String> recentTask) {
		this.recentTask = recentTask;
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

	/**
	 * @return the tag
	 */
	public Tag[] getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(Tag[] tag) {
		this.tag = tag;
	}

	/**
	 * @return the triggeredAlarmState
	 */
	public AlarmState[] getTriggeredAlarmState() {
		return triggeredAlarmState;
	}

	/**
	 * @param triggeredAlarmState the triggeredAlarmState to set
	 */
	public void setTriggeredAlarmState(AlarmState[] triggeredAlarmState) {
		this.triggeredAlarmState = triggeredAlarmState;
	}

	/**
	 * @return the value
	 */
	public CustomFieldValue[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(CustomFieldValue[] value) {
		this.value = value;
	}

	/**
	 * @return the moRef
	 */
	public String getMoRef() {
		return moRef;
	}

	/**
	 * @param moRef the moRef to set
	 */
	public void setMoRef(String moRef) {
		this.moRef = moRef;
	}

}
