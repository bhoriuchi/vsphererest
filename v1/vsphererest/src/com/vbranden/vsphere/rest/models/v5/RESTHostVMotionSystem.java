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
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vbranden.vsphere.rest.helpers.mo;
import com.vmware.vim25.HostIpConfig;
import com.vmware.vim25.HostVMotionNetConfig;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.HostSystem;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTHostVMotionSystem extends RESTManagedObject {
	
	private HostIpConfig ipConfig;
	private HostVMotionNetConfig netConfig;
	
	// constructor
	public RESTHostVMotionSystem() {
	}

	// overloaded constructor
	public RESTHostVMotionSystem(ViConnection viConnection, ManagedObjectReference mor, String uri, String fields, ViConnection v) {
		this.init(viConnection, mor, uri, fields, v);
	}

	public void init(ViConnection viConnection, ManagedObjectReference mor, String uri, String fields, ViConnection v) {

		try {
			

			// attempt to get the host system
			HostSystem mo = (HostSystem) v.getEntity("HostSystem", mor.getVal().replace("vmotionSystem", "host"));

			if (mo != null) {
				this.setIpConfig(mo.getConfig().getVmotion().getIpConfig());
				this.setNetConfig(mo.getConfig().getVmotion().getNetConfig());

				// set the extended properties
				this.setManagedObject(viConnection, new mo(mor, v.getSc()), fields, uri);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the ipConfig
	 */
	public HostIpConfig getIpConfig() {
		return ipConfig;
	}

	/**
	 * @param ipConfig the ipConfig to set
	 */
	public void setIpConfig(HostIpConfig ipConfig) {
		this.ipConfig = ipConfig;
	}

	/**
	 * @return the netConfig
	 */
	public HostVMotionNetConfig getNetConfig() {
		return netConfig;
	}

	/**
	 * @param netConfig the netConfig to set
	 */
	public void setNetConfig(HostVMotionNetConfig netConfig) {
		this.netConfig = netConfig;
	}


	
	
	


}
