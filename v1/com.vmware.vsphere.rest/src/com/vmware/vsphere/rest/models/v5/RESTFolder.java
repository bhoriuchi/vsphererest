package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTFolder extends RESTManagedEntity {
	
	private List<String> childEntity;
	private String[] childType;
	
	// constructor
	public RESTFolder() {
	}

	// overloaded constructor
	public RESTFolder(Folder mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Folder mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("childEntity", fields)) {
				this.setChildEntity(new ManagedObjectReferenceArray().getMORArray(mo.getChildEntity(), uri));
			}
			if (fg.get("childType", fields)) {
				this.setChildType(mo.getChildType());
			}
			

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);


		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	}
	
	/**
	 * @return the childEntity
	 */
	public List<String> getChildEntity() {
		return childEntity;
	}

	/**
	 * @param childEntity the childEntity to set
	 */
	public void setChildEntity(List<String> childEntity) {
		this.childEntity = childEntity;
	}

	/**
	 * @return the childType
	 */
	public String[] getChildType() {
		return childType;
	}

	/**
	 * @param childType the childType to set
	 */
	public void setChildType(String[] childType) {
		this.childType = childType;
	}



}
