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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTVirtualMachineSnapshot extends RESTExtensibleManagedObject {
	
	private List<String> childSnapshot;
	private VirtualMachineConfigInfo config;
	
	// constructor
	public RESTVirtualMachineSnapshot() {
	}

	// overloaded constructor
	public RESTVirtualMachineSnapshot(VirtualMachineSnapshot mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(VirtualMachineSnapshot mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("childSnapshot", fields)) {
				try {
					this.setChildSnapshot(new ManagedObjectReferenceArray().getMORArray(mo.getChildSnapshot(), uri));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					this.setChildSnapshot(new ManagedObjectReferenceArray().getMORArray(null, uri));
				}
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			
			/*
			 * There seems to be an issue with the VirtualMachineSnapshot object not inheriting its superclasses
			 */
			
			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
			}
			if (fg.get("resource", fields)) {
				this.setResource(new ManagedObjectReferenceUri().getUri(
						mo, uri));
			}
			
			// extended from RESTExtensibleManagedObject
			if (fg.get("availableField", fields)) {
				this.setAvailableField(null);
			}		
			if (fg.get("value", fields)) {
				this.setValue(null);
			}			
			

		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the childSnapshot
	 */
	public List<String> getChildSnapshot() {
		return childSnapshot;
	}

	/**
	 * @param childSnapshot the childSnapshot to set
	 */
	public void setChildSnapshot(List<String> childSnapshot) {
		this.childSnapshot = childSnapshot;
	}

	/**
	 * @return the config
	 */
	public VirtualMachineConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(VirtualMachineConfigInfo config) {
		this.config = config;
	}


}
