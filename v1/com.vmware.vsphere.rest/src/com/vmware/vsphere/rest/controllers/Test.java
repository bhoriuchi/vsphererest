package com.vmware.vsphere.rest.controllers;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.vmware.vsphere.rest.helpers.ViConnection;
import com.vmware.vsphere.rest.models.RESTVirtualMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

@Path("/test")
public class Test {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTest(@Context HttpHeaders headers) {

		
		String output = "";
		ServiceInstance si = new ViConnection().getServiceInstance(headers, "pvvvspm005.directv.com");
		Folder rootFolder = si.getRootFolder();
		ManagedEntity[] vms;
		try {
			vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
			
			VirtualMachine vm = (VirtualMachine) vms[0];
			JsonNode tree = new JacksonJsonProvider().locateMapper(RESTVirtualMachine.class, MediaType.APPLICATION_JSON_TYPE).valueToTree(vm);
			
			//ObjectMapper mapper = new ObjectMapper();
			
			//String[] props = {"name","summary.vm"};
			//output = mapper.writer().writeValueAsString(vms[0].getPropertiesByPaths(props));
			//output = mapper.writer().writeValueAsString(vm);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}

		return output;
	}
	
}
