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

package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.HostCapability;
import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostLicensableResourceInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.HostSystemResourceInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Task;

import com.vmware.vsphere.rest.helpers.DefaultValuesHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

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

		// initialize a custom response
		RESTCustomResponse cr = new RESTCustomResponse("",
				new ArrayList<String>());

		if (body == null) {

			cr.setResponseStatus("failed");
			cr.getResponseMessage().add(
					"No message body was specified in the request");

			return Response.status(400).entity(cr).build();
		}

		// instantiate helpers
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		DefaultValuesHelper h = new DefaultValuesHelper().init();
		HostConnectSpec spec = new HostConnectSpec();

		// create a new service instance
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Task t = null;

		if (body.getName() != null) {

			// create a new spec

			// set mandatory values
			spec.setForce((boolean) h.set("HostConnectSpec", "force",
					body.isForce()));
			spec.setHostName(body.getName());
			spec.setUserName((String) h.set("HostConnectSpec", "userName",
					body.getUsername()));

			if (body.getPassword() != null) {
				spec.setPassword(body.getPassword());
			}

			// optional values
			if (body.getManagementIp() != null) {
				spec.setManagementIp(body.getManagementIp());
			}
			if (body.getSslThumbprint() != null) {
				spec.setSslThumbprint(body.getSslThumbprint());
			}
			if (body.getVmFolder() != null) {
				Folder f = (Folder) v.getEntity("Folder", body.getVmFolder());

				if (f != null) {
					spec.setVmFolder(f.getMOR());
				}
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
