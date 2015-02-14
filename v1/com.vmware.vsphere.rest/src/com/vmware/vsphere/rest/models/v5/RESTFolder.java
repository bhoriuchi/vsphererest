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
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

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

		// initialize a custom response
		RESTCustomResponse cr = new RESTCustomResponse("",
				new ArrayList<String>());
		
		if (body == null) {
			
			cr.setResponseStatus("failed");
			cr.getResponseMessage().add("No message body was specified in the request");
			
			return Response.status(400).entity(cr).build();
		}
		
		// instantiate helpers
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();

		// create a new service instance
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		ServiceInstance si = v.getServiceInstance();
		Folder f = null;

		try {

			// check if a specific folder was specified
			if (body.getName() == null) {
				
				cr.setResponseStatus("failed");
				cr.getResponseMessage().add("Missing name parameter");
				
				return Response
						.status(400)
						.entity(cr).build();
			} else if (body.getParentFolder() != null) {
				f = (Folder) v.getEntity("Folder", body.getParentFolder());
			}
			// otherwise a folder type should be specified
			else if (body.getType() != null) {

				// a datacenter folder gets added at the root
				if (body.getType().toLowerCase().equals("datacenter")) {
					f = si.getRootFolder();
				}

				// every other folder gets added
				else if (body.getDatacenter() != null) {

					Datacenter dc = (Datacenter) v.getEntity("Datacenter",
							body.getDatacenter());

					if (dc == null) {
						cr.setResponseStatus("failed");
						cr.getResponseMessage().add("Datacenter could not be found");
						
						return Response
								.status(400)
								.entity(cr)
								.build();	
					}
					else if (body.getType().toLowerCase().equals("hostsystem")) {
						f = dc.getHostFolder();
					} else if (body.getType().toLowerCase().equals("datastore")) {
						f = dc.getDatastoreFolder();
					} else if (body.getType().toLowerCase().equals("network")) {
						f = dc.getNetworkFolder();
					} else if (body.getType().toLowerCase().equals("virtualmachine")) {
						f = dc.getVmFolder();
					} else {
						
						cr.setResponseStatus("failed");
						cr.getResponseMessage().add("Invalid folder type. Current types are Datacenter, HostSystem, Datastore, Network, and VirtualMachine");
						
						return Response
								.status(400)
								.entity(cr)
								.build();
					}
				}
			}

			if (f == null) {
				
				cr.setResponseStatus("failed");
				cr.getResponseMessage().add("A folder that matches the request could not be found");
				
				return Response
						.status(400)
						.entity(cr)
						.build();
			} else {

				Folder newFolder = f.createFolder(body.getName());
				
				if (newFolder != null) {
					return Response.created(new URI(moUri.getUri(newFolder, thisUri)))
							.entity(new RESTFolder(newFolder, thisUri, fields)).build();
				}
			}

		} catch (InvalidProperty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cr.setResponseStatus("failed");
		cr.getResponseMessage().add("Could not create folder");
		
		return Response
				.status(400)
				.entity(cr)
				.build();
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
