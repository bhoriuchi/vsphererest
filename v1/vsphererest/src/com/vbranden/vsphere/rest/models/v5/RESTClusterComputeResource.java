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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.ClusterActionHistory;
import com.vmware.vim25.ClusterConfigInfo;
import com.vmware.vim25.ClusterDrsFaults;
import com.vmware.vim25.ClusterDrsMigration;
import com.vmware.vim25.ClusterDrsRecommendation;
import com.vmware.vim25.ClusterRecommendation;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.StoragePod;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTClusterComputeResource extends RESTComputeResource {

	private ClusterActionHistory[] actionHistory;
	private ClusterConfigInfo configuration;
	private ClusterDrsFaults[] drsFault;
	private ClusterDrsRecommendation[] drsRecommendation;
	private ClusterDrsMigration[] migrationHistory;
	private ClusterRecommendation[] recommendation;
	private List<String> storagePod;

	// constructor
	public RESTClusterComputeResource() {
	}

	// overloaded constructor
	public RESTClusterComputeResource(ViConnection viConnection, ClusterComputeResource mo, String uri,
			String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	public void init(ViConnection viConnection, ClusterComputeResource mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// set the extended properties
			this.setManagedEntity(viConnection, mo, fields, uri);
			
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
			if (fg.get("storagePod", fields)) {
				
				StoragePod[] podArray = this.getStoragePods(viConnection, mo);
				
				this.setStoragePod(new ManagedObjectReferenceArray()
						.getMORArray(podArray, uri));
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
			if (fg.get("parentObjects", fields)) {
				this.setParentObjects(this.getParentObjects(mo));
			}	



		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// function to find parent objects
	private RESTParentObject getParentObjects(ClusterComputeResource cluster) {
		
		RESTParentObject po = new RESTParentObject();
		ManagedEntity e = cluster.getParent();

		// get parent objects until we reach the root folder
		while (e != null && !e.getMOR().getType().equals("Datacenter")) {

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
	 * get this objects children
	 */
	public Response getChildren(String vimType, String vimClass,
			String restClass, ViConnection vi, String search, String fieldStr, String thisUri,
			String id, String childType, int start, int position, int results) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = vi.getEntity("ClusterComputeResource", id);

			if (m == null) {
				return Response.status(404).build();
			}

			// type cast the object
			ClusterComputeResource mo = (ClusterComputeResource) m;
			Object e = null;

			if (childType.toLowerCase().equals("resourcepool")) {

				e = new ManagedObjectReferenceArray().getObjectArray(vi, mo.getResourcePool().getResourcePools(),
						ResourcePool.class, RESTResourcePool.class, search, thisUri,
						fieldStr, position, start, results, false);
			}
			else if (childType.toLowerCase().equals("datastore")) {

				e = new ManagedObjectReferenceArray().getObjectArray(vi, mo.getDatastores(),
						Datastore.class, RESTDatastore.class, search, thisUri,
						fieldStr, position, start, results, false);
			}
			else if (childType.toLowerCase().equals("storagepod")) {
				
				StoragePod[] podArray = this.getStoragePods(vi, mo);
				
				e = new ManagedObjectReferenceArray().getObjectArray(vi, podArray,
						StoragePod.class, RESTStoragePod.class, search, thisUri,
						fieldStr, position, start, results, false);

			}
			else if (childType.toLowerCase().equals("network")) {

				e = new ManagedObjectReferenceArray().getObjectArray(vi, mo.getNetworks(),
						Network.class, RESTNetwork.class, search, thisUri,
						fieldStr, position, start, results, false);
			}
			else if (childType.toLowerCase().equals("hostsystem")) {

				e = new ManagedObjectReferenceArray().getObjectArray(vi, mo.getHosts(),
						HostSystem.class, RESTHostSystem.class, search, thisUri,
						fieldStr, position, start, results, false);
			}
			

			if (e == null) {
				return Response.status(404).build();
			} else {
				return Response.ok().entity(e).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (InvalidProperty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(404).build();

	}
	
	
	private StoragePod[] getStoragePods(ViConnection vi, ClusterComputeResource mo) {
		Datastore[] datastores = mo.getDatastores();
		ManagedEntity[] storagePods = vi.getEntities("StoragePod");
		List<String> datastoreIds = new ArrayList<String>();
		List<String> podIds = new ArrayList<String>();
		List<StoragePod> pods = new ArrayList<StoragePod>();
		
		// get a list of all datastore ids
		for (Datastore datastore : datastores) {
			datastoreIds.add(datastore.getMOR().getVal());
		}
		
		// find the datastores that belong to the pods
		if (storagePods != null && storagePods.length > 0) {
			

			for (ManagedEntity p : storagePods) {
				StoragePod sp = (StoragePod) p;
				
				try {
					for (ManagedEntity child : sp.getChildEntity()) {
						
						if (datastoreIds.contains(child.getMOR().getVal()) && !podIds.contains(sp.getMOR().getVal())) {
							
							podIds.add(sp.getMOR().getVal());
							pods.add(sp);
						}
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// initialize an empty array
		StoragePod[] podArray = new StoragePod[0];
		
		if (pods.size() > 0) {
			podArray = pods.toArray(new StoragePod[pods.size()]);
		}

		return podArray;

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

	/**
	 * @return the storagePod
	 */
	public List<String> getStoragePod() {
		return storagePod;
	}

	/**
	 * @param storagePod the storagePod to set
	 */
	public void setStoragePod(List<String> storagePod) {
		this.storagePod = storagePod;
	}

}
