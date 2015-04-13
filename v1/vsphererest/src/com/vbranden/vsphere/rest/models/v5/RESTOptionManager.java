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
import com.vmware.vim25.OptionDef;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.mo.OptionManager;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTOptionManager extends RESTManagedObject {

	private OptionValue[] setting;
	private OptionDef[] supportedOption;

	// constructor
	public RESTOptionManager() {
	}

	// overloaded constructor
	public RESTOptionManager(ViConnection viConnection, OptionManager mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(ViConnection viConnection, OptionManager mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("setting", fields)) {
				this.setSetting(mo.getSetting());
			}
			if (fg.get("supportedOption", fields)) {
				this.setSupportedOption(mo.getSupportedOption());
			}

			// set the extended properties
			this.setManagedObject(viConnection, mo, fields, uri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the setting
	 */
	public OptionValue[] getSetting() {
		return setting;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting(OptionValue[] setting) {
		this.setting = setting;
	}

	/**
	 * @return the supportedOption
	 */
	public OptionDef[] getSupportedOption() {
		return supportedOption;
	}

	/**
	 * @param supportedOption the supportedOption to set
	 */
	public void setSupportedOption(OptionDef[] supportedOption) {
		this.supportedOption = supportedOption;
	}
}
