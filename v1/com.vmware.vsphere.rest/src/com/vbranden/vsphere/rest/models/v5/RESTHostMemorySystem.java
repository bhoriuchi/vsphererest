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

import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vmware.vim25.ServiceConsoleReservationInfo;
import com.vmware.vim25.VirtualMachineMemoryReservationInfo;
import com.vmware.vim25.mo.HostMemorySystem;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTHostMemorySystem extends RESTManagedObject {

	private ServiceConsoleReservationInfo consoleReservationInfo;
	private VirtualMachineMemoryReservationInfo virtualMachineReservationInfo;

	// constructor
	public RESTHostMemorySystem() {
	}

	// overloaded constructor
	public RESTHostMemorySystem(HostMemorySystem mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(HostMemorySystem mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {

			// specific fields
			if (fg.get("consoleReservationInfo", fields)) {
				this.setConsoleReservationInfo(mo.getConsoleReservationInfo());
			}
			if (fg.get("virtualMachineReservationInfo", fields)) {
				this.setVirtualMachineReservationInfo(mo.getVirtualMachineReservationInfo());
			}
			
			
			// set the extended properties
			this.setManagedObject(mo, fields, uri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the consoleReservationInfo
	 */
	public ServiceConsoleReservationInfo getConsoleReservationInfo() {
		return consoleReservationInfo;
	}

	/**
	 * @param consoleReservationInfo the consoleReservationInfo to set
	 */
	public void setConsoleReservationInfo(ServiceConsoleReservationInfo consoleReservationInfo) {
		this.consoleReservationInfo = consoleReservationInfo;
	}

	/**
	 * @return the virtualMachineReservationInfo
	 */
	public VirtualMachineMemoryReservationInfo getVirtualMachineReservationInfo() {
		return virtualMachineReservationInfo;
	}

	/**
	 * @param virtualMachineReservationInfo the virtualMachineReservationInfo to set
	 */
	public void setVirtualMachineReservationInfo(
			VirtualMachineMemoryReservationInfo virtualMachineReservationInfo) {
		this.virtualMachineReservationInfo = virtualMachineReservationInfo;
	}


}
