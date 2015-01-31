package com.vmware.vsphere.rest.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.vmware.vsphere.rest.models.RESTCustomResponse;
import com.vmware.vsphere.rest.models.RESTDatacenter;
import com.vmware.vsphere.rest.models.RESTTask;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.helpers.SearchParser;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.hubspot.jackson.jaxrs.PropertyFiltering;


@Path("/{viServer}/datacenters")
public class DatacenterController {

	// default values
	private int count = 0;
	private int maxResults = 100;
	final static String defaults = "id,name";
	
	// initialize
	@Context UriInfo uri;

	
	/*
	 * Get all datacenters
	 */
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RESTDatacenter> getEntity(@Context HttpHeaders headers, 
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

			List<RESTDatacenter> moList = new ArrayList<RESTDatacenter>();
			ManagedEntity[] e = new ViConnection().getEntities("Datacenter", headers, viServer);
			SearchParser sp = new SearchParser(search);
			
			for (ManagedEntity m : e) {

				if (position >= start) {
					
					RESTDatacenter mo = new RESTDatacenter((Datacenter) m, thisUri, fieldStr);
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
	
	
	/*
	 * Get a single datacenter by id
	 */
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public RESTDatacenter getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("id") String id, @QueryParam("fields") String fields) {
		
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fieldStr = defaults;
	    if (fields != null) { fieldStr = fields; }

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id, headers, viServer);
			
			if (m != null) {

				RESTDatacenter mo = new RESTDatacenter((Datacenter) m, thisUri, fieldStr);
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

	/*
	 * Create a new datacenter
	 */
	@POST
	@PropertyFiltering(using = "fields", defaults = "id,name,responseStatus,responseMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer, RESTDatacenter body) {
	
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fields = defaults;
	    
		ViConnection vi = new ViConnection(headers, viServer);
		ServiceInstance si = vi.getServiceInstance();
		Folder rootFolder = si.getRootFolder();
		
		try {
			
			if (body.getName() != null) {
				Datacenter dc = rootFolder.createDatacenter(body.getName());
				URI uri = new URI(thisUri + dc.getMOR().getType() + "s/" + dc.getMOR().getVal());
				return Response.created(uri).entity(new RESTDatacenter(dc, thisUri, fields)).build();
			}
			else {
				return Response.status(400).entity(new RESTCustomResponse("badRequest","name not specified")).build();
			}
		} catch (InvalidName e) {
			return Response.status(400).entity(new RESTCustomResponse("invalidName", body.getName() + " is not a valid name")).build();
		} catch (DuplicateName e) {
			return Response.status(400).entity(new RESTCustomResponse("duplicateName", body.getName() + " already exists")).build();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * Modify a datacenter
	 */
	@Path("{id}")
	@PUT
	@PropertyFiltering(using = "fields", defaults = "id,name,responseStatus,responseMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putEntity(@Context HttpHeaders headers,
			@PathParam("id") String id,
			@PathParam("viServer") String viServer, RESTDatacenter body) {
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fieldStr = defaults;

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id, headers, viServer);
			
			if (m != null) {

				Datacenter mo = (Datacenter) m;
				
				// check if a new name was specified
				if (body.getName() != null) {
					Task t = mo.rename_Task(body.getName());
					URI uri = new URI(thisUri + t.getMOR().getType() + "s/" + t.getMOR().getVal());
					return Response.created(uri).entity(new RESTTask(t, thisUri, fieldStr)).build();
				}
			}
			else {
				return null;
			}

		} catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
