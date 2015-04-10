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

import java.rmi.RemoteException;

import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vmware.vim25.CustomFieldDef;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ExtensibleManagedObject;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
