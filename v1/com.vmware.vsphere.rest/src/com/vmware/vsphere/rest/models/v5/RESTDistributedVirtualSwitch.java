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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.DVSCapability;
import com.vmware.vim25.DVSConfigInfo;
import com.vmware.vim25.DVSCreateSpec;
import com.vmware.vim25.DVSNetworkResourcePool;
import com.vmware.vim25.DVSRuntimeInfo;
import com.vmware.vim25.DVSSummary;
import com.vmware.vim25.DistributedVirtualSwitchProductSpec;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.DvsFault;
import com.vmware.vim25.DvsNotAuthorized;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.VMwareDVSConfigSpec;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.Task;

import com.vmware.vsphere.rest.helpers.ArrayHelper;
import com.vmware.vsphere.rest.helpers.ConditionHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
	public RESTDistributedVirtualSwitch(DistributedVirtualSwitch mo,
			String uri, String fields) {
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
				this.setPortgroup(new ManagedObjectReferenceArray()
						.getMORArray(mo.getPortgroup(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("uuid", fields)) {
				this.setUuid(mo.getUuid());
			}

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);

		} catch (InvocationTargetException | NoSuchMethodException e) {
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
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Folder f = null;
		Task t = null;

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {
			
			// get the parent folder. it can either be specified by the user or be placed in the root network folder on the datacenter
			if (body.getDatacenter() != null) {
				
				Datacenter dc = (Datacenter) v.getEntity("Datacenter",
						body.getDatacenter());
				f = dc.getNetworkFolder();
				
			} else if (body.getParentFolder() != null) {

				f = (Folder) v.getEntity("Folder", body.getParentFolder());
			}
			
			// verify folder
			if (!ch.checkCondition((f != null && ah.containsCaseInsensitive("DistributedVirtualSwitch",
					f.getChildType())), "Invalid folder").isFailed()) {
				
				// check if quickconfig
				if (body.getName() != null) {

					// create a default config spec
					DVSCreateSpec spec = new DVSCreateSpec();
					VMwareDVSConfigSpec dvSpec = new VMwareDVSConfigSpec();
					dvSpec.setName(body.getName());
					spec.setConfigSpec(dvSpec);
					
					// check if a specific dvSwitch version was requested
					if (body.getVersion() != null) {
						DistributedVirtualSwitchProductSpec pSpec = new DistributedVirtualSwitchProductSpec();
						pSpec.setVersion(body.getVersion());
						spec.setProductInfo(pSpec);
					}
					
					// set the spec
					body.setSpec(spec);
				}
				
				// check spec
				if (!ch.checkCondition((body.getSpec() != null), "spec not specified").isFailed()) {
					t = f.createDVS_Task((DVSCreateSpec) body.getSpec());
				}
			}
			
			
			// check if cluster was created
			ch.checkCondition((t != null),
					"Failed to create DistributedVirtualSwitch");

		} catch (DvsNotAuthorized e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Not authorized");
		} catch (DvsFault e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("DvsFault");
		} catch (DuplicateName e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Duplicate Name");
		} catch (InvalidName e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Invalid Name");
		} catch (Exception e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Unknown Error");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response
						.created(new URI(moUri.getUri(t, thisUri)))
						.entity(new RESTTask(t, thisUri,
								fields)).build();
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
	public DVSCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
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
	 * @param config
	 *            the config to set
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
	 * @param networkResourcePool
	 *            the networkResourcePool to set
	 */
	public void setNetworkResourcePool(
			DVSNetworkResourcePool[] networkResourcePool) {
		this.networkResourcePool = networkResourcePool;
	}

	/**
	 * @return the portgroup
	 */
	public List<String> getPortgroup() {
		return portgroup;
	}

	/**
	 * @param portgroup
	 *            the portgroup to set
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
	 * @param runtime
	 *            the runtime to set
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
	 * @param summary
	 *            the summary to set
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
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
