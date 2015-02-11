package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.vim25.ManagedObjectReference;

public class ManagedObjectReferenceUri {

	public ManagedObjectReferenceUri() {
	}

	public String getUri(Object o, String uri) {

		String ref = null;
		ManagedObjectReference mor;

		if (o != null) {

			try {
				
				if (o instanceof ManagedObjectReference) {
					mor = (ManagedObjectReference) o;
				}
				else {
					// get the managed object reference
					mor = (ManagedObjectReference) o.getClass().getMethod("getMOR").invoke(o);
				}
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
	public String getId(String resource) {
		Pattern p = Pattern.compile("^http?://");
		Matcher m = p.matcher(resource);
		
		if (m.find()) {
			return resource.substring(resource.lastIndexOf('/') + 1, resource.length());
		}
		else {
			return resource;
		}
		
	}
}
