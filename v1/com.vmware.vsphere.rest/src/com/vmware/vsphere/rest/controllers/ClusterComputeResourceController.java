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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.vmware.vsphere.rest.models.RESTClusterComputeResource;
import com.vmware.vsphere.rest.models.RESTCustomResponse;
import com.vmware.vsphere.rest.models.RESTRequestBody;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.helpers.SearchParser;
import com.vmware.vim25.ClusterConfigSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.hubspot.jackson.jaxrs.PropertyFiltering;


@Path("/{viServer}/clustercomputeresources")
public class ClusterComputeResourceController {

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
	public List<RESTClusterComputeResource> getEntity(@Context HttpHeaders headers, 
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
			List<RESTClusterComputeResource> moList = new ArrayList<RESTClusterComputeResource>();
			ManagedEntity[] e = new ViConnection().getEntities("ClusterComputeResource", headers, viServer);
			SearchParser sp = new SearchParser(search);
			
			for (ManagedEntity m : e) {

				if (position >= start) {
					
					// create a new custom virtual machine object
					RESTClusterComputeResource mo = new RESTClusterComputeResource((ClusterComputeResource) m, thisUri, fieldStr);
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
	public RESTClusterComputeResource getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("id") String id, @QueryParam("fields") String fields) {
		
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fieldStr = defaults;
	    if (fields != null) { fieldStr = fields; }

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("ClusterComputeResource", id, headers, viServer);
			
			if (m != null) {

				RESTClusterComputeResource mo = new RESTClusterComputeResource((ClusterComputeResource) m, thisUri, fieldStr);
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
	 * Create a new clustercomputeresource (cluster)
	 */
	@POST
	@PropertyFiltering(using = "fields", defaults = "id,name,responseStatus,responseMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer, RESTRequestBody body) {
	
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
	    String fields = defaults;

		try {
			
			if (body.getDatacenter() != null && body.getName() != null) {
				
				// try to get the datacenter host folder
				ManagedEntity m = new ViConnection().getEntity("Datacenter", body.getDatacenter(), headers, viServer);
				
				if (m != null) {
					
					if (body.getSpec() == null) {
						body.setSpec(new ClusterConfigSpec());
					}
					
					Datacenter dc = (Datacenter) m;
					Folder f = dc.getHostFolder();
					ClusterComputeResource mo = f.createCluster(body.getName(), (ClusterConfigSpec) body.getSpec());
				
				
				
				
					URI uri = new URI(thisUri + mo.getMOR().getType().toLowerCase() + "s/" + mo.getMOR().getVal());
					return Response.created(uri).entity(new RESTClusterComputeResource(mo, thisUri, fields)).build();
				}
			}
			else {
				return Response.status(400).entity(new RESTCustomResponse("badRequest","name or cluster not specified")).build();
			}
			
		} catch (URISyntaxException e) {
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
		return null;
	}
	
	
	
	
}
