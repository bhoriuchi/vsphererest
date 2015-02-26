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

import com.vmware.vim25.HostAuthenticationManagerInfo;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.helpers.mo;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTHostAuthenticationManager extends RESTManagedObject {
	
	private HostAuthenticationManagerInfo info;
	private String supportedStore;
	
	// constructor
	public RESTHostAuthenticationManager() {
	}

	// overloaded constructor
	public RESTHostAuthenticationManager(ManagedObjectReference mor, String uri, String fields, ViConnection v) {
		this.init(mor, uri, fields, v);
	}

	public void init(ManagedObjectReference mor, String uri, String fields, ViConnection v) {

		try {
			

			// attempt to get the host system
			HostSystem mo = (HostSystem) v.getEntity("HostSystem", mor.getVal().replace("authenticationManager", "host"));

			if (mo != null) {
				this.setInfo(mo.getConfig().getAuthenticationManagerInfo());
				this.setSupportedStore(null);

				// set the extended properties
				this.setManagedObject(new mo(mor, v.getSc()), fields, uri);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the info
	 */
	public HostAuthenticationManagerInfo getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(HostAuthenticationManagerInfo info) {
		this.info = info;
	}

	/**
	 * @return the supportedStore
	 */
	public String getSupportedStore() {
		return supportedStore;
	}

	/**
	 * @param supportedStore the supportedStore to set
	 */
	public void setSupportedStore(String supportedStore) {
		this.supportedStore = supportedStore;
	}


	
	


}
