package com.vmware.vsphere.rest.helpers;

import java.util.HashMap;



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
