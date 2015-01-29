package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;

import com.vmware.vim25.ManagedObjectReference;

public class ManagedObjectReferenceUri {

	public ManagedObjectReferenceUri() {
	}

	public String getUri(Object o, String uri) {

		String ref = null;

		if (o != null) {

			try {
				
				// get the managed object reference
				ManagedObjectReference mor = (ManagedObjectReference) o.getClass().getMethod("getMOR").invoke(o);
				
				// construct the reference URI. Add an s to the object type
				// to make the REST call plural per best practice
				if (mor != null) {
					ref = uri + mor.getType().toLowerCase() + "s/" + mor.getVal();
				}
				
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ref;
	}
}
