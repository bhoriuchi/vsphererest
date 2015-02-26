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


import java.rmi.RemoteException;

import com.vmware.vim25.mo.HostSystem;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTHostConfigManager extends RESTDynamicData {
	
	private String advancedOption;
	private String authenticationManager;
	private String autoStartManager;
	private String bootDeviceSystem;
	private String cacheConfigurationManager;
	private String cpuScheduler;
	private String datastoreSystem;
	private String dateTimeSystem;
	private String diagnosticSystem;
	private String esxAgentHostManager;
	private String firewallSystem;
	private String firmwareSystem;
	private String healthStatusSystem;
	private String imageConfigManager;
	private String iscsiManager;
	private String kernelModuleSystem;
	private String licenseManager;
	private String memoryManager;
	private String networkSystem;
	private String patchManager;
	private String pciPassthruSystem;
	private String powerSystem;
	private String serviceSystem;
	private String snmpSystem;
	private String storageSystem;
	private String virtualNicManager;
	private String vmotionSystem;
	
	
	// constructor
	public RESTHostConfigManager() {
	}

	// overloaded constructor
	public RESTHostConfigManager(HostSystem mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}
	
	public void init(HostSystem mo, String uri, String fields) {
		
		try {
			
			ManagedObjectReferenceUri m = new ManagedObjectReferenceUri();
			

			// get reference URIs for each manager
			this.setAdvancedOption(m.getUri(mo.getOptionManager(), uri));
			this.setAuthenticationManager(uri + "hostauthenticationmanagers/" + mo.getMOR().getVal().replace("host", "authenticationManager"));
			this.setAutoStartManager(m.getUri(mo.getHostAutoStartManager(), uri));
			this.setBootDeviceSystem(m.getUri(mo.getHostBootDeviceSystem(), uri));
			this.setCacheConfigurationManager(m.getUri(mo.getHostCacheConfigurationManager(), uri));
			this.setCpuScheduler(m.getUri(mo.getHostCpuSchedulerSystem(), uri));
			this.setDatastoreSystem(m.getUri(mo.getHostDatastoreSystem(), uri));
			this.setDateTimeSystem(m.getUri(mo.getHostDateTimeSystem(), uri));
			this.setDiagnosticSystem(m.getUri(mo.getHostDiagnosticSystem(), uri));
			this.setEsxAgentHostManager(m.getUri(mo.getHostEsxAgentHostManager(), uri));
			this.setFirewallSystem(m.getUri(mo.getHostFirewallSystem(), uri));
			this.setFirmwareSystem(m.getUri(mo.getHostFirmwareSystem(), uri));
			this.setHealthStatusSystem(m.getUri(mo.getHealthStatusSystem(), uri));
			this.setImageConfigManager(m.getUri(mo.getHostImageConfigManager(), uri));
			this.setIscsiManager(m.getUri(mo.getIscsiManager(), uri));
			this.setKernelModuleSystem(m.getUri(mo.getHostKernelModuleSystem(), uri));
			this.setLicenseManager(m.getUri(mo.getLicenseManager(), uri));
			this.setMemoryManager(m.getUri(mo.getHostMemorySystem(), uri));
			this.setNetworkSystem(m.getUri(mo.getHostNetworkSystem(), uri));
			this.setPatchManager(m.getUri(mo.getHostPatchManager(), uri));
			this.setPciPassthruSystem(m.getUri(mo.getHostPciPassthruSystem(), uri));
			this.setPowerSystem(uri + "hostpowersystems/" + mo.getMOR().getVal().replace("host", "powerSystem"));
			this.setServiceSystem(m.getUri(mo.getHostServiceSystem(), uri));
			this.setSnmpSystem(m.getUri(mo.getHostSnmpSystem(), uri));
			this.setStorageSystem(m.getUri(mo.getHostStorageSystem(), uri));
			this.setVirtualNicManager(m.getUri(mo.getHostVirtualNicManager(), uri));
			this.setVmotionSystem(uri + "hostvmotionsystems/" + mo.getMOR().getVal().replace("host", "vmotionSystem"));

			// extended from RESTDynamicData
			this.setDynamicProperty(null);
			this.setDynamicType(null);
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the advancedOption
	 */
	public String getAdvancedOption() {
		return advancedOption;
	}

	/**
	 * @param advancedOption the advancedOption to set
	 */
	public void setAdvancedOption(String advancedOption) {
		this.advancedOption = advancedOption;
	}

	/**
	 * @return the authenticationManager
	 */
	public String getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(String authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @return the autoStartManager
	 */
	public String getAutoStartManager() {
		return autoStartManager;
	}

	/**
	 * @param autoStartManager the autoStartManager to set
	 */
	public void setAutoStartManager(String autoStartManager) {
		this.autoStartManager = autoStartManager;
	}

	/**
	 * @return the bootDeviceSystem
	 */
	public String getBootDeviceSystem() {
		return bootDeviceSystem;
	}

	/**
	 * @param bootDeviceSystem the bootDeviceSystem to set
	 */
	public void setBootDeviceSystem(String bootDeviceSystem) {
		this.bootDeviceSystem = bootDeviceSystem;
	}

	/**
	 * @return the cacheConfigurationManager
	 */
	public String getCacheConfigurationManager() {
		return cacheConfigurationManager;
	}

	/**
	 * @param cacheConfigurationManager the cacheConfigurationManager to set
	 */
	public void setCacheConfigurationManager(String cacheConfigurationManager) {
		this.cacheConfigurationManager = cacheConfigurationManager;
	}

	/**
	 * @return the cpuScheduler
	 */
	public String getCpuScheduler() {
		return cpuScheduler;
	}

	/**
	 * @param cpuScheduler the cpuScheduler to set
	 */
	public void setCpuScheduler(String cpuScheduler) {
		this.cpuScheduler = cpuScheduler;
	}

	/**
	 * @return the datastoreSystem
	 */
	public String getDatastoreSystem() {
		return datastoreSystem;
	}

	/**
	 * @param datastoreSystem the datastoreSystem to set
	 */
	public void setDatastoreSystem(String datastoreSystem) {
		this.datastoreSystem = datastoreSystem;
	}

	/**
	 * @return the dateTimeSystem
	 */
	public String getDateTimeSystem() {
		return dateTimeSystem;
	}

	/**
	 * @param dateTimeSystem the dateTimeSystem to set
	 */
	public void setDateTimeSystem(String dateTimeSystem) {
		this.dateTimeSystem = dateTimeSystem;
	}

	/**
	 * @return the diagnosticSystem
	 */
	public String getDiagnosticSystem() {
		return diagnosticSystem;
	}

	/**
	 * @param diagnosticSystem the diagnosticSystem to set
	 */
	public void setDiagnosticSystem(String diagnosticSystem) {
		this.diagnosticSystem = diagnosticSystem;
	}

	/**
	 * @return the esxAgentHostManager
	 */
	public String getEsxAgentHostManager() {
		return esxAgentHostManager;
	}

	/**
	 * @param esxAgentHostManager the esxAgentHostManager to set
	 */
	public void setEsxAgentHostManager(String esxAgentHostManager) {
		this.esxAgentHostManager = esxAgentHostManager;
	}

	/**
	 * @return the firewallSystem
	 */
	public String getFirewallSystem() {
		return firewallSystem;
	}

	/**
	 * @param firewallSystem the firewallSystem to set
	 */
	public void setFirewallSystem(String firewallSystem) {
		this.firewallSystem = firewallSystem;
	}

	/**
	 * @return the firmwareSystem
	 */
	public String getFirmwareSystem() {
		return firmwareSystem;
	}

	/**
	 * @param firmwareSystem the firmwareSystem to set
	 */
	public void setFirmwareSystem(String firmwareSystem) {
		this.firmwareSystem = firmwareSystem;
	}

	/**
	 * @return the healthStatusSystem
	 */
	public String getHealthStatusSystem() {
		return healthStatusSystem;
	}

	/**
	 * @param healthStatusSystem the healthStatusSystem to set
	 */
	public void setHealthStatusSystem(String healthStatusSystem) {
		this.healthStatusSystem = healthStatusSystem;
	}

	/**
	 * @return the imageConfigManager
	 */
	public String getImageConfigManager() {
		return imageConfigManager;
	}

	/**
	 * @param imageConfigManager the imageConfigManager to set
	 */
	public void setImageConfigManager(String imageConfigManager) {
		this.imageConfigManager = imageConfigManager;
	}

	/**
	 * @return the iscsiManager
	 */
	public String getIscsiManager() {
		return iscsiManager;
	}

	/**
	 * @param iscsiManager the iscsiManager to set
	 */
	public void setIscsiManager(String iscsiManager) {
		this.iscsiManager = iscsiManager;
	}

	/**
	 * @return the kernelModuleSystem
	 */
	public String getKernelModuleSystem() {
		return kernelModuleSystem;
	}

	/**
	 * @param kernelModuleSystem the kernelModuleSystem to set
	 */
	public void setKernelModuleSystem(String kernelModuleSystem) {
		this.kernelModuleSystem = kernelModuleSystem;
	}

	/**
	 * @return the licenseManager
	 */
	public String getLicenseManager() {
		return licenseManager;
	}

	/**
	 * @param licenseManager the licenseManager to set
	 */
	public void setLicenseManager(String licenseManager) {
		this.licenseManager = licenseManager;
	}

	/**
	 * @return the memoryManager
	 */
	public String getMemoryManager() {
		return memoryManager;
	}

	/**
	 * @param memoryManager the memoryManager to set
	 */
	public void setMemoryManager(String memoryManager) {
		this.memoryManager = memoryManager;
	}

	/**
	 * @return the networkSystem
	 */
	public String getNetworkSystem() {
		return networkSystem;
	}

	/**
	 * @param networkSystem the networkSystem to set
	 */
	public void setNetworkSystem(String networkSystem) {
		this.networkSystem = networkSystem;
	}

	/**
	 * @return the patchManager
	 */
	public String getPatchManager() {
		return patchManager;
	}

	/**
	 * @param patchManager the patchManager to set
	 */
	public void setPatchManager(String patchManager) {
		this.patchManager = patchManager;
	}

	/**
	 * @return the pciPassthruSystem
	 */
	public String getPciPassthruSystem() {
		return pciPassthruSystem;
	}

	/**
	 * @param pciPassthruSystem the pciPassthruSystem to set
	 */
	public void setPciPassthruSystem(String pciPassthruSystem) {
		this.pciPassthruSystem = pciPassthruSystem;
	}

	/**
	 * @return the powerSystem
	 */
	public String getPowerSystem() {
		return powerSystem;
	}

	/**
	 * @param powerSystem the powerSystem to set
	 */
	public void setPowerSystem(String powerSystem) {
		this.powerSystem = powerSystem;
	}

	/**
	 * @return the serviceSystem
	 */
	public String getServiceSystem() {
		return serviceSystem;
	}

	/**
	 * @param serviceSystem the serviceSystem to set
	 */
	public void setServiceSystem(String serviceSystem) {
		this.serviceSystem = serviceSystem;
	}

	/**
	 * @return the snmpSystem
	 */
	public String getSnmpSystem() {
		return snmpSystem;
	}

	/**
	 * @param snmpSystem the snmpSystem to set
	 */
	public void setSnmpSystem(String snmpSystem) {
		this.snmpSystem = snmpSystem;
	}

	/**
	 * @return the storageSystem
	 */
	public String getStorageSystem() {
		return storageSystem;
	}

	/**
	 * @param storageSystem the storageSystem to set
	 */
	public void setStorageSystem(String storageSystem) {
		this.storageSystem = storageSystem;
	}

	/**
	 * @return the virtualNicManager
	 */
	public String getVirtualNicManager() {
		return virtualNicManager;
	}

	/**
	 * @param virtualNicManager the virtualNicManager to set
	 */
	public void setVirtualNicManager(String virtualNicManager) {
		this.virtualNicManager = virtualNicManager;
	}

	/**
	 * @return the vmotionSystem
	 */
	public String getVmotionSystem() {
		return vmotionSystem;
	}

	/**
	 * @param vmotionSystem the vmotionSystem to set
	 */
	public void setVmotionSystem(String vmotionSystem) {
		this.vmotionSystem = vmotionSystem;
	}

}
