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

package com.vmware.vsphere.rest.helpers;

import java.util.ArrayList;

import com.vmware.vsphere.rest.models.v5.RESTCustomResponse;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

// helps to eliminate overly nested if statements and adds error logging
public class ConditionHelper {
	
	private Object obj;
	private boolean failed = false;
	private RESTCustomResponse response;
	
	public ConditionHelper() {
		this.setResponse(new RESTCustomResponse("", new ArrayList<String>()));
	}
	
	// eliminates need for multiple nested if statements
	public ConditionHelper checkCondition(boolean condition, String failMessage) {
		
		// if the object has not failed yet, set the failed field to the current condition
		if (!this.isFailed()) {
			this.setFailed(!condition);
		}
		
		// if the condition fails then always add the fail message
		if (!condition) {
			System.out.println(failMessage);
			this.getResponse().setResponseStatus("failed");
			this.getResponse().getResponseMessage().add(failMessage);
		}
		
		return this;
	}
	
	// attempts to get an object
	public ConditionHelper getEntity(boolean condition, String type, String id, ViConnection v) {
		
		if (condition) {
			
			// search for the entity
			Object o = v.getEntity(type, id);

			// if the entity is not null set it
			if (o != null) {
				this.setObj(o);
			}
			else {
				this.setFailed(true);
				this.getResponse().setResponseStatus("failed");
				this.getResponse().getResponseMessage().add("Failed to find " + type);
			}
		}
		
		return this;
	}
	
	
	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * @return the failed
	 */
	public boolean isFailed() {
		return failed;
	}

	/**
	 * @param failed the failed to set
	 */
	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	/**
	 * @return the response
	 */
	public RESTCustomResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(RESTCustomResponse response) {
		this.response = response;
	}
	
	
}
