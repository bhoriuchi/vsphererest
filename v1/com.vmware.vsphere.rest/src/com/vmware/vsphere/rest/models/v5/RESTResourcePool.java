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
import java.util.List;

import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.ResourcePoolRuntimeInfo;
import com.vmware.vim25.ResourcePoolSummary;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
	public RESTResourcePool(ResourcePool mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}
	
	public void init(ResourcePool mo, String uri, String fields) {
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
			this.setManagedEntity(mo, fields, uri);


		} catch (RemoteException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
