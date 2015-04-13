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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTRequestBody {

	private String name;
	private String id;
	private String moRef;
	private String datacenter;
	private String clusterComputeResource;
	private String resourcePool;
	private String parentResourcePool;
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
	private String vimAccountName;
	private String vimAccountPassword;
	
	// vm config spec options
	private String alternativeGuestName;
	private String annotation;
	private String guestId;
	
	private int numPorts = -1;
	private int vlanId = -1;
	private int pVlanId = -1;
	private int port = -1;
	private int memoryMB = 1024;
	private int numCoresPerSocket = 1;
	private int numCPUs = 1;

	private boolean force = false;
	private boolean connected = true;
	
	private Object spec;
	private Object[] specs;	
	private Object computeResourceConfigSpec;
	
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
	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * @param connected the connected to set
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the vimAccountName
	 */
	public String getVimAccountName() {
		return vimAccountName;
	}
	/**
	 * @param vimAccountName the vimAccountName to set
	 */
	public void setVimAccountName(String vimAccountName) {
		this.vimAccountName = vimAccountName;
	}
	/**
	 * @return the vimAccountPassword
	 */
	public String getVimAccountPassword() {
		return vimAccountPassword;
	}
	/**
	 * @param vimAccountPassword the vimAccountPassword to set
	 */
	public void setVimAccountPassword(String vimAccountPassword) {
		this.vimAccountPassword = vimAccountPassword;
	}
	/**
	 * @return the computeResourceConfigSpec
	 */
	public Object getComputeResourceConfigSpec() {
		return computeResourceConfigSpec;
	}
	/**
	 * @param computeResourceConfigSpec the computeResourceConfigSpec to set
	 */
	public void setComputeResourceConfigSpec(Object computeResourceConfigSpec) {
		this.computeResourceConfigSpec = computeResourceConfigSpec;
	}
	/**
	 * @return the parentResourcePool
	 */
	public String getParentResourcePool() {
		return parentResourcePool;
	}
	/**
	 * @param parentResourcePool the parentResourcePool to set
	 */
	public void setParentResourcePool(String parentResourcePool) {
		this.parentResourcePool = parentResourcePool;
	}
	/**
	 * @return the alternativeGuestName
	 */
	public String getAlternativeGuestName() {
		return alternativeGuestName;
	}
	/**
	 * @param alternativeGuestName the alternativeGuestName to set
	 */
	public void setAlternativeGuestName(String alternativeGuestName) {
		this.alternativeGuestName = alternativeGuestName;
	}
	/**
	 * @return the annotation
	 */
	public String getAnnotation() {
		return annotation;
	}
	/**
	 * @param annotation the annotation to set
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	/**
	 * @return the guestId
	 */
	public String getGuestId() {
		return guestId;
	}
	/**
	 * @param guestId the guestId to set
	 */
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	/**
	 * @return the memoryMB
	 */
	public int getMemoryMB() {
		return memoryMB;
	}
	/**
	 * @param memoryMB the memoryMB to set
	 */
	public void setMemoryMB(int memoryMB) {
		this.memoryMB = memoryMB;
	}
	/**
	 * @return the numCoresPerSocket
	 */
	public int getNumCoresPerSocket() {
		return numCoresPerSocket;
	}
	/**
	 * @param numCoresPerSocket the numCoresPerSocket to set
	 */
	public void setNumCoresPerSocket(int numCoresPerSocket) {
		this.numCoresPerSocket = numCoresPerSocket;
	}
	/**
	 * @return the numCPUs
	 */
	public int getNumCPUs() {
		return numCPUs;
	}
	/**
	 * @param numCPUs the numCPUs to set
	 */
	public void setNumCPUs(int numCPUs) {
		this.numCPUs = numCPUs;
	}
}
