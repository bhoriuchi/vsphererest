package com.vmware.vsphere.rest.controllers;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.reflections.Reflections;

import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vim25.mo.util.MorUtil;
import com.hubspot.jackson.jaxrs.PropertyFiltering;

/*
 * Generic Object type controller. This will only work for objects that do not have 
 * fields that point to a managed object reference as MORs will through an infinite
 * recursion fault most of the time
 */
@Path("/{viServer}/{objectType}s")
public class ManagedObjectController {

	// default values
	final static String defaults = "id";
	
	// initialize
	@Context UriInfo uri;
	
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public ManagedObject getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer, @PathParam("objectType") String objectType,
			@PathParam("id") String id, @QueryParam("fields") String fields) {
		
	    System.out.println("running managed object controller");
	    
		try {
			
			Reflections reflections = new Reflections("com.vmware.vim25.mo");
			Set<Class<? extends ManagedObject>> allClasses = reflections.getSubTypesOf(ManagedObject.class);
			
			for (Class<?> c : allClasses) {
				System.out.println(c.getSimpleName());
				if (c.getSimpleName().toLowerCase().equals(objectType.toLowerCase())) {
					
					// Create a new MOR and use the MorUtil to create the object
					ManagedObjectReference mor = new ManagedObjectReference();
					mor.setType(c.getSimpleName());
					mor.setVal(id);
					ManagedObject mo = MorUtil.createExactManagedObject(new ViConnection().getServiceInstance(headers, viServer).getServerConnection(), mor);
			
					if (mo != null) {
						return mo;
					}
					else {
						return null;
					}
				}
			}
			

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
