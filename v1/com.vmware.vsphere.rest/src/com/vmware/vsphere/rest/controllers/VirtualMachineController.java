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
import com.vmware.vsphere.rest.models.CustomVirtualMachine;
import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.helpers.SearchParser;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;
import com.hubspot.jackson.jaxrs.PropertyFiltering;


@Path("/{viServer}/vms")
public class VirtualMachineController {

	// default values
	private int count = 0;
	private int maxResults = 25;

	@GET
	@PropertyFiltering(using = "fields", defaults = "id,name")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomVirtualMachine> getEntity(@Context HttpHeaders headers, 
			@PathParam("viServer") String viServer,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("10") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search) {
		
		// initialize variables
	    int position = 0;
	    
	    // determine the result set
	    if (results > this.maxResults) { results = this.maxResults; }
	    
		try {
			
			// create a new list and get all VMs
			List<CustomVirtualMachine> vmList = new ArrayList<CustomVirtualMachine>();
			ManagedEntity[] vms = new ViConnection().getEntities("VirtualMachine", headers, viServer);
			SearchParser sp = new SearchParser(search);
			
			for (ManagedEntity m : vms) {

				if (position >= start) {
					
					// create a new custom virtual machine object
					CustomVirtualMachine vm = new CustomVirtualMachine((VirtualMachine) m);
					if (sp.Match(vm)) {
						vmList.add(vm);
						this.count++;
						if (this.count >= results) { break; }
					}
				}
				position++;
			}
			
			// return filtered output
			return vmList;

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = "id,name")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomVirtualMachine getEntityById(@Context HttpHeaders headers, 
			@PathParam("viServer") String viServer,
			@PathParam("id") String id) {

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("VirtualMachine", id, headers, viServer);
			
			if (m != null) {

				CustomVirtualMachine vm = new CustomVirtualMachine((VirtualMachine) m);
				return vm;
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
