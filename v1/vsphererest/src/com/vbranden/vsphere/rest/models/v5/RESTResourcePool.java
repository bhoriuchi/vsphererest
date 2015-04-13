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
import com.vmware.vim25.ResourceAllocationInfo;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.ResourcePoolRuntimeInfo;
import com.vmware.vim25.ResourcePoolSummary;
import com.vmware.vim25.SharesInfo;
import com.vmware.vim25.SharesLevel;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ResourcePool;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTResourcePool extends RESTManagedEntity {
	
	private ResourceConfigSpec[] childConfiguration;
	private ResourceConfigSpec config;
	private String owner;
	private List<String> resourcePool;
	private ResourcePoolRuntimeInfo runtime;
	private ResourcePoolSummary summary;
	private List<String> vm;
	
	// constructor
	public RESTResourcePool() {
	}

	// overloaded constructor
	public RESTResourcePool(ViConnection viConnection, ResourcePool mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}
	
	public void init(ViConnection viConnection, ResourcePool mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields
			if (fg.get("childConfiguration", fields)) {
				this.setChildConfiguration(mo.getChildConfiguration());
			}		
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("owner", fields)) {
				this.setOwner(new ManagedObjectReferenceUri().getUri(mo.getOwner(), uri));
			}			
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceArray().getMORArray(mo.getResourcePools(), uri));
			}
			if (fg.get("runtime", fields)) {
				this.setRuntime(mo.getRuntime());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(mo.getVMs(), uri));
			}
			
			
			// set the extended properties
			this.setManagedEntity(viConnection, mo, fields, uri);


		} catch (RemoteException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			ViConnection vi,
			String fields, String thisUri, RESTRequestBody body) {

		// initialize classes
		ConditionHelper ch = new ConditionHelper();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ResourcePool rp = null;
		ResourcePool parentPool = null;
		ClusterComputeResource cl = null;
		ResourceConfigSpec spec = null;


		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {
			
			// check fields and create resourcepool
			if (!ch.checkCondition((body.getName() != null), "Name not specified").isFailed()) {
				if (body.getClusterComputeResource() != null && !ch.getEntity(!ch.isFailed(), "ClusterComputeResource", body.getClusterComputeResource(), vi, false).isFailed()) {
					cl = (ClusterComputeResource) ch.getObj();
					parentPool = cl.getResourcePool();
				}
				else if (body.getParentResourcePool() != null && !ch.getEntity(!ch.isFailed(), "ResourcePool", body.getParentResourcePool(), vi, false).isFailed()) {
					parentPool = (ResourcePool) ch.getObj();
				}
				
				// check that a parent resource pool exists
				if (!ch.checkCondition((parentPool != null), "No parent Resource Pool found").isFailed()) {
					
					
					// check for specification
					if (body.getSpec() == null) {

						// create a new resource config spec
						spec = new ResourceConfigSpec();

						// create a cpu allocation
						ResourceAllocationInfo cpuAlloc = new ResourceAllocationInfo();
						SharesInfo cpuShareInfo = new SharesInfo();
						cpuAlloc.setExpandableReservation(true);
						cpuAlloc.setLimit((long) -1);
						cpuAlloc.setReservation((long) 0);
						cpuShareInfo.setLevel(SharesLevel.normal);
						cpuShareInfo.setShares(parentPool.getConfig().getCpuAllocation().getShares().getShares());
						cpuAlloc.setShares(cpuShareInfo);
						spec.setCpuAllocation(cpuAlloc);					
						
						// create a memory allocation
						ResourceAllocationInfo memAlloc = new ResourceAllocationInfo();					
						SharesInfo memShareInfo = new SharesInfo();						
						memAlloc.setExpandableReservation(true);
						memAlloc.setLimit((long) -1);
						memAlloc.setReservation((long) 0);
						memShareInfo.setLevel(SharesLevel.normal);
						memShareInfo.setShares(parentPool.getConfig().getMemoryAllocation().getShares().getShares());
						memAlloc.setShares(memShareInfo);
						spec.setMemoryAllocation(memAlloc);

					}
					else {
						spec = (ResourceConfigSpec) body.getSpec();
					}
					
					// attempt to create the resource pool
					rp = parentPool.createResourcePool(body.getName(), spec);
				}
			}
			
			// check that the object was created
			ch.checkCondition((rp != null), "Failed to create Resource Pool");

		} catch (Exception e) {
			e.printStackTrace();
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Unknown Error");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(rp, thisUri)))
						.entity(new RESTResourcePool(vi, rp, thisUri, fields))
						.build();
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
	 * @return the childConfiguration
	 */
	public ResourceConfigSpec[] getChildConfiguration() {
		return childConfiguration;
	}

	/**
	 * @param childConfiguration the childConfiguration to set
	 */
	public void setChildConfiguration(ResourceConfigSpec[] childConfiguration) {
		this.childConfiguration = childConfiguration;
	}

	/**
	 * @return the config
	 */
	public ResourceConfigSpec getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(ResourceConfigSpec config) {
		this.config = config;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the resourcePool
	 */
	public List<String> getResourcePool() {
		return resourcePool;
	}

	/**
	 * @param resourcePool the resourcePool to set
	 */
	public void setResourcePool(List<String> resourcePool) {
		this.resourcePool = resourcePool;
	}

	/**
	 * @return the runtime
	 */
	public ResourcePoolRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(ResourcePoolRuntimeInfo runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the summary
	 */
	public ResourcePoolSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(ResourcePoolSummary summary) {
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
