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

package com.vbranden.vsphere.rest.helpers;

import java.util.HashMap;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/


public class DefaultValuesHelper {
	
	public HashMap<String,HashMap<String,Object>> d;
	
	public DefaultValuesHelper() { }
	
	public DefaultValuesHelper init() {

		// create a new root HashMap
		this.d = new HashMap<String,HashMap<String,Object>>();
		
		// DVPortgroupConfigSpec
		this.d.put("DVPortgroupConfigSpec", new HashMap<String,Object>());
		this.d.get("DVPortgroupConfigSpec").put("numPorts", 128);
		this.d.get("DVPortgroupConfigSpec").put("type", "earlyBinding");
		
		// VmwareDistributedVirtualSwitchVlanIdSpec
		this.d.put("VmwareDistributedVirtualSwitchVlanIdSpec", new HashMap<String,Object>());
		this.d.get("VmwareDistributedVirtualSwitchVlanIdSpec").put("inherited", false);
		
		// VmwareDistributedVirtualSwitchTrunkVlanSpec
		this.d.put("VmwareDistributedVirtualSwitchTrunkVlanSpec", new HashMap<String,Object>());
		this.d.get("VmwareDistributedVirtualSwitchTrunkVlanSpec").put("inherited", false);
		
		// VmwareDistributedVirtualSwitchPvlanSpec
		this.d.put("VmwareDistributedVirtualSwitchPvlanSpec", new HashMap<String,Object>());
		this.d.get("VmwareDistributedVirtualSwitchPvlanSpec").put("inherited", false);
		
		// HostConnectSpec
		this.d.put("HostConnectSpec", new HashMap<String,Object>());
		this.d.get("HostConnectSpec").put("force", false);
		this.d.get("HostConnectSpec").put("userName", "root");
		
		
		
		return this;
	}
	
	
	public Object set(String type, String key) {
		return this.set(type, key, null);
	}
	
	
	public Object set(String type, String key, Object value) {
		
		try {
			if (this.getObjectType(value) != "null") {
				
				if (this.getObjectType(value) == "int" && Integer.parseInt(value.toString()) == -1) {
					return this.d.get(type).get(key);
				}
				return value;
			}
			else {
				return this.d.get(type).get(key);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	private String getObjectType(Object o) {
		
		
		if (o == null) {
			return "null";
		}
		else {
			
			// convert the object to a string
			String oStr = o.toString();
			
			if (Boolean.parseBoolean(oStr)) {
				return "boolean";
			}
			else if (isInteger(oStr)) {
				return "int";
			}
			else {
				return "string";
			}
			
		}
		
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	

}
