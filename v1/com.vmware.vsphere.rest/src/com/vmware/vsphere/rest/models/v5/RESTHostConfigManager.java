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
import com.vmware.vsphere.rest.helpers.FieldGet;
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
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields	
			if (fg.get("advancedOption", fields)) {
				this.setAdvancedOption(new ManagedObjectReferenceUri().getUri(mo.getOptionManager(), uri));
			}
			//fix this
			if (fg.get("authenticationManager", fields)) {
				this.setAuthenticationManager(null);
			}	
			if (fg.get("autoStartManager", fields)) {
				this.setAutoStartManager(new ManagedObjectReferenceUri().getUri(mo.getHostAutoStartManager(), uri));
			}	
			if (fg.get("bootDeviceSystem", fields)) {
				this.setBootDeviceSystem(new ManagedObjectReferenceUri().getUri(mo.getHostBootDeviceSystem(), uri));
			}				
			if (fg.get("cacheConfigurationManager", fields)) {
				this.setCacheConfigurationManager(new ManagedObjectReferenceUri().getUri(mo.getHostCacheConfigurationManager(), uri));
			}			
			if (fg.get("cpuScheduler", fields)) {
				this.setCpuScheduler(new ManagedObjectReferenceUri().getUri(mo.getHostCpuSchedulerSystem(), uri));
			}
			if (fg.get("datastoreSystem", fields)) {
				this.setDatastoreSystem(new ManagedObjectReferenceUri().getUri(mo.getHostDatastoreSystem(), uri));
			}
			if (fg.get("dateTimeSystem", fields)) {
				this.setDateTimeSystem(new ManagedObjectReferenceUri().getUri(mo.getHostDateTimeSystem(), uri));
			}
			if (fg.get("diagnosticSystem", fields)) {
				this.setDiagnosticSystem(new ManagedObjectReferenceUri().getUri(mo.getHostDiagnosticSystem(), uri));
			}
			if (fg.get("esxAgentHostManager", fields)) {
				this.setEsxAgentHostManager(new ManagedObjectReferenceUri().getUri(mo.getHostEsxAgentHostManager(), uri));
			}
			if (fg.get("firewallSystem", fields)) {
				this.setFirewallSystem(new ManagedObjectReferenceUri().getUri(mo.getHostFirewallSystem(), uri));
			}
			if (fg.get("firmwareSystem", fields)) {
				this.setFirmwareSystem(new ManagedObjectReferenceUri().getUri(mo.getHostFirmwareSystem(), uri));
			}
			if (fg.get("healthStatusSystem", fields)) {
				this.setHealthStatusSystem(new ManagedObjectReferenceUri().getUri(mo.getHealthStatusSystem(), uri));
			}
			if (fg.get("imageConfigManager", fields)) {
				this.setImageConfigManager(new ManagedObjectReferenceUri().getUri(mo.getHostImageConfigManager(), uri));
			}
			if (fg.get("iscsiManager", fields)) {
				this.setIscsiManager(new ManagedObjectReferenceUri().getUri(mo.getIscsiManager(), uri));
			}
			if (fg.get("kernelModuleSystem", fields)) {
				this.setKernelModuleSystem(new ManagedObjectReferenceUri().getUri(mo.getHostKernelModuleSystem(), uri));
			}
			if (fg.get("licenseManager", fields)) {
				this.setLicenseManager(new ManagedObjectReferenceUri().getUri(mo.getLicenseManager(), uri));
			}
			if (fg.get("memoryManager", fields)) {
				this.setMemoryManager(new ManagedObjectReferenceUri().getUri(mo.getHostMemorySystem(), uri));
			}					
			if (fg.get("networkSystem", fields)) {
				this.setNetworkSystem(new ManagedObjectReferenceUri().getUri(mo.getHostNetworkSystem(), uri));
			}					
			if (fg.get("patchManager", fields)) {
				this.setPatchManager(new ManagedObjectReferenceUri().getUri(mo.getHostPatchManager(), uri));
			}					
			if (fg.get("pciPassthruSystem", fields)) {
				this.setPciPassthruSystem(new ManagedObjectReferenceUri().getUri(mo.getHostPciPassthruSystem(), uri));
			}
			
			// fix
			if (fg.get("powerSystem", fields)) {
				this.setPowerSystem(null);
			}					
			if (fg.get("serviceSystem", fields)) {
				this.setServiceSystem(new ManagedObjectReferenceUri().getUri(mo.getHostServiceSystem(), uri));
			}					
			if (fg.get("snmpSystem", fields)) {
				this.setSnmpSystem(new ManagedObjectReferenceUri().getUri(mo.getHostSnmpSystem(), uri));
			}					
			if (fg.get("storageSystem", fields)) {
				this.setStorageSystem(new ManagedObjectReferenceUri().getUri(mo.getHostStorageSystem(), uri));
			}					
			if (fg.get("virtualNicManager", fields)) {
				this.setVirtualNicManager(new ManagedObjectReferenceUri().getUri(mo.getHostVirtualNicManager(), uri));
			}					
			if (fg.get("vmotionSystem", fields)) {
				this.setVmotionSystem(null);
			}					

			
			
			
			
			// extended from RESTDynamicData
			if (fg.get("dynamicProperty", fields)) {
				this.setDynamicProperty(null);
			}
			if (fg.get("dynamicType", fields)) {
				this.setDynamicType(null);
			}
			
			
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
