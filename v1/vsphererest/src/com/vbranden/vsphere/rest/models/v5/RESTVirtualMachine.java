/*================================================================================
Copyright (c) 2015 Branden Horiuchi. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

 * Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vbranden.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vbranden.vsphere.rest.helpers.ConditionHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.GuestInfo;
//import com.vmware.vim25.HostDatastoreBrowserSearchSpec;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OutOfBounds;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.RuntimeFault;
//import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
//import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.VirtualMachineFileLayout;
import com.vmware.vim25.VirtualMachineFileLayoutEx;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineStorageInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.ClusterComputeResource;
//import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * @author Branden Horiuchi (bhoriuchi@gmail.com)
 * @version 5
 */

@JsonIgnoreProperties(ignoreUnknown = true)
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
	private RESTParentObject parentObjects;

	// constructor
	public RESTVirtualMachine() {
	}

	// overloaded constructor
	public RESTVirtualMachine(ViConnection viConnection, VirtualMachine mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	public void init(ViConnection viConnection, VirtualMachine mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// set the extended properties
			this.setManagedEntity(viConnection, mo, fields, uri);
			
			// virtual machine specific fields
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray()
						.getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("environmentBrowser", fields)) {
				this.setEnvironmentBrowser(new ManagedObjectReferenceUri()
						.getUri(mo.getEnvironmentBrowser(), uri));
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
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(
						mo.getNetworks(), uri));
			}
			if (fg.get("parentVApp", fields)) {
				this.setParentVApp(new ManagedObjectReferenceUri().getUri(
						mo.getParentVApp(), uri));
			}
			if (fg.get("resourceConfig", fields)) {
				this.setResourceConfig(mo.getResourceConfig());
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(
						mo.getResourcePool(), uri));
			}
			if (fg.get("rootSnapshot", fields)) {
				this.setRootSnapshot(new ManagedObjectReferenceArray()
						.getMORArray(mo.getRootSnapshot(), uri));
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
			if (fg.get("parentObjects", fields)) {
				this.setParentObjects(this.getParentObjects(mo));
			}			


		} catch (RemoteException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// function to find parent objects
	private RESTParentObject getParentObjects(VirtualMachine vm) {
		
		RESTParentObject po = new RESTParentObject();
		
		// get host
		ManagedObjectReference h = vm.getRuntime().getHost();
		po.setHostSystem(h);
		
		// get host parents until we reach the datacenter
		ManagedEntity me = this.getViConnection().getEntity("HostSystem", h.getVal());
		HostSystem host = (HostSystem) me;
		ManagedEntity e = host.getParent();

		// get parent objects until we reach the root folder
		while (e != null && !e.getMOR().getType().equals("Datacenter")) {

			// check if the parent is a cluster compute resource
			if (e.getMOR().getType().equals("ClusterComputeResource")) {
				po.setClusterComputeResource(e.getMOR());
			}
			
			// move to the next parent
			e = e.getParent();
		}

		// if the last parent was a datacenter, set it
		if (e != null && e.getMOR().getType().equals("Datacenter")) {
			po.setDatacenter(e.getMOR());
		}

		// return the object
		return po;
	}
	

	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			ViConnection vi, String fields, String thisUri, RESTRequestBody body) {

		// initialize classes
		ConditionHelper ch = new ConditionHelper();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ResourcePool rp = null;
		ClusterComputeResource cl = null;
		Folder f = null;
		HostSystem h = null;
		Task t = null;
		VirtualMachineConfigSpec spec = new VirtualMachineConfigSpec();

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// get the resource pool
			if (body.getClusterComputeResource() != null
					&& !ch.getEntity(!ch.isFailed(), "ClusterComputeResource",
							body.getClusterComputeResource(), vi, false)
							.isFailed()) {
				cl = (ClusterComputeResource) ch.getObj();
				rp = cl.getResourcePool();
			} else if (body.getResourcePool() != null
					&& !ch.getEntity(!ch.isFailed(), "ResourcePool",
							body.getResourcePool(), vi, false).isFailed()) {
				rp = (ResourcePool) ch.getObj();
			}

			// get the host
			if (body.getHostSystem() != null
					&& !ch.getEntity(!ch.isFailed(), "HostSystem",
							body.getHostSystem(), vi, false).isFailed()) {
				h = (HostSystem) ch.getObj();
			}

			// create the spec
			if (body.getSpec() != null) {
				spec = (VirtualMachineConfigSpec) body.getSpec();
			} else {
				
				// a name is required for a virtual machine, create a default if it is null
				if (body.getName() == null) {
					body.setName(this.getNextVmName(vi, "New Virtual Machine"));
				}

				ch.setObj(spec);
				ch.invokeSet(ch.getObj(), "setAlternateGuestName",
						body.getAlternativeGuestName(), String.class);
				ch.invokeSet(ch.getObj(), "setAnnotation",
						body.getAnnotation(), String.class);
				ch.invokeSet(ch.getObj(), "setMemoryMB", body.getMemoryMB(),
						int.class);
				ch.invokeSet(ch.getObj(), "setName", body.getName(),
						String.class);
				ch.invokeSet(ch.getObj(), "setNumCoresPerSocket",
						body.getNumCoresPerSocket(), int.class);
				ch.invokeSet(ch.getObj(), "setNumCPUs", body.getNumCPUs(),
						int.class);
				ch.invokeSet(ch.getObj(), "setVersion", body.getVersion(),
						String.class);

				spec = (VirtualMachineConfigSpec) ch.getObj();
			}

			// verify that a resource pool exists
			if (!ch.checkCondition((rp != null), "Resource Pool not found")
					.isFailed()) {

				// check if the VM is being added to a folder, otherwise add it
				// to the pool
				if (body.getParentFolder() != null
						&& !ch.getEntity(!ch.isFailed(), "Folder",
								body.getParentFolder(), vi, false).isFailed()) {
					f = (Folder) ch.getObj();
					t = f.createVM_Task(spec, rp, h);

				} else {
					t = rp.createChildVM_Task(spec, h);
				}

			}

			// check that the VM was created
			ch.checkCondition((t != null), "Failed to create Datacenter");
			
			
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VmConfigFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientResourcesFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDatastore e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(t, thisUri)))
						.entity(new RESTTask(vi, t, thisUri, fields)).build();
			} catch (URISyntaxException e) {
				ch.setFailed(true);
				ch.getResponse().setResponseStatus("failed");
				ch.getResponse().getResponseMessage()
						.add("Invalid URI created");
			}
		}

		return null;
	}
	
	// function to get the next name
	private String getNextVmName(ViConnection v, String name) {
		int count = 0;
		String newName = name;
		ManagedEntity[] vms = v.getEntities("VirtualMachine");
		
		for (ManagedEntity vm : vms) {
			if (vm.getName().matches(name)) {
				count++;
			}
		}
		
		if (count > 0) {
			newName = name + " " + count;
		}
		
		return newName;
	}
	
	// function to get the directory for vm files
	/*
	private String getVmDirectory(Datastore d, String name) {
		
		String dsRoot = "[" + d.getName() + "]";
		HostDatastoreBrowserSearchSpec spec = new HostDatastoreBrowserSearchSpec();
		spec.setMatchPattern(new String[] { name });
		spec.setSortFoldersFirst(true);
		
		try {
			
			Task t = d.getBrowser().searchDatastore_Task(dsRoot, spec);
			System.out.println("Task Created:" + t.getMOR().getVal());
			TaskInfoState state = t.getTaskInfo().getState();
			
			while (state != TaskInfoState.error && state != TaskInfoState.success) {
				state = t.getTaskInfo().getState();
			}
			
			
			
			
		} catch (FileFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDatastore e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	*/
	
	

	/**
	 * @return the capability
	 */
	public VirtualMachineCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
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
	 * @param config
	 *            the config to set
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
	 * @param datastore
	 *            the datastore to set
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
	 * @param environmentBrowser
	 *            the environmentBrowser to set
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
	 * @param guest
	 *            the guest to set
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
	 * @param guestHeartbeatStatus
	 *            the guestHeartbeatStatus to set
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
	 * @param layout
	 *            the layout to set
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
	 * @param layoutEx
	 *            the layoutEx to set
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
	 * @param network
	 *            the network to set
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
	 * @param parentVApp
	 *            the parentVApp to set
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
	 * @param resourceConfig
	 *            the resourceConfig to set
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
	 * @param resourcePool
	 *            the resourcePool to set
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
	 * @param rootSnapshot
	 *            the rootSnapshot to set
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
	 * @param runtime
	 *            the runtime to set
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
	 * @param snapshot
	 *            the snapshot to set
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
	 * @param storage
	 *            the storage to set
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
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(VirtualMachineSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the parentObjects
	 */
	public RESTParentObject getParentObjects() {
		return parentObjects;
	}

	/**
	 * @param parentObjects the parentObjects to set
	 */
	public void setParentObjects(RESTParentObject parentObjects) {
		this.parentObjects = parentObjects;
	}
}
