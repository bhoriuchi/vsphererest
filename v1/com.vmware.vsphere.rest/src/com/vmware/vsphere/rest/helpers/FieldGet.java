package com.vmware.vsphere.rest.helpers;

public class FieldGet {

	public FieldGet() { }
	
	public Boolean get(String fieldName, String fields) {
		
		for (String field : fields.split(",")) {
			String[] rootField = field.split("\\.");
			
			if (rootField[0].equals(fieldName) || rootField[0].equals("all")) {
				return true;
			}
		}
		return false;
	}
}
