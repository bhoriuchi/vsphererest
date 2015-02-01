package com.vmware.vsphere.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTRequestBody {

	private String name;
	private String id;
	private String moRef;
	private String datacenter;
	private String clusterComputeResource;
	private String reference;
	private Object spec;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the moRef
	 */
	public String getMoRef() {
		return moRef;
	}
	/**
	 * @param moref the moRef to set
	 */
	public void setMoRef(String moRef) {
		this.moRef = moRef;
	}
	/**
	 * @return the datacenter
	 */
	public String getDatacenter() {
		return datacenter;
	}
	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * @return the clusterComputeResource
	 */
	public String getClusterComputeResource() {
		return clusterComputeResource;
	}
	/**
	 * @param clusterComputeResource the clusterComputeResource to set
	 */
	public void setClusterComputeResource(String clusterComputeResource) {
		this.clusterComputeResource = clusterComputeResource;
	}
	/**
	 * @return the spec
	 */
	public Object getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(Object spec) {
		this.spec = spec;
	}
}
