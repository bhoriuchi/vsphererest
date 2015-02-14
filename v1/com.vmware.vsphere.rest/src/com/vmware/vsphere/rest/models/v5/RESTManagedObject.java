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

package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
			if (RESTManagedEntity.class.isAssignableFrom(rest)) {

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
