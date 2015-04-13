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
import com.vmware.vim25.HostFileSystemVolumeInfo;
import com.vmware.vim25.HostMultipathStateInfo;
import com.vmware.vim25.HostStorageDeviceInfo;
import com.vmware.vim25.mo.HostStorageSystem;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTHostStorageSystem extends RESTManagedObject {

	private HostFileSystemVolumeInfo fileSystemVolumeInfo;
	private HostMultipathStateInfo multipathStateInfo;
	private HostStorageDeviceInfo storageDeviceInfo;
	private String[] systemFile;

	// constructor
	public RESTHostStorageSystem() {
	}

	// overloaded constructor
	public RESTHostStorageSystem(ViConnection viConnection, HostStorageSystem mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(ViConnection viConnection, HostStorageSystem mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("fileSystemVolumeInfo", fields)) {
				this.setFileSystemVolumeInfo(mo.getFileSystemVolumeInfo());
			}
			if (fg.get("multipathStateInfo", fields)) {
				this.setMultipathStateInfo(mo.getMultipathStateInfo());
			}
			if (fg.get("storageDeviceInfo", fields)) {
				this.setStorageDeviceInfo(mo.getStorageDeviceInfo());
			}			
			if (fg.get("systemFile", fields)) {
				this.setSystemFile(mo.getSystemFile());
			}	
			
			// set the extended properties
			this.setManagedObject(viConnection, mo, fields, uri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the fileSystemVolumeInfo
	 */
	public HostFileSystemVolumeInfo getFileSystemVolumeInfo() {
		return fileSystemVolumeInfo;
	}

	/**
	 * @param fileSystemVolumeInfo the fileSystemVolumeInfo to set
	 */
	public void setFileSystemVolumeInfo(
			HostFileSystemVolumeInfo fileSystemVolumeInfo) {
		this.fileSystemVolumeInfo = fileSystemVolumeInfo;
	}

	/**
	 * @return the multipathStateInfo
	 */
	public HostMultipathStateInfo getMultipathStateInfo() {
		return multipathStateInfo;
	}

	/**
	 * @param multipathStateInfo the multipathStateInfo to set
	 */
	public void setMultipathStateInfo(HostMultipathStateInfo multipathStateInfo) {
		this.multipathStateInfo = multipathStateInfo;
	}

	/**
	 * @return the storageDeviceInfo
	 */
	public HostStorageDeviceInfo getStorageDeviceInfo() {
		return storageDeviceInfo;
	}

	/**
	 * @param storageDeviceInfo the storageDeviceInfo to set
	 */
	public void setStorageDeviceInfo(HostStorageDeviceInfo storageDeviceInfo) {
		this.storageDeviceInfo = storageDeviceInfo;
	}

	/**
	 * @return the systemFile
	 */
	public String[] getSystemFile() {
		return systemFile;
	}

	/**
	 * @param systemFile the systemFile to set
	 */
	public void setSystemFile(String[] systemFile) {
		this.systemFile = systemFile;
	}


}
