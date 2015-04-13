package com.vbranden.vsphere.rest.models.v5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vmware.vim25.ManagedObjectReference;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTParentObject {

	private ManagedObjectReference hostSystem;
	private ManagedObjectReference clusterComputeResource;
	private ManagedObjectReference datacenter;
	/**
	 * @return the hostSystem
	 */
	public ManagedObjectReference getHostSystem() {
		return hostSystem;
	}
	/**
	 * @param hostSystem the hostSystem to set
	 */
	public void setHostSystem(ManagedObjectReference hostSystem) {
		this.hostSystem = hostSystem;
	}
	/**
	 * @return the clusterComputeResource
	 */
	public ManagedObjectReference getClusterComputeResource() {
		return clusterComputeResource;
	}
	/**
	 * @param clusterComputeResource the clusterComputeResource to set
	 */
	public void setClusterComputeResource(
			ManagedObjectReference clusterComputeResource) {
		this.clusterComputeResource = clusterComputeResource;
	}
	/**
	 * @return the datacenter
	 */
	public ManagedObjectReference getDatacenter() {
		return datacenter;
	}
	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(ManagedObjectReference datacenter) {
		this.datacenter = datacenter;
	}

	
	
}
