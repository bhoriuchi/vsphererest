package com.vbranden.vsphere.rest.models.v5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTParentObject {


	private RESTNamedManagedEntity hostSystem;
	private RESTNamedManagedEntity clusterComputeResource;
	private RESTNamedManagedEntity datacenter;
	
	
	
	/**
	 * @return the hostSystem
	 */
	public RESTNamedManagedEntity getHostSystem() {
		return hostSystem;
	}
	/**
	 * @param hostSystem the hostSystem to set
	 */
	public void setHostSystem(RESTNamedManagedEntity hostSystem) {
		this.hostSystem = hostSystem;
	}
	/**
	 * @return the clusterComputeResource
	 */
	public RESTNamedManagedEntity getClusterComputeResource() {
		return clusterComputeResource;
	}
	/**
	 * @param clusterComputeResource the clusterComputeResource to set
	 */
	public void setClusterComputeResource(
			RESTNamedManagedEntity clusterComputeResource) {
		this.clusterComputeResource = clusterComputeResource;
	}
	/**
	 * @return the datacenter
	 */
	public RESTNamedManagedEntity getDatacenter() {
		return datacenter;
	}
	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(RESTNamedManagedEntity datacenter) {
		this.datacenter = datacenter;
	}

	
	
	

	
	
	
	
}
