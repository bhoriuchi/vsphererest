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

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.ArrayHelper;
import com.vbranden.vsphere.rest.helpers.ConditionHelper;
import com.vbranden.vsphere.rest.helpers.DefaultValuesHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.DvsFault;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VmwareDistributedVirtualSwitchPvlanSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchTrunkVlanSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTDistributedVirtualPortgroup extends RESTNetwork {

	private DVPortgroupConfigInfo config;
	private String key;
	private String[] portKeys;

	// constructor
	public RESTDistributedVirtualPortgroup() {
	}

	// overloaded constructor
	public RESTDistributedVirtualPortgroup(DistributedVirtualPortgroup mo,
			String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(DistributedVirtualPortgroup mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("key", fields)) {
				this.setKey(mo.getKey());
			}
			if (fg.get("portKeys", fields)) {
				this.setPortKeys(mo.getPortKeys());
			}

			// extended from RESTNetwork
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(
						mo.getHosts(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(
						mo.getVms(), uri));
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
		DefaultValuesHelper dh = new DefaultValuesHelper().init();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		VmwareDistributedVirtualSwitch s = null;
		Task t = null;
		

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check required values
			ch.checkCondition((body.getDistributedVirtualSwitch() != null), "distributedVirtualSwitch not specified");
			
			// get dvSwitch
			if (body.getDistributedVirtualSwitch() != null && !ch.getEntity(!ch.isFailed(), "VmwareDistributedVirtualSwitch",
							body.getDistributedVirtualSwitch(), v).isFailed()) {
				
				// get the switch as a variable
				s = (VmwareDistributedVirtualSwitch) ch.getObj();
			
				// check if a quick config was used
				if (body.getName() != null) {
					
					// create a config spec with minimal options
					DVPortgroupConfigSpec spec = new DVPortgroupConfigSpec();
					VMwareDVSPortSetting dvps = new VMwareDVSPortSetting();
					
					// configure vlanId
					if (body.getVlanId() != -1) {

						VmwareDistributedVirtualSwitchVlanIdSpec vs = new VmwareDistributedVirtualSwitchVlanIdSpec();
						vs.setVlanId(body.getVlanId());
						vs.setInherited( (Boolean) dh.set("VmwareDistributedVirtualSwitchVlanIdSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);

					}
					
					// configure Trunk Vlan
					else if (body.getVlanTrunk() != null) {
						
						VmwareDistributedVirtualSwitchTrunkVlanSpec vs = new VmwareDistributedVirtualSwitchTrunkVlanSpec();
						ArrayHelper ah = new ArrayHelper();
						vs.setVlanId(ah.getNumericRange(body.getVlanTrunk()));
						vs.setInherited( (Boolean) dh.set("VmwareDistributedVirtualSwitchTrunkVlanSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);
					}
					
					// configure pVlan
					else if (body.getpVlanId() != -1) {
						
						VmwareDistributedVirtualSwitchPvlanSpec vs = new VmwareDistributedVirtualSwitchPvlanSpec();
						vs.setPvlanId(body.getpVlanId());
						vs.setInherited( (Boolean) dh.set("VmwareDistributedVirtualSwitchPvlanSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);
					}

					// update default spec
					spec.setName(body.getName());
					spec.setType( (String) dh.set("DVPortgroupConfigSpec", "type", body.getType()) );
					spec.setNumPorts( (Integer) dh.set("DVPortgroupConfigSpec", "numPorts", body.getNumPorts()) );
					spec.setDefaultPortConfig(dvps);
					
					// set the body spec to the newly created spec
					body.setSpec(spec);
					
				}

				
				// attempt to create the dvpg
				if (!ch.checkCondition((body.getSpec() != null), "spec not specified").isFailed()) {

					t = s.createDVPortgroup_Task((DVPortgroupConfigSpec) body
							.getSpec());
					
				}
				
				// check that a task was created
				ch.checkCondition((t != null), "Failed to create DistributedVirtualPortGroup");
			}

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
			ch.getResponse().getResponseMessage().add("Unknown Exception");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(t, thisUri)))
						.entity(new RESTTask(t, thisUri, fields))
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
	 * @return the config
	 */
	public DVPortgroupConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(DVPortgroupConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the portKeys
	 */
	public String[] getPortKeys() {
		return portKeys;
	}

	/**
	 * @param portKeys
	 *            the portKeys to set
	 */
	public void setPortKeys(String[] portKeys) {
		this.portKeys = portKeys;
	}

}
