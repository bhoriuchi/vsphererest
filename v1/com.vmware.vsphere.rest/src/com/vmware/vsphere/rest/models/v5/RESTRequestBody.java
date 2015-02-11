package com.vmware.vsphere.rest.models.v5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTRequestBody {

	private String name;
	private String id;
	private String moRef;
	private String datacenter;
	private String clusterComputeResource;
	private String resourcePool;
	private String distributedVirtualSwitch;
	private String hostSystem;
	private String reference;
	private String inherited;
	private String locale;
	private String type;
	private String path;
	private String version;
	private String parentFolder;
	private String vmFolder;
	private String vlanTrunk;	
	private String username;
	private String password;
	private String managementIp;
	private String sslThumbprint;
	private String license;
	
	
	
	private int numPorts = -1;
	private int vlanId = -1;
	private int pVlanId = -1;

	private boolean force = false;
	private boolean asConnected = true;
	
	private Object spec;
	private Object[] specs;	
	
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
	/**
	 * @return the distributedVirtualSwitch
	 */
	public String getDistributedVirtualSwitch() {
		return distributedVirtualSwitch;
	}
	/**
	 * @param distributedVirtualSwitch the distributedVirtualSwitch to set
	 */
	public void setDistributedVirtualSwitch(String distributedVirtualSwitch) {
		this.distributedVirtualSwitch = distributedVirtualSwitch;
	}
	/**
	 * @return the specs
	 */
	public Object[] getSpecs() {
		return specs;
	}
	/**
	 * @param specs the specs to set
	 */
	public void setSpecs(Object[] specs) {
		this.specs = specs;
	}
	/**
	 * @return the inherited
	 */
	public String getInherited() {
		return inherited;
	}
	/**
	 * @param inherited the inherited to set
	 */
	public void setInherited(String inherited) {
		this.inherited = inherited;
	}
	/**
	 * @return the numPorts
	 */
	public int getNumPorts() {
		return numPorts;
	}
	/**
	 * @param numPorts the numPorts to set
	 */
	public void setNumPorts(int numPorts) {
		this.numPorts = numPorts;
	}
	/**
	 * @return the vlanId
	 */
	public int getVlanId() {
		return vlanId;
	}
	/**
	 * @param vlanId the vlanId to set
	 */
	public void setVlanId(int vlanId) {
		this.vlanId = vlanId;
	}
	/**
	 * @return the vlanTrunk
	 */
	public String getVlanTrunk() {
		return vlanTrunk;
	}
	/**
	 * @param vlanTrunk the vlanTrunk to set
	 */
	public void setVlanTrunk(String vlanTrunk) {
		this.vlanTrunk = vlanTrunk;
	}
	/**
	 * @return the pVlanId
	 */
	public int getpVlanId() {
		return pVlanId;
	}
	/**
	 * @param pVlanId the pVlanId to set
	 */
	public void setpVlanId(int pVlanId) {
		this.pVlanId = pVlanId;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the parentFolder
	 */
	public String getParentFolder() {
		return parentFolder;
	}
	/**
	 * @param parentFolder the parentFolder to set
	 */
	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the managementIp
	 */
	public String getManagementIp() {
		return managementIp;
	}
	/**
	 * @param managementIp the managementIp to set
	 */
	public void setManagementIp(String managementIp) {
		this.managementIp = managementIp;
	}
	/**
	 * @return the sslThumbprint
	 */
	public String getSslThumbprint() {
		return sslThumbprint;
	}
	/**
	 * @param sslThumbprint the sslThumbprint to set
	 */
	public void setSslThumbprint(String sslThumbprint) {
		this.sslThumbprint = sslThumbprint;
	}
	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	/**
	 * @return the force
	 */
	public boolean isForce() {
		return force;
	}
	/**
	 * @param force the force to set
	 */
	public void setForce(boolean force) {
		this.force = force;
	}
	/**
	 * @return the asConnected
	 */
	public boolean isAsConnected() {
		return asConnected;
	}
	/**
	 * @param asConnected the asConnected to set
	 */
	public void setAsConnected(boolean asConnected) {
		this.asConnected = asConnected;
	}
	/**
	 * @return the vmFolder
	 */
	public String getVmFolder() {
		return vmFolder;
	}
	/**
	 * @param vmFolder the vmFolder to set
	 */
	public void setVmFolder(String vmFolder) {
		this.vmFolder = vmFolder;
	}
}
