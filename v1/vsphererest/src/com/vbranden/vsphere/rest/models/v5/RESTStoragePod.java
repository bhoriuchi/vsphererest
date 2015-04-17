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
import java.rmi.RemoteException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.PodStorageDrsEntry;
import com.vmware.vim25.StoragePodSummary;
import com.vmware.vim25.mo.StoragePod;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTStoragePod extends RESTFolder {

	
	private PodStorageDrsEntry podStorageDrsEntry;
	private StoragePodSummary summary;
	
	
	public RESTStoragePod() {}
	
	public RESTStoragePod(ViConnection viConnection, StoragePod mo, String uri,
			String fields) {
		this.init(viConnection, mo, uri, fields);
	}
	
	public void init(ViConnection viConnection, StoragePod mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			
			if (fg.get("podStorageDrsEntry", fields)) {
				this.setPodStorageDrsEntry(mo.getPodStorageDrsEntry());
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}			
			if (fg.get("childEntity", fields)) {
				this.setChildEntity(new ManagedObjectReferenceArray()
						.getMORArray(mo.getChildEntity(), uri));
			}
			if (fg.get("childType", fields)) {
				this.setChildType(mo.getChildType());
			}

			// set the extended properties
			this.setManagedEntity(viConnection, mo, fields, uri);
	

		} catch (InvocationTargetException | NoSuchMethodException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * @return the podStorageDrsEntry
	 */
	public PodStorageDrsEntry getPodStorageDrsEntry() {
		return podStorageDrsEntry;
	}
	/**
	 * @param podStorageDrsEntry the podStorageDrsEntry to set
	 */
	public void setPodStorageDrsEntry(PodStorageDrsEntry podStorageDrsEntry) {
		this.podStorageDrsEntry = podStorageDrsEntry;
	}
	/**
	 * @return the summary
	 */
	public StoragePodSummary getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(StoragePodSummary summary) {
		this.summary = summary;
	}
	
	
	
}
