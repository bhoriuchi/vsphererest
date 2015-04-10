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
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.ConditionHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.ClusterConfigSpec;
import com.vmware.vim25.ComputeResourceConfigInfo;
import com.vmware.vim25.ComputeResourceSummary;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTComputeResource extends RESTManagedEntity {
	
	private ComputeResourceConfigInfo configurationEx;
	private List<String> datastore;
	private String environmentBrowser;
	private List<String> host;
	private List<String> network;
	private String resourcePool;
	private ComputeResourceSummary summary;
	
	// constructor
	public RESTComputeResource() {
	}

	// overloaded constructor
	public RESTComputeResource(ComputeResource mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(ComputeResource mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {
			
			// compute resource specific fields
			if (fg.get("configurationEx", fields)) {
				this.setConfigurationEx(mo.getConfigurationEx());
			}		
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("environmentBrowser", fields)) {
				
				this.setEnvironmentBrowser(new ManagedObjectReferenceUri().getUri(mo.getEnvironmentBrowser(), uri));
			}			
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(mo.getHosts(), uri));
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(mo.getNetworks(), uri));
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(mo.getResourcePool(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
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
		ClusterComputeResource cl = null;

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check fields and create cluster
			ch.checkCondition((body.getName() != null), "Name not specified");
			ch.checkCondition((body.getDatacenter() != null),
					"Datacenter not specified");
			if (body.getDatacenter() != null && !ch.getEntity(!ch.isFailed(), "Datacenter",
					body.getDatacenter(), v).isFailed()) {

				// create an empty spec if one doesnt exist
				if (body.getSpec() == null) {
					body.setSpec(new ClusterConfigSpec());
				}

				// create request
				Datacenter dc = (Datacenter) ch.getObj();
				cl = dc.getHostFolder().createCluster(body.getName(),
						(ClusterConfigSpec) body.getSpec());
				
			}
			
			// check if cluster was created
			ch.checkCondition((cl != null),
					"Failed to create ClusterComputeResource");

		} catch (InvalidName e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Invalid name");
		} catch (DuplicateName e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Duplicate name");
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
						.created(new URI(moUri.getUri(cl, thisUri)))
						.entity(new RESTClusterComputeResource(cl, thisUri,
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
	 * @return the configurationEx
	 */
	public ComputeResourceConfigInfo getConfigurationEx() {
		return configurationEx;
	}

	/**
	 * @param configurationEx the configurationEx to set
	 */
	public void setConfigurationEx(ComputeResourceConfigInfo configurationEx) {
		this.configurationEx = configurationEx;
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
	 * @return the host
	 */
	public List<String> getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(List<String> host) {
		this.host = host;
	}

	/**
	 * @return the summary
	 */
	public ComputeResourceSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(ComputeResourceSummary summary) {
		this.summary = summary;
	}



}
