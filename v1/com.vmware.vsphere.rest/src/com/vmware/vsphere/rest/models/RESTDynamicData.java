package com.vmware.vsphere.rest.models;

import com.vmware.vim25.DynamicProperty;

public class RESTDynamicData {
	
	private DynamicProperty[] dynamicProperty;
	private String dynamicType;
	/**
	 * @return the dynamicProperty
	 */
	public DynamicProperty[] getDynamicProperty() {
		return dynamicProperty;
	}
	/**
	 * @param dynamicProperty the dynamicProperty to set
	 */
	public void setDynamicProperty(DynamicProperty[] dynamicProperty) {
		this.dynamicProperty = dynamicProperty;
	}
	/**
	 * @return the dynamicType
	 */
	public String getDynamicType() {
		return dynamicType;
	}
	/**
	 * @param dynamicType the dynamicType to set
	 */
	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}


	

}
