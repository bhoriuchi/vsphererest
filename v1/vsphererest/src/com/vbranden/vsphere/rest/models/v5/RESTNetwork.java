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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.NetworkSummary;
import com.vmware.vim25.mo.Network;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTNetwork extends RESTManagedEntity {
	
	private List<String> host;
	private NetworkSummary summary;
	private List<String> vm;
	
	// constructor
	public RESTNetwork() {
	}

	// overloaded constructor
	public RESTNetwork(ViConnection viConnection, Network mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	public void init(ViConnection viConnection, Network mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(mo.getHosts(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(mo.getVms(), uri));
			}

			// set the extended properties
			this.setManagedEntity(viConnection, mo, fields, uri);


		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the host
	 */
	public List<String> getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(List<String> host) {
		this.host = host;
	}

	/**
	 * @return the summary
	 */
	public NetworkSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(NetworkSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the vm
	 */
	public List<String> getVm() {
		return vm;
	}

	/**
	 * @param vm the vm to set
	 */
	public void setVm(List<String> vm) {
		this.vm = vm;
	}

}
