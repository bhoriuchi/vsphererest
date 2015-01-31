package com.vmware.vsphere.rest.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.vmware.vsphere.rest.models.RESTVirtualMachineSnapshot;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import com.vmware.vim25.mo.util.MorUtil;
import com.hubspot.jackson.jaxrs.PropertyFiltering;


@Path("/{viServer}/virtualmachinesnapshots")
public class VirtualMachineSnapshotController {

	// default values
	final static String defaults = "id,childSnapshot";
	
	// initialize
	@Context UriInfo uri;
	
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public RESTVirtualMachineSnapshot getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("id") String id, @QueryParam("fields") String fields) {
		
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fieldStr = defaults;
	    if (fields != null) { fieldStr = fields; }

		try {
			
			// Create a new MOR and use the MorUtil to create the object
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType("VirtualMachineSnapshot");
			mor.setVal(id);
			VirtualMachineSnapshot m = (VirtualMachineSnapshot) MorUtil.createExactManagedObject(new ViConnection().getServiceInstance(headers, viServer).getServerConnection(), mor);
			
			if (m != null) {

				RESTVirtualMachineSnapshot mo = new RESTVirtualMachineSnapshot(m, thisUri, fieldStr);
				return mo;
			}
			else {
				return null;
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
