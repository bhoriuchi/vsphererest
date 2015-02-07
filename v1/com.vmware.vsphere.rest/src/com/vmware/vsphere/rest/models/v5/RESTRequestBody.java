package com.vmware.vsphere.rest.models.v5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTRequestBody {

	private String name;
	private String id;
	private String moRef;
	private String datacenter;
	private String clusterComputeResource;
	private String hostSystem;
	private String reference;
	private Object spec;
	private String locale;
	private String type;
	private String path;
	
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
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the hostSystem
	 */
	public String getHostSystem() {
		return hostSystem;
	}
	/**
	 * @param hostSystem the hostSystem to set
	 */
	public void setHostSystem(String hostSystem) {
		this.hostSystem = hostSystem;
	}
}
