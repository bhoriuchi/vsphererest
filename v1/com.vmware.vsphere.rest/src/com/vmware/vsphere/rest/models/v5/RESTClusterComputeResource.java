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

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.ClusterActionHistory;
import com.vmware.vim25.ClusterConfigInfo;
import com.vmware.vim25.ClusterConfigSpec;
import com.vmware.vim25.ClusterDrsFaults;
import com.vmware.vim25.ClusterDrsMigration;
import com.vmware.vim25.ClusterDrsRecommendation;
import com.vmware.vim25.ClusterRecommendation;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;

import com.vmware.vsphere.rest.helpers.ConditionHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTClusterComputeResource extends RESTComputeResource {

	private ClusterActionHistory[] actionHistory;
	private ClusterConfigInfo configuration;
	private ClusterDrsFaults[] drsFault;
	private ClusterDrsRecommendation[] drsRecommendation;
	private ClusterDrsMigration[] migrationHistory;
	private ClusterRecommendation[] recommendation;

	// constructor
	public RESTClusterComputeResource() {
	}

	// overloaded constructor
	public RESTClusterComputeResource(ClusterComputeResource mo, String uri,
			String fields) {
		this.init(mo, uri, fields);
	}

	public void init(ClusterComputeResource mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("actionHistory", fields)) {
				this.setActionHistory(mo.getActionHistory());
			}
			if (fg.get("configuration", fields)) {
				this.setConfiguration(mo.getConfiguration());
			}
			if (fg.get("drsFault", fields)) {
				this.setDrsFault(mo.getDrsFault());
			}
			if (fg.get("drsRecommendation", fields)) {
				this.setDrsRecommendation(mo.getDrsRecommendation());
			}
			if (fg.get("migrationHistory", fields)) {
				this.setMigrationHistory(mo.getMigrationHistory());
			}
			if (fg.get("recommendation", fields)) {
				this.setRecommendation(mo.getRecommendation());
			}

			// compute resource specific fields
			if (fg.get("configurationEx", fields)) {
				this.setConfigurationEx(mo.getConfigurationEx());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray()
						.getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("environmentBrowser", fields)) {

				this.setEnvironmentBrowser(new ManagedObjectReferenceUri()
						.getUri(mo.getEnvironmentBrowser(), uri));
			}
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(
						mo.getHosts(), uri));
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(
						mo.getNetworks(), uri));
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(
						mo.getResourcePool(), uri));
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
		ClusterComputeResource mo = null;

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check fields and create cluster
			ch.checkCondition((body.getName() != null), "Name not specified")
					.isFailed();
			ch.checkCondition((body.getDatacenter() != null),
					"Datacenter not specified");
			if (!ch.getEntity(!ch.isFailed(), "Datacenter",
					body.getDatacenter(), v).isFailed()) {

				// create an empty spec if one doesnt exist
				if (body.getSpec() == null) {
					body.setSpec(new ClusterConfigSpec());
				}

				// create request
				Datacenter dc = (Datacenter) ch.getObj();
				mo = dc.getHostFolder().createCluster(body.getName(),
						(ClusterConfigSpec) body.getSpec());
				ch.checkCondition((mo != null),
						"Failed to create ClusterComputeResource");
			}

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
						.created(new URI(moUri.getUri(mo, thisUri)))
						.entity(new RESTClusterComputeResource(mo, thisUri,
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

	/*
	 * update this object
	 */

	/**
	 * @return the actionHistory
	 */
	public ClusterActionHistory[] getActionHistory() {
		return actionHistory;
	}

	/**
	 * @param actionHistory
	 *            the actionHistory to set
	 */
	public void setActionHistory(ClusterActionHistory[] actionHistory) {
		this.actionHistory = actionHistory;
	}

	/**
	 * @return the configuration
	 */
	public ClusterConfigInfo getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(ClusterConfigInfo configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the drsFault
	 */
	public ClusterDrsFaults[] getDrsFault() {
		return drsFault;
	}

	/**
	 * @param drsFault
	 *            the drsFault to set
	 */
	public void setDrsFault(ClusterDrsFaults[] drsFault) {
		this.drsFault = drsFault;
	}

	/**
	 * @return the drsRecommendation
	 */
	public ClusterDrsRecommendation[] getDrsRecommendation() {
		return drsRecommendation;
	}

	/**
	 * @param drsRecommendation
	 *            the drsRecommendation to set
	 */
	public void setDrsRecommendation(
			ClusterDrsRecommendation[] drsRecommendation) {
		this.drsRecommendation = drsRecommendation;
	}

	/**
	 * @return the migrationHistory
	 */
	public ClusterDrsMigration[] getMigrationHistory() {
		return migrationHistory;
	}

	/**
	 * @param migrationHistory
	 *            the migrationHistory to set
	 */
	public void setMigrationHistory(ClusterDrsMigration[] migrationHistory) {
		this.migrationHistory = migrationHistory;
	}

	/**
	 * @return the recommendation
	 */
	public ClusterRecommendation[] getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendation
	 *            the recommendation to set
	 */
	public void setRecommendation(ClusterRecommendation[] recommendation) {
		this.recommendation = recommendation;
	}

}
