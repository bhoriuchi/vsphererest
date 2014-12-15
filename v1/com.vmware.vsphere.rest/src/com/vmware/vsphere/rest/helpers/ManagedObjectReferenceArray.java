package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.ManagedObjectReference;

public class ManagedObjectReferenceArray {

	public ManagedObjectReferenceArray() {
	}

	public List<ManagedObjectReference> getMORArray(Object[] objArray) {
		try {

			List<ManagedObjectReference> objList = new ArrayList<ManagedObjectReference>();

			for (Object o : objArray) {
				objList.add((ManagedObjectReference) o.getClass()
						.getMethod("getMOR").invoke(o));
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
