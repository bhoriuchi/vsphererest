package com.vmware.vsphere.rest.models.v5;

import java.rmi.RemoteException;

import com.vmware.vim25.CustomFieldDef;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ExtensibleManagedObject;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTExtensibleManagedObject extends RESTManagedObject {

	private CustomFieldDef[] availableField;
	private CustomFieldValue[] value;

	public void setExtensibleManagedObject(ExtensibleManagedObject mo,
			String fields, String uri) {

		FieldGet fg = new FieldGet();

		try {
			if (fg.get("availableField", fields)) {
				this.setAvailableField(mo.getAvailableField());
			}
			if (fg.get("value", fields)) {
				this.setValue(mo.getValues());
			}
		} catch (InvalidProperty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setManagedObject(mo, fields, uri);
	}

	/**
	 * @return the availableField
	 */
	public CustomFieldDef[] getAvailableField() {
		return availableField;
	}

	/**
	 * @param availableField
	 *            the availableField to set
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
	 * @param value
	 *            the value to set
	 */
	public void setValue(CustomFieldValue[] value) {
		this.value = value;
	}

}
