package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.ManagedObjectReference;

public class ManagedObjectReferenceArray {

	public ManagedObjectReferenceArray() {
	}

	public List<String> getMORArray(Object[] objArray, String ref) {

		try {

			List<String> objList = new ArrayList<String>();
			ManagedObjectReferenceUri uri = new ManagedObjectReferenceUri();

			if (objArray != null) {
				for (Object o : objArray) {
					objList.add(uri.getUri((ManagedObjectReference) o.getClass()
							.getMethod("getMOR").invoke(o), ref));
				}
			}
			return objList;

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
