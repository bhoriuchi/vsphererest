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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.ConditionHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
				this.setChildEntity(new ManagedObjectReferenceArray()
						.getMORArray(mo.getChildEntity(), uri));
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

	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, RESTRequestBody body) {

		// initialize classes
		ConditionHelper ch = new ConditionHelper();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Folder f = null;
		Folder nf = null;

		// check the body
		if (ch.checkCondition((body != null),
				"No message body was specified in the request").isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		}

		// attempt to create
		try {

			// check fields and create datacenter
			if (!ch.checkCondition((body.getName() != null), "Name not specified").isFailed()) {
				
				// get the parent folder
				if (body.getParentFolder() != null && !ch.getEntity(!ch.isFailed(), "Folder", body.getParentFolder(), v).isFailed()) {
					f = (Folder) ch.getObj();
				}
				else if (body.getDatacenter() != null && !ch.getEntity(!ch.isFailed(), "Datacenter", body.getDatacenter(), v).isFailed()) {
					
					Datacenter dc = (Datacenter) ch.getObj();
					
					if (body.getType().toLowerCase().equals("hostsystem")) {
						f = dc.getHostFolder();
					} else if (body.getType().toLowerCase().equals("datastore")) {
						f = dc.getDatastoreFolder();
					} else if (body.getType().toLowerCase().equals("network")) {
						f = dc.getNetworkFolder();
					} else if (body.getType().toLowerCase().equals("virtualmachine")) {
						f = dc.getVmFolder();
					}
				}
				
				// if the parent folder exists then try to create the sub-folder
				if (!ch.checkCondition((f != null), "parent folder not found").isFailed()) {
					nf = f.createFolder(body.getName());
				}
			}
			
			// check that the datacenter was created
			ch.checkCondition((nf != null), "Failed to create Folder");

		} catch (InvalidProperty e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Invalid property");
		} catch (Exception e) {
			ch.setFailed(true);
			ch.getResponse().setResponseStatus("failed");
			ch.getResponse().getResponseMessage().add("Unknown Error");
		}

		// check if the request failed
		if (ch.isFailed()) {
			return Response.status(400).entity(ch.getResponse()).build();
		} else {
			try {
				return Response.created(new URI(moUri.getUri(nf, thisUri)))
						.entity(new RESTFolder(nf, thisUri, fields))
						.build();
			} catch (URISyntaxException e) {
				ch.setFailed(true);
				ch.getResponse().setResponseStatus("failed");
				ch.getResponse().getResponseMessage()
						.add("Invalid URI created");
			}
		}

		return null;
	}

	/**
	 * @return the childEntity
	 */
	public List<String> getChildEntity() {
		return childEntity;
	}

	/**
	 * @param childEntity
	 *            the childEntity to set
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
	 * @param childType
	 *            the childType to set
	 */
	public void setChildType(String[] childType) {
		this.childType = childType;
	}

}
