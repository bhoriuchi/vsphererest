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
import com.vmware.vsphere.rest.helpers.OutputFilter;
import com.vmware.vsphere.rest.helpers.SearchParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;

@Path("/{viServer}/vms")
public class VirtualMachineController {

	// default values
	private int count = 0;
	private int maxResults = 25;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getVms(@Context HttpHeaders headers, 
			@PathParam("viServer") String viServer,
			@DefaultValue("id,name") @QueryParam("fields") String fields,
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
			return new OutputFilter().getOutput(fields, vmList);

		} catch (NullPointerException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CustomVirtualMachine getVmById(@Context HttpHeaders headers, 
			@PathParam("viServer") String viServer,
			@PathParam("id") String id,
			@DefaultValue("id,name") @QueryParam("fields") String fields) {

		try {
			
			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("VirtualMachine", id, headers, viServer);
			
			if (m != null) {

				// return filtered output
				//return new OutputFilter().getOutput(fields, new CustomVirtualMachine((VirtualMachine) m));
				return new CustomVirtualMachine((VirtualMachine) m);
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
