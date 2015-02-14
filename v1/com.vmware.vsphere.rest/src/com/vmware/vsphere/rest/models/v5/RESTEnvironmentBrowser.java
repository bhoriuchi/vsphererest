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

import com.vmware.vim25.mo.EnvironmentBrowser;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTEnvironmentBrowser extends RESTManagedObject {
	
	private String datastoreBrowser;
	
	// constructor
	public RESTEnvironmentBrowser() {
	}

	// overloaded constructor
	public RESTEnvironmentBrowser(EnvironmentBrowser mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}
	
	public void init(EnvironmentBrowser mo, String uri, String fields)  {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		// EnvironmentBrowser specific fields	
		if (fg.get("datastoreBrowser", fields)) {
			this.setDatastoreBrowser(new ManagedObjectReferenceUri().getUri(mo.getDatastoreBrowser(), uri));
		}		


		// set the extended properties
		this.setManagedObject(mo, fields, uri);
	}

	/**
	 * @return the datastoreBrowser
	 */
	public String getDatastoreBrowser() {
		return datastoreBrowser;
	}

	/**
	 * @param datastoreBrowser the datastoreBrowser to set
	 */
	public void setDatastoreBrowser(String datastoreBrowser) {
		this.datastoreBrowser = datastoreBrowser;
	}



}
