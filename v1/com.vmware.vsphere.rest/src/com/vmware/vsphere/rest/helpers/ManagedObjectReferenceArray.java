package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ManagedObjectReferenceArray {

	public ManagedObjectReferenceArray() {
	}

	public List<String> getMORArray(Object[] objArray, String ref) throws InvocationTargetException, NoSuchMethodException {

		try {

			List<String> objList = new ArrayList<String>();
			ManagedObjectReferenceUri uri = new ManagedObjectReferenceUri();

			if (objArray != null) {
				for (Object o : objArray) {
					
					/*
					objList.add(uri.getUri((ManagedObjectReference) o.getClass()
							.getMethod("getMOR").invoke(o), ref));
							*/
					objList.add(uri.getUri(o, ref));
				}
			}
			return objList;

		} catch (IllegalArgumentException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
