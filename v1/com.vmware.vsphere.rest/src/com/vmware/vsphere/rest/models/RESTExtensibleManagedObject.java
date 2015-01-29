package com.vmware.vsphere.rest.models;

import com.vmware.vim25.CustomFieldDef;
import com.vmware.vim25.CustomFieldValue;

public class RESTExtensibleManagedObject extends RESTManagedObject {

	private CustomFieldDef[] availableField;
	private CustomFieldValue[] value;
	
	/**
	 * @return the availableField
	 */
	public CustomFieldDef[] getAvailableField() {
		return availableField;
	}
	/**
	 * @param availableField the availableField to set
	 */
	public void setAvailableField(CustomFieldDef[] availableField) {
		this.availableField = availableField;
	}
	/**
	 * @return the value
	 */
	public CustomFieldValue[] getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(CustomFieldValue[] value) {
		this.value = value;
	}
	
}
