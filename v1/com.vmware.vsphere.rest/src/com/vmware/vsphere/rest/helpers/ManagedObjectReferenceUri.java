package com.vmware.vsphere.rest.helpers;

import com.vmware.vim25.ManagedObjectReference;

public class ManagedObjectReferenceUri {

	public ManagedObjectReferenceUri() {
	}

	public String getUri(ManagedObjectReference mor, String uri) {

		String ref = null;

		if (mor != null) {
			
			// construct the reference URI. Add an s to the object type
			// to make the REST call plural per best practice
			ref = uri + mor.getType().toLowerCase() + "s/" + mor.getVal();
		}

		return ref;
	}
}
