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

import com.vmware.vim25.DatastoreCapability;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostNasVolumeSpec;
import com.vmware.vim25.StorageIORMInfo;
import com.vmware.vim25.VmfsDatastoreCreateSpec;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostDatastoreSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vsphere.rest.helpers.ConditionHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

/**
 * @author Branden Horiuchi (bhoriuchi@gmail.com)
 * @version 5
 */

public class RESTDatastore extends RESTManagedEntity {

	private String browser;
	private DatastoreCapability capability;
	private DatastoreHostMount[] host;
	private DatastoreInfo info;
	private StorageIORMInfo iormConfiguration;
	private DatastoreSummary summary;
	private List<String> vm;

	// constructor
	public RESTDatastore() {
	}

	// overloaded constructor
	public RESTDatastore(Datastore mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Datastore mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("browser", fields)) {
				this.setBrowser(new ManagedObjectReferenceUri().getUri(
						mo.getBrowser(), uri));
			}
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("host", fields)) {
				this.setHost(mo.getHost());
			}
			if (fg.get("info", fields)) {
				this.setInfo(mo.getInfo());
			}
			if (fg.get("iormConfiguration", fields)) {
				this.setIormConfiguration(mo.getIormConfiguration());
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
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Datastore d = null;

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check for mandatory fields
			ch.checkCondition((body.getHostSystem() != null),
					"hostSystem not specified");
			ch.checkCondition((body.getType() != null), "Type not specified");

			// get the host system
			if (body.getHostSystem() != null
					&& !ch.getEntity(!ch.isFailed(), "HostSystem",
							body.getHostSystem(), v).isFailed()) {

				// get the HostDatastoreSystem
				HostSystem h = (HostSystem) ch.getObj();
				HostDatastoreSystem ds = h.getHostDatastoreSystem();

				// determine the type of Datastore to add
				if (body.getType() == "local"
						&& !ch.checkCondition((body.getPath() != null),
								"path not specified").isFailed()) {

					d = ds.createLocalDatastore(body.getName(), body.getPath());

				} else if (body.getType() == "nas"
						&& !ch.checkCondition((body.getSpec() != null),
								"spec not specified").isFailed()) {

					d = ds.createNasDatastore((HostNasVolumeSpec) body
							.getSpec());

				} else if (body.getType() == "vmfs"
						&& !ch.checkCondition((body.getSpec() != null),
								"spec not specified").isFailed()) {

					d = ds.createVmfsDatastore((VmfsDatastoreCreateSpec) body
							.getSpec());

				}

				// check that the object was created
				ch.checkCondition((d != null), "Failed to create Datstore");
			}

		} catch (Exception e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage()
					.add("Failed to create the Datastore");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(d, thisUri)))
						.entity(new RESTDatastore(d, thisUri, fields)).build();
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
	public DatastoreCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
	 */
	public void setCapability(DatastoreCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser
	 *            the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the host
	 */
	public DatastoreHostMount[] getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(DatastoreHostMount[] host) {
		this.host = host;
	}

	/**
	 * @return the info
	 */
	public DatastoreInfo getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(DatastoreInfo info) {
		this.info = info;
	}

	/**
	 * @return the iormConfiguration
	 */
	public StorageIORMInfo getIormConfiguration() {
		return iormConfiguration;
	}

	/**
	 * @param iormConfiguration
	 *            the iormConfiguration to set
	 */
	public void setIormConfiguration(StorageIORMInfo iormConfiguration) {
		this.iormConfiguration = iormConfiguration;
	}

	/**
	 * @return the summary
	 */
	public DatastoreSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(DatastoreSummary summary) {
		this.summary = summary;
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
