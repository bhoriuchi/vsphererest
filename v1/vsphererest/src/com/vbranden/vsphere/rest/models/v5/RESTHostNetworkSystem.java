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
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.HostDnsConfig;
import com.vmware.vim25.HostIpRouteConfig;
import com.vmware.vim25.HostNetCapabilities;
import com.vmware.vim25.HostNetOffloadCapabilities;
import com.vmware.vim25.HostNetworkConfig;
import com.vmware.vim25.HostNetworkInfo;
import com.vmware.vim25.mo.HostNetworkSystem;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTHostNetworkSystem extends RESTManagedObject {

	private HostNetCapabilities capabilities;
	private HostIpRouteConfig consoleIpRouteConfig;
	private HostDnsConfig dnsConfig;
	private HostIpRouteConfig ipRouteConfig;
	private HostNetworkConfig networkConfig;
	private HostNetworkInfo networkInfo;
	private HostNetOffloadCapabilities offloadCapabilities;


	// constructor
	public RESTHostNetworkSystem() {
	}

	// overloaded constructor
	public RESTHostNetworkSystem(ViConnection viConnection, HostNetworkSystem mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(ViConnection viConnection, HostNetworkSystem mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {

			// specific fields
			if (fg.get("capabilities", fields)) {
				this.setCapabilities(mo.getCapabilities());
			}
			if (fg.get("consoleIpRouteConfig", fields)) {
				this.setConsoleIpRouteConfig(mo.getConsoleIpRouteConfig());
			}
			if (fg.get("dnsConfig", fields)) {
				this.setDnsConfig(mo.getDnsConfig());
			}
			if (fg.get("ipRouteConfig", fields)) {
				this.setIpRouteConfig(mo.getIpRouteConfig());
			}		
			if (fg.get("networkConfig", fields)) {
				this.setNetworkConfig(mo.getNetworkConfig());
			}
			if (fg.get("networkInfo", fields)) {
				this.setNetworkInfo(mo.getNetworkInfo());
			}			
			if (fg.get("offloadCapabilities", fields)) {
				this.setOffloadCapabilities(mo.getOffloadCapabilities());
			}			

			
			// set the extended properties
			this.setManagedObject(viConnection, mo, fields, uri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the capabilities
	 */
	public HostNetCapabilities getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(HostNetCapabilities capabilities) {
		this.capabilities = capabilities;
	}

	/**
	 * @return the consoleIpRouteConfig
	 */
	public HostIpRouteConfig getConsoleIpRouteConfig() {
		return consoleIpRouteConfig;
	}

	/**
	 * @param consoleIpRouteConfig the consoleIpRouteConfig to set
	 */
	public void setConsoleIpRouteConfig(HostIpRouteConfig consoleIpRouteConfig) {
		this.consoleIpRouteConfig = consoleIpRouteConfig;
	}

	/**
	 * @return the dnsConfig
	 */
	public HostDnsConfig getDnsConfig() {
		return dnsConfig;
	}

	/**
	 * @param dnsConfig the dnsConfig to set
	 */
	public void setDnsConfig(HostDnsConfig dnsConfig) {
		this.dnsConfig = dnsConfig;
	}

	/**
	 * @return the ipRouteConfig
	 */
	public HostIpRouteConfig getIpRouteConfig() {
		return ipRouteConfig;
	}

	/**
	 * @param ipRouteConfig the ipRouteConfig to set
	 */
	public void setIpRouteConfig(HostIpRouteConfig ipRouteConfig) {
		this.ipRouteConfig = ipRouteConfig;
	}

	/**
	 * @return the networkConfig
	 */
	public HostNetworkConfig getNetworkConfig() {
		return networkConfig;
	}

	/**
	 * @param networkConfig the networkConfig to set
	 */
	public void setNetworkConfig(HostNetworkConfig networkConfig) {
		this.networkConfig = networkConfig;
	}

	/**
	 * @return the networkInfo
	 */
	public HostNetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	/**
	 * @param networkInfo the networkInfo to set
	 */
	public void setNetworkInfo(HostNetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}

	/**
	 * @return the offloadCapabilities
	 */
	public HostNetOffloadCapabilities getOffloadCapabilities() {
		return offloadCapabilities;
	}

	/**
	 * @param offloadCapabilities the offloadCapabilities to set
	 */
	public void setOffloadCapabilities(
			HostNetOffloadCapabilities offloadCapabilities) {
		this.offloadCapabilities = offloadCapabilities;
	}

}
