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

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.ArrayHelper;
import com.vbranden.vsphere.rest.helpers.ConditionHelper;
import com.vbranden.vsphere.rest.helpers.DefaultValuesHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.ComputeResourceConfigSpec;
import com.vmware.vim25.HostCapability;
import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostConnectFault;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostLicensableResourceInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.HostSystemResourceInfo;
import com.vmware.vim25.InvalidLogin;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.Task;

/**
 * @author Branden Horiuchi (bhoriuchi@gmail.com)
 * @version 5
 */

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
		this.init(mo, uri, fields);
	}

	public void init(HostSystem mo, String uri, String fields) {
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
				this.setDatastore(new ManagedObjectReferenceArray()
						.getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("datastoreBrowser", fields)) {
				this.setDatastoreBrowser(new ManagedObjectReferenceUri()
						.getUri(mo.getDatastoreBrowser(), uri));
			}
			if (fg.get("hardware", fields)) {
				this.setHardware(mo.getHardware());
			}
			if (fg.get("licensableResource", fields)) {
				this.setLicensableResource(mo.getLicensableResource());
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(
						mo.getNetworks(), uri));
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
				this.setVm(new ManagedObjectReferenceArray().getMORArray(
						mo.getVms(), uri));
			}

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);

		} catch (RemoteException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, RESTRequestBody body) {

		// initialize classes
		ConditionHelper ch = new ConditionHelper();
		ArrayHelper ah = new ArrayHelper();
		DefaultValuesHelper h = new DefaultValuesHelper().init();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Task t = null;
		Folder f = null;
		ClusterComputeResource cl = null;
		ResourcePool rp = null;
		ComputeResourceConfigSpec rs = new ComputeResourceConfigSpec();

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check if a quickspec
			if (body.getName() != null) {

				// create a new spec
				HostConnectSpec hSpec = new HostConnectSpec();

				// set mandatory values
				hSpec.setForce((boolean) h.set("HostConnectSpec", "force",
						body.isForce()));
				hSpec.setHostName(body.getName());

				// if a folder was specified add it
				if (body.getVmFolder() != null
						&& !ch.getEntity((!ch.isFailed()), "Folder",
								body.getVmFolder(), v, false).isFailed()) {
					f = (Folder) ch.getObj();
					hSpec.setVmFolder(f.getMOR());
				}

				// set optional values
				ch.setObj(hSpec);
				ch.invokeSet(ch.getObj(), "setUserName", body.getUsername(),
						String.class);
				ch.invokeSet(ch.getObj(), "setPassword", body.getPassword(),
						String.class);
				ch.invokeSet(ch.getObj(), "setManagementIp",
						body.getManagementIp(), String.class);
				ch.invokeSet(ch.getObj(), "setPort", body.getPort(), int.class);
				ch.invokeSet(ch.getObj(), "setSslThumbprint",
						body.getSslThumbprint(), String.class);
				ch.invokeSet(ch.getObj(), "setVimAccountName",
						body.getVimAccountName(), String.class);
				ch.invokeSet(ch.getObj(), "setVimAccountPassword",
						body.getVimAccountPassword(), String.class);

				// set the specification
				body.setSpec(ch.getObj());

			}

			// check to make sure a spec was created
			if (!ch.checkCondition((body.getSpec() != null),
					"spec not specified").isFailed()) {

				if (body.getClusterComputeResource() != null) {

					if (!ch.getEntity(!ch.isFailed(), "ClusterComputeResource",
							body.getClusterComputeResource(), v).isFailed()) {

						// get parent object
						cl = (ClusterComputeResource) ch.getObj();

						// get the resource pool
						if (body.getResourcePool() != null
								&& !ch.getEntity(!ch.isFailed(),
										"ResourcePool", body.getResourcePool(),
										v, false).isFailed()) {
							rp = (ResourcePool) ch.getObj();
						}

						// attempt to create
						if (body.getLicense() != null) {

							t = cl.addHost_Task(
									(HostConnectSpec) body.getSpec(),
									body.isConnected(), rp, body.getLicense());
						} else {

							t = cl.addHost_Task(
									(HostConnectSpec) body.getSpec(),
									body.isConnected(), rp);
						}
					}
				}

				else {

					if (body.getParentFolder() != null
							&& !ch.getEntity(!ch.isFailed(), "Folder",
									body.getParentFolder(), v).isFailed()) {

						// get parent object
						f = (Folder) ch.getObj();
					} else if (body.getDatacenter() != null
							&& !ch.getEntity(!ch.isFailed(), "Datacenter",
									body.getDatacenter(), v).isFailed()) {

						// get datacenter host folder
						Datacenter dc = (Datacenter) ch.getObj();
						f = dc.getHostFolder();
					}

					// check that an appropriate folder was found and that it
					// can hold hosts
					if (!ch.checkCondition(
							(f != null && ah.containsCaseInsensitive(
									"HostSystem", f.getChildType())),
							"invalid HostSystem folder, or no folder found")
							.isFailed()) {
						// get compute resource spec
						if (body.getComputeResourceConfigSpec() != null) {
							rs = (ComputeResourceConfigSpec) body
									.getComputeResourceConfigSpec();
						}

						// attempt to create
						if (body.getLicense() != null) {

							t = f.addStandaloneHost_Task(
									(HostConnectSpec) body.getSpec(), rs,
									body.isConnected(), body.getLicense());
						} else {

							t = f.addStandaloneHost_Task(
									(HostConnectSpec) body.getSpec(), rs,
									body.isConnected());
						}
					}
				}
			}

			// check if cluster was created
			ch.checkCondition((t != null), "Failed to add HostSystem");

		} catch (InvalidName e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Invalid name");
		} catch (InvalidLogin e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Login failed");
		} catch (HostConnectFault e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Host failed to connect");
		} catch (Exception e) {
			e.printStackTrace();
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Unknown exception");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(t, thisUri)))
						.entity(new RESTTask(t, thisUri, fields)).build();
			} catch (URISyntaxException e) {
				ch.setFailed(true);
				ch.getResponse().setResponseStatus("failed");
				ch.getResponse().getResponseMessage()
						.add("Invalid URI created");
			}
		}

		return null;
	}

	/**
	 * @return the capability
	 */
	public HostCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
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
	 * @param config
	 *            the config to set
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
	 * @param configManager
	 *            the configManager to set
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
	 * @param datastore
	 *            the datastore to set
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
	 * @param datastoreBrowser
	 *            the datastoreBrowser to set
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
	 * @param hardware
	 *            the hardware to set
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
	 * @param licensableResource
	 *            the licensableResource to set
	 */
	public void setLicensableResource(
			HostLicensableResourceInfo licensableResource) {
		this.licensableResource = licensableResource;
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
	 * @return the runtime
	 */
	public HostRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime
	 *            the runtime to set
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
	 * @param summary
	 *            the summary to set
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
	 * @param systemResource
	 *            the systemResource to set
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
	 * @param vm
	 *            the vm to set
	 */
	public void setVm(List<String> vm) {
		this.vm = vm;
	}

}
