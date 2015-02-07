package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTManagedObject {

	private String id;
	private String moRef;
	private String resource;

	public void setManagedObject(ManagedObject mo, String fields, String uri) {
		
		FieldGet fg = new FieldGet();

		if (fg.get("id", fields)) {
			this.setId(mo.getMOR().getVal());
		}
		if (fg.get("moRef", fields)) {
			this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
		}
		if (fg.get("resource", fields)) {
			this.setResource(new ManagedObjectReferenceUri().getUri(mo, uri));
		}
	}

	/*
	 * get a specific object of this type by id
	 */
	public Response getById(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fieldStr, String thisUri, String id) {

		try {

			// initialize classes
			Class<?> vim = Class.forName(vimClass);
			Class<?> rest = Class.forName(restClass);
			Object mo = rest.newInstance();
			Object m = null;
			
			// check for managed entities
			if (rest.getSuperclass() == RESTManagedEntity.class) {

				m = new ViConnection().getEntity(vimType, id, headers,
						sessionKey, viServer);
			}

			// check for non managed entities
			else {

				m = new ViConnection().getManagedObject(vimType, id, headers,
						sessionKey, viServer);
			}

			// create the REST object if it exists
			if (m != null) {

				// create parameter/argument array and init the rest class
				Class<?> params[] = { vim, String.class, String.class };
				Object args[] = { vim.cast(m), thisUri, fieldStr };
				Method method = rest.getMethod("init", params);
				method.invoke(mo, args);

				return Response.ok().entity(mo).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(404).build();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the moRef
	 */
	public String getMoRef() {
		return moRef;
	}

	/**
	 * @param moRef
	 *            the moRef to set
	 */
	public void setMoRef(String moRef) {
		this.moRef = moRef;
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

}
