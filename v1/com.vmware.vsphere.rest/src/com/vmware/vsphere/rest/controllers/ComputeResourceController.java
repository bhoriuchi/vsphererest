package com.vmware.vsphere.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.vmware.vsphere.rest.models.RESTComputeResource;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.helpers.SearchParser;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.ManagedEntity;
import com.hubspot.jackson.jaxrs.PropertyFiltering;


@Path("/{viServer}/computeresources")
public class ComputeResourceController {

	// default values
	private int count = 0;
	private int maxResults = 100;
	final static String defaults = "id,name";
	
	// initialize
	@Context UriInfo uri;

	
	// Get methods
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RESTComputeResource> getEntity(@Context HttpHeaders headers, 
			@PathParam("viServer") String viServer,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search, @QueryParam("fields") String fields) {
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    int position = 0;
	    String fieldStr = defaults;
	    if (fields != null) { fieldStr = fields; }
	    
	    // determine the result set
	    if (results > this.maxResults) { results = this.maxResults; }
	    
		try {
			
			// create a new list and get all VMs
			List<RESTComputeResource> moList = new ArrayList<RESTComputeResource>();
			ManagedEntity[] e = new ViConnection().getEntities("ComputeResource", headers, viServer);
			SearchParser sp = new SearchParser(search);
			
			for (ManagedEntity m : e) {

				if (position >= start) {
					
					// create a new custom virtual machine object
					RESTComputeResource mo = new RESTComputeResource((ComputeResource) m, thisUri, fieldStr);
					if (sp.Match(mo)) {
						moList.add(mo);
						this.count++;
						if (this.count >= results) { break; }
					}
				}
				position++;
			}
			
			// return filtered output
			return moList;

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public RESTComputeResource getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("id") String id, @QueryParam("fields") String fields) {
		
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fieldStr = defaults;
	    if (fields != null) { fieldStr = fields; }

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("ComputeResource", id, headers, viServer);
			
			if (m != null) {

				RESTComputeResource mo = new RESTComputeResource((ComputeResource) m, thisUri, fieldStr);
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