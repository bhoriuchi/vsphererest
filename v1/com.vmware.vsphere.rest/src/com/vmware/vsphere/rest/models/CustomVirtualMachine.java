package com.vmware.vsphere.rest.models;


import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineFileLayout;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineStorageInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.EnvironmentBrowser;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;

public class CustomVirtualMachine {
	
	
	private String id;
	private String name;
	
	private VirtualMachineCapability capability;
	private VirtualMachineConfigInfo config;
	private List<ManagedObjectReference> datastore;
	private ManagedObjectReference environmentBrowser;
	private GuestInfo guest;
	private ManagedEntityStatus guestHeartbeatStatus;
	private VirtualMachineFileLayout layout;
	private List<ManagedObjectReference> network;
	private ManagedObjectReference parentVApp;
	private ResourceConfigSpec resourceConfig;
	private ManagedObjectReference resourcePool;
	private List<ManagedObjectReference> rootSnapshot;
	private VirtualMachineRuntimeInfo runtime;
	private VirtualMachineSnapshotInfo snapshot;
	private VirtualMachineStorageInfo storage;
	private VirtualMachineSummary summary;

	// constructor
	public CustomVirtualMachine() { }

	// overloaded constructor
	public CustomVirtualMachine(VirtualMachine vm) {
		
		try {
			
			// simple values
			this.id = vm.getMOR().getVal();
			this.name = vm.getName();
		
			// complex values
			
			this.capability = vm.getCapability();
			this.config = vm.getConfig();
			
			this.datastore = new ManagedObjectReferenceArray().getMORArray(vm.getDatastores());
			
			this.environmentBrowser = vm.getEnvironmentBrowser().getMOR();
			this.guest = vm.getGuest();
			this.guestHeartbeatStatus = vm.getGuestHeartbeatStatus();
			this.layout = vm.getLayout();
			this.network = new ManagedObjectReferenceArray().getMORArray(vm.getNetworks());
			this.parentVApp = vm.getParentVApp().getMOR();
			this.resourceConfig = vm.getResourceConfig();
			this.resourcePool = vm.getResourcePool().getMOR();
			
			this.rootSnapshot = new ManagedObjectReferenceArray().getMORArray(vm.getRootSnapshot());
			this.runtime = vm.getRuntime();
			this.snapshot = vm.getSnapshot();
			this.storage = vm.getStorage();
			this.summary = vm.getSummary();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public VirtualMachineCapability getCapability() { return this.capability; }
	public VirtualMachineConfigInfo getConfig() { return this.config; }
	public List<ManagedObjectReference> getDatastore() { return this.datastore; }
	public ManagedObjectReference getEnvironmentBrowser() { return this.environmentBrowser; }
	public GuestInfo getGuest() { return this.guest; }
	public ManagedEntityStatus getGuestHeartbeatStatus() { return this.guestHeartbeatStatus; }
	public VirtualMachineFileLayout getVirtualMachineFileLayout() { return this.layout; }
	public List<ManagedObjectReference> getNetwork() { return this.network; }
	public ManagedObjectReference getParentVApp() { return this.parentVApp; }
	public ResourceConfigSpec getResourceConfig() { return this.resourceConfig; }
	public ManagedObjectReference getResourcePool() { return this.resourcePool; }
	public List<ManagedObjectReference> getRootSnapshot() { return this.rootSnapshot; }
	public VirtualMachineRuntimeInfo getRuntime() { return this.runtime; }
	public VirtualMachineSnapshotInfo getSnapshot() { return this.snapshot; }
	public VirtualMachineStorageInfo getStorage() { return this.storage; }
	public VirtualMachineSummary getSummary() { return this.summary; }
	
	

}
